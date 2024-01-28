package com.technolearn.rasoulonlineshop.vo.res

data class SliderRes(
    var id: Int? = null,
    var image: String? = null,
    var link: String? = null,
    var subTitle: String? = null,
    var title: String? = null,
    var category: CategoryRes? = CategoryRes(),
)
