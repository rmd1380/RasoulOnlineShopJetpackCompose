package com.technolearn.rasoulonlineshop.vo.generics

data class ApiResponse<T>(
    val data: T?=null,
    val message: String?=null,
    val status: Int?=null,
    val totalCount: Long?=null,
)
