package com.technolearn.rasoulonlineshop.vo.generics

import com.technolearn.rasoulonlineshop.vo.enums.ErrorStatus
import com.technolearn.rasoulonlineshop.vo.enums.Status
import com.technolearn.rasoulonlineshop.vo.res.ErrorResponse

data class Resource<out T>(
    val status: Status,
    val data: T?,
    val errorData: ErrorResponse?,
    val responseCode: Int?,
    val message: String?,
    val errorStatus: ErrorStatus?,
) {
    companion object {

        fun <T> loading(data: T?): Resource<T> {
            return Resource(Status.LOADING, data, null, null, null, null)
        }

        fun <T> success(data: T?): Resource<T> {
            return Resource(Status.SUCCESS, data, null, null, null, null)
        }

        fun <T> error(
            responseCode: Int?,
            message: String?,
            data: T?,
            errorData: ErrorResponse?,
            errorStatus: ErrorStatus?
        ): Resource<T> {
            return Resource(Status.ERROR, data, errorData, responseCode, message, errorStatus)
        }

    }
}
