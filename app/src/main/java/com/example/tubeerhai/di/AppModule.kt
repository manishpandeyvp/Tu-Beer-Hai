package com.example.tubeerhai.di

import com.example.tubeerhai.data.remote.BeersApiService
import com.example.tubeerhai.data.repository.MyBeersRepositoryImpl
import com.example.tubeerhai.domain.repository.MyBeersRepository
import com.example.tubeerhai.domain.usecase.GetAllBearsUseCase
import com.example.tubeerhai.domain.usecase.GetAllBearsUseCaseImpl
import com.example.tubeerhai.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideBeersApiService(): BeersApiService {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(BeersApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideMyBeersRepository(beersApiService: BeersApiService): MyBeersRepository {
        return MyBeersRepositoryImpl(beersApiService)
    }

    @Provides
    @Singleton
    fun providesGetAllBearsUseCase(beersRepository: MyBeersRepository): GetAllBearsUseCase {
        return GetAllBearsUseCaseImpl(beersRepository)
    }
}