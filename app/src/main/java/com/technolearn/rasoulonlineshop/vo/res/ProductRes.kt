package com.technolearn.rasoulonlineshop.vo.res

import androidx.annotation.DrawableRes

data class ProductRes(
    var id: Int = 0,
    var brand: String? = "",
    var title: String? = "",
    @DrawableRes var image: ArrayList<Int> = arrayListOf(),
    var addDate: String? = "",
    var price: Double? = 0.0,
    var rate: Double? = 0.0,
    var label: String? = "",
    var description: String? = "",
    var hasDiscount: Float? = 0f,
    //
    var isAddToFavorites: Boolean = false
)
