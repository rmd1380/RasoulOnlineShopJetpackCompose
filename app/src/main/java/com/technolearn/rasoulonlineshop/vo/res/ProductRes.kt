package com.technolearn.rasoulonlineshop.vo.res

data class ProductRes(
    var id: Int = 0,
    var brand: String? = null,
    var title: String? = null,
    var image: ArrayList<String> = arrayListOf(),
    var threeDModel: String? = null,
    var addDate: Long? = null,
    var price: Double? = null,
    var rate: Double? = null,
    var description: String? = null,
    var hasDiscount: Float? = null,
    var category: CategoryRes? = CategoryRes(),
    var colors: ArrayList<ColorRes> = arrayListOf(),
    var sizes: ArrayList<SizeRes> = arrayListOf(),
//    var invoiceItems: ArrayList<InvoiceItems> = arrayListOf(),
    var isAddToFavorites: Boolean = false,
    var isAddToCart: Boolean = false
)
