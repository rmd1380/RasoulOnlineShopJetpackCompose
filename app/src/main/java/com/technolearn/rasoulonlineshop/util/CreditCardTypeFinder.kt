package com.technolearn.rasoulonlineshop.util

import com.technolearn.rasoulonlineshop.vo.enums.CreditCardType

class CreditCardTypeFinder {

    companion object {
        fun detect(number: String): CreditCardType = when {
            isMelli(number) -> CreditCardType.MELLI
            isSepah(number) -> CreditCardType.SEPAH
            isMellat(number) -> CreditCardType.MELLAT
            isTejarat(number) -> CreditCardType.TEJARAT
            else -> CreditCardType.OTHER
        }

        private fun hasValidLengthAndPrefix(number: String, prefix: String): Boolean =
            number.length >= 6 && number.startsWith(prefix)

        private fun isMelli(number: String) = hasValidLengthAndPrefix(number, "603799")
        private fun isSepah(number: String) = hasValidLengthAndPrefix(number, "589210")
        private fun isMellat(number: String) = hasValidLengthAndPrefix(number, "610433")
        private fun isTejarat(number: String) = hasValidLengthAndPrefix(number, "627353")
    }


}