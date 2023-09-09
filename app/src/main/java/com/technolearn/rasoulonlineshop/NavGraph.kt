package com.technolearn.rasoulonlineshop

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun SetupNavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Screen.SignUpScreen.route
    ) {
        composable(route = Screen.SignUpScreen.route) {
            SignUpView(navController = navController)
        }

        composable(route = Screen.LoginScreen.route) {
            LoginView(navController = navController)
        }
    }
}