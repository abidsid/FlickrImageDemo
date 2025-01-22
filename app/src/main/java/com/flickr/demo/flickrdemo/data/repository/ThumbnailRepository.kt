package com.flickr.demo.flickrdemo.data.repository

import com.flickr.demo.flickrdemo.data.model.Thumbnail

interface ThumbnailRepository {
    suspend fun searchThumbnails(query: String): Thumbnail
}
