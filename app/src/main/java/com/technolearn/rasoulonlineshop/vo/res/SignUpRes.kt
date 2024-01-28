package com.technolearn.rasoulonlineshop.vo.res

data class SignUpRes(
    val id: Long,
    val userName: String?=null,
    val password: String?=null,
    val email: String?=null,
    val customer: Customer?=null
) {
    data class Customer(
        val id: Long?,
        val firstName: String?,
        val lastName: String?,
        val phone: String?,
        val addressName: String?,
        val address: String?,
        val city: String?,
        val province: String?,
        val postalCode: String?,
        val country: String?
    )
}
