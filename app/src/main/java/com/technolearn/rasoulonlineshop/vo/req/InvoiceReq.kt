package com.technolearn.rasoulonlineshop.vo.req

import com.technolearn.rasoulonlineshop.vo.model.InvoiceItems
import com.technolearn.rasoulonlineshop.vo.res.SignUpRes

data class InvoiceReq(
    var user: SignUpRes?=null,
    var invoiceItems: List<InvoiceItems>?=null
)
