package com.flickr.demo.flickrdemo.domain.usecase

import com.flickr.demo.flickrdemo.data.model.Thumbnail
import com.flickr.demo.flickrdemo.data.repository.ThumbnailRepository

class GetThumbnailsUseCase(private val repository: ThumbnailRepository) {
    suspend fun execute(query: String): Thumbnail {
        return repository.searchThumbnails(query)
    }
}
