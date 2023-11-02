package com.technolearn.rasoulonlineshop.vo.res

data class SignUpRes(
    val id: Long,
    val userName: String,
    val password: String,
    val email: String,
    val customer: Customer?
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
