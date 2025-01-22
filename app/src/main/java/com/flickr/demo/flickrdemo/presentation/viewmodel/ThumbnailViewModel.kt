package com.flickr.demo.flickrdemo.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flickr.demo.flickrdemo.data.model.Items
import com.flickr.demo.flickrdemo.data.model.Thumbnail
import com.flickr.demo.flickrdemo.domain.usecase.GetThumbnailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ThumbnailViewModel @Inject constructor(private val getThumbnailsUseCase: GetThumbnailsUseCase) :
    ViewModel() {
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    private val _thumbnails = MutableStateFlow<Thumbnail?>(null)
    val thumbnails: StateFlow<Thumbnail?> = _thumbnails

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    private val _selectedThumbnail = MutableStateFlow<Items?>(null)
    val selectedThumbnail: StateFlow<Items?> = _selectedThumbnail


    fun setSelectedThumbnail(thumbnail: Items) {
        _selectedThumbnail.value = thumbnail
    }

    fun getSelectedThumbnail(): Items? {
        return _selectedThumbnail.value
    }

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
        viewModelScope.launch {
            _isLoading.value = true

            searchQuery.debounce(300L)
                .distinctUntilChanged()
                .collect { query ->
                    _isLoading.value = false
                    if (query.isNotEmpty()) {
                        _thumbnails.value = getThumbnailsUseCase.execute(query)

                    } else {
                        _thumbnails.value = null
                        //_locations.value = emptyList()
                    }
                }
        }
    }

    fun searchThumbnails(query: String) {
        viewModelScope.launch {
            searchQuery.debounce(300L)
                .distinctUntilChanged()
                .collect { query ->
                    if (query.isNotEmpty()) {
                        _thumbnails.value = getThumbnailsUseCase.execute(query)

                    } else {
                        _thumbnails.value = null
                        //_locations.value = emptyList()
                    }
                }
        }
//        viewModelScope.launch {
//            _thumbnails.value = getThumbnailsUseCase.execute(query)
//        }
    }

    init {
        // Initial loading with empty query or default query
        Log.d("Search>>", _searchQuery.value)
        viewModelScope.launch {
            searchThumbnails(_searchQuery.value)
        }
    }
}
