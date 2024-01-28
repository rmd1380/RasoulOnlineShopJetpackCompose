package com.technolearn.rasoulonlineshop.vo.model

import com.technolearn.rasoulonlineshop.vo.res.ProductRes

data class InvoiceItems(
    var id: Long? = null,
    var quantity: Int? = null,
    var unitPrice: Double? = null,
    var product: ProductRes? = ProductRes()
)