package com.technolearn.rasoulonlineshop.navigation

const val PRODUCT_ID = "PRODUCT_ID"

sealed class Screen(val route: String) {
    object SignUpScreen : Screen("signup_screen")
    object LoginScreen : Screen("login_screen")
    object ProductScreen : Screen("product_screen")
    object ProductDetailScreen : Screen("product_detail_screen/{$PRODUCT_ID}") {
        fun passProductId(id: Int): String {
            return this.route.replace(oldValue = "{$PRODUCT_ID}", newValue = id.toString())
        }
    }
}
