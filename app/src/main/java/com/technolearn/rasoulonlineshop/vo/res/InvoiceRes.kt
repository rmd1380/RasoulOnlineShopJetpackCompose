package com.technolearn.rasoulonlineshop.vo.res

import com.technolearn.rasoulonlineshop.vo.model.InvoiceItems

data class InvoiceRes(
    var id: Long? = null,
    var status: String? = null,
    var addDate: String? = null,
    var paymentDate: String? = null,
    var user: SignUpRes? = null,
    var invoiceItems: ArrayList<InvoiceItems>? = arrayListOf()
)

