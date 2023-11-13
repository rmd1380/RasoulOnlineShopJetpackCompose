package com.technolearn.rasoulonlineshop.vo.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class FavoriteEntity(
    @PrimaryKey var id: Int = 0,
    var brand: String? = null,
    var title: String? = null,
    var image: ArrayList<String> = arrayListOf(),
    var addDate: Long? = null,
    var price: Double? = null,
    var rate: Double? = null,
    var hasDiscount: Float? = null,
    var isAddToFavorites: Boolean = false
)
