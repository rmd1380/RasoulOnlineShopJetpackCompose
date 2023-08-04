package com.technolearn.rasoulonlineshop.vo.res


data class ErrorResponse(
    val message: String,
    val succeeded: Boolean,
    val isWarning: Boolean,
    val error: List<Error>
)

data class Error(
    val fieldName: String,
    val errorMessages: List<String>
)