package com.flickr.demo.flickrdemo.data.repository

import com.flickr.demo.flickrdemo.data.model.Thumbnail
import com.flickr.demo.flickrdemo.data.remote.ThumbnailApiService

class ThumbnailRepositoryImpl(private val apiService: ThumbnailApiService) : ThumbnailRepository {
    override suspend fun searchThumbnails(query: String): Thumbnail {
        return apiService.searchThumbnails(query)
    }
}
