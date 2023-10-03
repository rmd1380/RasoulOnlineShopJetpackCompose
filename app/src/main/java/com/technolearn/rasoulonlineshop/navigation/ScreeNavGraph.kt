package com.technolearn.rasoulonlineshop.navigation

sealed class Screen(val route:String){
    object SignUpScreen: Screen("signup_screen")
    object LoginScreen: Screen("login_screen")
    object ProductScreen: Screen("product_screen")
    object ProductDetailScreen: Screen("product_detail_screen")
}
