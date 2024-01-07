package com.technolearn.rasoulonlineshop.vo.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_login")
data class UserLoginEntity(
    @PrimaryKey (autoGenerate = true) val id: Long=0,
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
    val token: String,
    //
    val isLogin:Boolean=false,
    val dateOfBirth:String=""
)

