package com.example.tubeerhai.domain.usecase

import com.example.tubeerhai.data.model.BeerModel
import com.example.tubeerhai.domain.repository.MyBeersRepository
import com.example.tubeerhai.util.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

interface GetBeerDataWithIdUseCase {
    fun execute(id: String): Flow<Response<BeerModel>>
}

class GetBeerDataWithIdUseCaseImpl @Inject constructor(
    private val beersRepository: MyBeersRepository
): GetBeerDataWithIdUseCase {
    override fun execute(id: String): Flow<Response<BeerModel>> = flow {
        try {
            emit(Response.Loading<BeerModel>())
            val data = beersRepository.getBeerWithId(id)?.toBeerModel()
            if (data != null) {
                emit(Response.Success<BeerModel>(data = data))
            } else {
                emit(Response.Error<BeerModel>("Error Occurred while fetching the data!"))
            }
        } catch (e: HttpException) {
            emit(Response.Error<BeerModel>("Error Occurred while fetching the data!"))
        } catch (e: IOException) {
            emit(Response.Error<BeerModel>("Error Occurred while fetching the data!"))
        }
    }
}