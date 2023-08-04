package com.technolearn.rasoulonlineshop.util

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.technolearn.rasoulonlineshop.util.Extensions.showHttpErrorToast
import com.technolearn.rasoulonlineshop.util.Extensions.showNetworkConnectionErrorToast
import com.technolearn.rasoulonlineshop.vo.enums.ErrorStatus
import com.technolearn.rasoulonlineshop.vo.generics.Resource
import com.technolearn.rasoulonlineshop.vo.res.ErrorResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response
import timber.log.Timber
import java.io.IOException
import java.net.ConnectException
import java.net.UnknownHostException


private val gson: Gson = Gson()
private val errorType = object : TypeToken<ErrorResponse>() {}.type

suspend fun <T> safeApiCall(
    dispatcher: CoroutineDispatcher,
    context: Context? = null,
    apiCall: suspend () -> Response<T>
): LiveData<Resource<T>> {
    return liveData(dispatcher) {
        var response: Response<T>? = null
        try {
            Timber.d("safeApiCall Try")
            emit(Resource.loading(null))

            response = apiCall.invoke()
            if (response.isSuccessful) {
                emit(Resource.success(response.body()))
            } else {
                emit(
                    Resource.error(
                        response.code(), response.message(), response.body(),
                        //////////// check here
                        convertToErrorResponse(response.errorBody()!!), ErrorStatus.HTTP_EXCEPTION
                    )
                )
            }
        } catch (throwable: Throwable) {
            Timber.d("safeApiCall catch %s", throwable)
            when (throwable) {
                is IOException -> {
                    Timber.d("throwable::TT:: $throwable")
                    if (throwable is ConnectException || throwable is UnknownHostException) {
                        Timber.d("throwable::IF")
                        CoroutineScope(Dispatchers.Main).launch {
                            context?.showNetworkConnectionErrorToast()
                        }

                        emit(
                            Resource.error(
                                response?.code(),
                                throwable.message,
                                null,
                                null,
                                ErrorStatus.NETWORK_CONNECTION_ERROR
                            )
                        )
                    } else {
                        Timber.d("throwable::ELSE :: ${throwable.message}")
                        CoroutineScope(Dispatchers.Main).launch {
                            context?.showHttpErrorToast(throwable.message)
                        }
                        emit(
                            Resource.error(
                                response?.code(),
                                throwable.message,
                                null,
                                null,
                                ErrorStatus.IO_EXCEPTION
                            )
                        )
                    }
                }
                is HttpException -> {
                    CoroutineScope(Dispatchers.Main).launch {
                        context?.showHttpErrorToast(throwable.message)
                    }
                    val code = throwable.code()
                    emit(
                        Resource.error(
                            code,
                            throwable.message,
                            null,
                            convertToErrorResponse(throwable.response()!!.errorBody()!!),
                            ErrorStatus.HTTP_EXCEPTION)
                    )
                }
                else -> {
                    CoroutineScope(Dispatchers.Main).launch {
                        context?.showHttpErrorToast(throwable.message)
                    }
                    emit(Resource.error(response?.code(), throwable.message, null, null, null))
                }
            }
        }
    }
}

private fun convertToErrorResponse(errorBody: ResponseBody): ErrorResponse? {
    return gson.fromJson(errorBody.string(), errorType)
}