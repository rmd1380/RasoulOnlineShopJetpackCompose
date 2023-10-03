package com.technolearn.rasoulonlineshop.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.technolearn.rasoulonlineshop.screens.LoginScreen
import com.technolearn.rasoulonlineshop.screens.ProductScreen
import com.technolearn.rasoulonlineshop.screens.SignUpScreen

@Composable
fun SetupNavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Screen.ProductScreen.route
    ) {
        composable(route = Screen.SignUpScreen.route) {
            SignUpScreen(navController = navController)
        }

        composable(
            route = Screen.LoginScreen.route,
        ) {
            LoginScreen(navController = navController)
        }

        composable(
            route = Screen.ProductScreen.route,
        ) {
            ProductScreen(navController = navController)
        }
    }
}