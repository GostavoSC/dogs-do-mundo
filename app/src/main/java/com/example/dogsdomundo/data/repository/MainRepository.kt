package com.example.dogsdomundo.data.repository

import ErrorResponse
import com.example.dogsdomundo.data.api.ApiHelper
import com.example.dogsdomundo.data.repository.dto.BreedImageApiDto
import com.example.dogsdomundo.data.repository.dto.BreedsListApiDto
import com.example.dogsdomundo.ui.util.ResultWrapper
import com.squareup.moshi.Moshi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

class MainRepository(
    private val apiHelper: ApiHelper,
    val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    suspend fun getBreeds(): ResultWrapper<BreedsListApiDto> {
        return safeApiCall(dispatcher) { apiHelper.getBreeds() }
    }


    suspend fun getImageBreeds(breed: String): ResultWrapper<BreedImageApiDto> {
        return safeApiCall(dispatcher) { apiHelper.getImageBreed(breed) }
    }


    suspend fun <T> safeApiCall(
        dispatcher: CoroutineDispatcher,
        apiCall: suspend () -> T
    ): ResultWrapper<T> {
        return withContext(dispatcher) {
            try {
                ResultWrapper.Success(apiCall.invoke())
            } catch (throwable: Throwable) {
                when (throwable) {
                    is IOException -> ResultWrapper.NetworkError
                    is HttpException -> {
                        val code = throwable.code()
                        val errorResponse = convertErrorBody(throwable)
                        ResultWrapper.GenericError(code, errorResponse)
                    }
                    else -> {
                        ResultWrapper.GenericError(null, null)
                    }
                }
            }
        }
    }

    private fun convertErrorBody(throwable: HttpException): ErrorResponse? {
        return try {
            throwable.response()?.errorBody()?.source()?.let {
                val moshiAdapter = Moshi.Builder().build().adapter(ErrorResponse::class.java)
                moshiAdapter.fromJson(it)
            }
        } catch (exception: Exception) {
            null
        }
    }

}