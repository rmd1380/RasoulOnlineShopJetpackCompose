package com.technolearn.rasoulonlineshop.vo.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_login")
data class UserLoginEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val username: String? = null,
    val oldPassword: String? = null,
    val password: String? = null,
    val repeatPassword: String? = null,
    val email: String? = null,
    val firstName: String? = null,
    val lastName: String? = null,
    val phone: String? = null,
    val addressName: String? = null,
    val address: String? = null,
    val city: String? = null,
    val province: String? = null,
    val postalCode: String? = null,
    val country: String? = null,
    val customerId: Long? = null,
    val token: String? = null,
    //
    val isLogin: Boolean = false,
    val dateOfBirth: String = ""
)

