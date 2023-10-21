package com.technolearn.rasoulonlineshop.vo.res

import androidx.annotation.DrawableRes

data class CategoryRes(
    var id: Long = 0,
    var title: String = "",
    @DrawableRes var image: Int
)
