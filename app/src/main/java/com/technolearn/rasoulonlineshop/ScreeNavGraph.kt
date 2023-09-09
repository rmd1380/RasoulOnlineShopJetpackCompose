package com.technolearn.rasoulonlineshop

sealed class Screen(val route:String){
    object SignUpScreen:Screen("signup_screen")
    object LoginScreen:Screen("login_screen")
}
