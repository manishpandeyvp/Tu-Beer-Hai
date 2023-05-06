package com.example.tubeerhai.domain.usecase

import android.util.Log
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
            emit(Response.Loading<List<BeerModel>>())
            val data = beersRepository.getAllBears().map {
                it.toBeerModel()
            }
            Log.d("list_debug_2: ", "$data")
            emit(Response.Success<List<BeerModel>>(data = data))
        } catch (e: HttpException) {
            e.localizedMessage?.let { Log.d("list_debug_2: ", it) }
            Log.d("list_debug_2: ", e.message())
            emit(Response.Error<List<BeerModel>>("Error Occurred while fetching the data!"))
        } catch (e: IOException) {
            e.localizedMessage?.let { Log.d("list_debug_3: ", it) }
            e.message?.let { Log.d("list_debug_3: ", it) }
            emit(Response.Error<List<BeerModel>>("Error Occurred while fetching the data!"))
        }
    }
}