package com.technolearn.rasoulonlineshop.vo.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.technolearn.rasoulonlineshop.vo.res.CategoryRes

@Entity(tableName = "user_cart")
data class UserCartEntity(
    @PrimaryKey val id: Int?=0,
    val name:String?=null,
    val image:String?=null,
    val color:String?=null,
    val size:String?=null,
    val price: Double?=null,
    var quantity:Int?=null,
    var category: CategoryRes? = CategoryRes(),
)

