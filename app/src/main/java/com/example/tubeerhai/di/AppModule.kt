package com.example.tubeerhai.di

import com.example.tubeerhai.data.remote.BeersApiService
import com.example.tubeerhai.data.repository.MyBeersRepositoryImpl
import com.example.tubeerhai.domain.repository.MyBeersRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideBeersApiService(): BeersApiService {
        return Retrofit.Builder()
            .baseUrl("https://api.punkapi.com/v2")
            .build()
            .create(BeersApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideMyBeersRepository(beersApiService: BeersApiService): MyBeersRepository {
        return MyBeersRepositoryImpl(beersApiService)
    }
}