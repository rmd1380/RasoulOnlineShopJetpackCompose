package com.technolearn.rasoulonlineshop.vo.res

import androidx.annotation.DrawableRes
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class ProductRes(
    @PrimaryKey var id: Int = 0,
    var brand: String?=null,
    var title: String?=null,
    var image: ArrayList<String>?=null,
    var addDate: String?=null,
    var price: Double?=null,
    var rate: Double?=null,
    var label: String?=null,
    var description: String?=null,
    var hasDiscount: Float?=null,
    //
    var isAddToFavorites: Boolean = false
)
