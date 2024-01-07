package com.technolearn.rasoulonlineshop.vo.req

data class UpdatePasswordReq(
    val id: Long,
    val oldPassword: String,
    val password: String,
    val repeatPassword: String
)
