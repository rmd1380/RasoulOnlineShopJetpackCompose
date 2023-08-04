package com.technolearn.rasoulonlineshop.vo.generics

data class ApiResponse<T>(
    val data: List<T>?=null,
    val message: String?=null,
    val status: String?=null,
    val totalCount: Long?=null,
)