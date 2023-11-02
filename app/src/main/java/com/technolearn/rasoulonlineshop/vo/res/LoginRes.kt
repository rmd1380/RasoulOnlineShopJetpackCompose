package com.technolearn.rasoulonlineshop.vo.res

data class LoginRes(
    val id: Long,
    val username: String,
    val oldPassword: String,
    val password: String,
    val repeatPassword: String,
    val email: String,
    val firstName: String,
    val lastName: String,
    val phone: String,
    val addressName: String,
    val address: String,
    val city: String,
    val province: String,
    val postalCode: String,
    val country: String,
    val customerId: Long,
    val token: String
) 
