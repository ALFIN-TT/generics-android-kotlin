package com.example.generics.example.examplesix

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


sealed class Result<T>(
    val response: T? = null,
    val message: Any? = null
) {

    class Loading<T>() : Result<T>()
    class Success<T>(response: T) : Result<T>()
    class Error<T>(error: Any?) : Result<T>()
}

class Response


suspend fun getResultFromApi(sendError: Boolean = false): Flow<Result<Response>> = flow {

    emit(Result.Loading())
    delay(100)
    try {
        //call api here
        //handle success
        if (sendError) 1 / 0
        emit(Result.Success(Response()))
    } catch (e: Exception) {
        //handle error
        emit(Result.Error(error = e.message))
    }
}