package com.technolearn.rasoulonlineshop.vo.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_cart")
data class UserCartEntity(
    @PrimaryKey val id: Int?,
    val name:String?,
    val image:String?,
    val color:String?,
    val size:String?,
    val price: Double?,
    var quantity:Int?
)

