package com.technolearn.rasoulonlineshop.vo.model.helperComponent

import com.technolearn.rasoulonlineshop.R
import com.technolearn.rasoulonlineshop.util.CreditCardTypeFinder
import com.technolearn.rasoulonlineshop.vo.enums.CreditCardType

data class CreditCardModel(
    var number: String = "",
    var expiration: String = "0000", // First two digits = month, last two digits = year
    var holderName: String = "",
    var cvv2: String = "000",
    var cardEntity: String = "VISA"
) {
    val logoCardIssuer = when(CreditCardTypeFinder.detect(number)) {
        CreditCardType.MELLI -> R.drawable.melli_logo
        CreditCardType.SEPAH -> R.drawable.sepah_logo
        CreditCardType.MELLAT -> R.drawable.mellat_logo
        CreditCardType.TEJARAT -> R.drawable.tejarat_logo
        CreditCardType.OTHER -> null
    }


    val formattedExpiration = when {
        expiration.length == 2 -> "$expiration/"
        expiration.length > 2 -> expiration.substring(0, 2) + "/" + expiration.substring(2, expiration.length)
        else -> expiration
    }
}