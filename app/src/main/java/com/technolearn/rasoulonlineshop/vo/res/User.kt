package com.technolearn.rasoulonlineshop.vo.res

data class User(
    val id: Long?=null,
    val userName: String? = null,
    val password: String? = null,
    val email: String? = null,
    val customer: Customer? = null
) {
    data class Customer(

        var id: Long? = null,
        var firstName: String? = null,
        var lastName: String? = null,
        var phone: String? = null,
        var addressName: String? = null,
        var address: String? = null,
        var city: String? = null,
        var province: String? = null,
        var postalCode: String? = null,
        var country: String? = null

    )
}
