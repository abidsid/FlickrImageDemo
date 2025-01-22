package com.flickr.demo.flickrdemo.data.remote

import com.flickr.demo.flickrdemo.data.model.Thumbnail
import retrofit2.http.GET
import retrofit2.http.Query

interface ThumbnailApiService {
    @GET("photos_public.gne?format=json&nojsoncallback=1")
    suspend fun searchThumbnails(@Query("tags") tags: String): Thumbnail
}
