package com.example.tubeerhai.domain.usecase

import com.example.tubeerhai.data.model.BeerModel
import com.example.tubeerhai.domain.repository.MyBeersRepository
import com.example.tubeerhai.util.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

interface GetAllBearsUseCase {
    fun execute(): Flow<Response<List<BeerModel>>>
}

class GetAllBearsUseCaseImpl @Inject constructor(
    private val beersRepository: MyBeersRepository
): GetAllBearsUseCase {
    override fun execute(): Flow<Response<List<BeerModel>>> = flow {
        try {
            emit(Response.Loading())
            val data = beersRepository.getAllBears().map {
                it.toBeerModel()
            }
            emit(Response.Success(data = data))
        } catch (e: HttpException) {
            emit(Response.Error("Error Occurred while fetching the data!"))
        } catch (e: IOException) {
            emit(Response.Error("Error Occurred while fetching the data!"))
        }
    }
}