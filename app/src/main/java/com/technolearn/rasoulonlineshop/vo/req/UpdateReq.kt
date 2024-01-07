package com.technolearn.rasoulonlineshop.vo.req

data class UpdateReq(
    val id: Long,
    val firstName: String,
    val lastName: String,
    val phone: String,
    val addressName: String,
    val address: String,
    val city: String,
    val province: String,
    val postalCode: String,
    val country: String
)
