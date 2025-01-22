package com.flickr.demo.flickrdemo

import com.flickr.demo.flickrdemo.data.remote.ThumbnailApiService
import com.flickr.demo.flickrdemo.data.repository.ThumbnailRepository
import com.flickr.demo.flickrdemo.data.repository.ThumbnailRepositoryImpl
import com.flickr.demo.flickrdemo.domain.usecase.GetThumbnailsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideThumbnailApiService(): ThumbnailApiService {
        return Retrofit.Builder()
            .baseUrl("https://api.flickr.com/services/feeds/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ThumbnailApiService::class.java)
    }

    @Provides
    fun provideThumbnailRepository(apiService: ThumbnailApiService): ThumbnailRepository {
        return ThumbnailRepositoryImpl(apiService)
    }

    @Provides
    fun provideGetThumbnailsUseCase(repository: ThumbnailRepository): GetThumbnailsUseCase {
        return GetThumbnailsUseCase(repository)
    }
}
