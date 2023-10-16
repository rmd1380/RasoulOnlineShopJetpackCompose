package com.technolearn.rasoulonlineshop.navigation

import com.technolearn.rasoulonlineshop.util.Constants

sealed class Screen(val route: String) {
    object SignUpScreen : Screen("signup_screen")
    object LoginScreen : Screen("login_screen")
    object ProductScreen : Screen("product_screen")
    object ProductDetailScreen : Screen("product_detail_screen/{${Constants.Argument.PRODUCT_ID}}") {
        fun passProductId(id: Int): String {
            return this.route.replace(oldValue = "{${Constants.Argument.PRODUCT_ID}}", newValue = id.toString())
        }
    }
}
