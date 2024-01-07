package com.technolearn.rasoulonlineshop.vo.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_Address")
data class UserAddressEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val userId:Long,
    val firstName: String,
    val lastName: String,
    val phone: String,
    val addressName: String,
    val address: String,
    val city: String,
    val province: String,
    val postalCode: String,
    val country: String,
    var isAddressSelected:Boolean=false
)

