package com.technolearn.rasoulonlineshop.util

import java.util.Locale

fun prepareToken(token: String): String {
    var fixedToken = token
    if (!fixedToken.lowercase(Locale.getDefault()).startsWith("bearer")) {
        fixedToken = "Bearer $token"
    }
    return fixedToken
}