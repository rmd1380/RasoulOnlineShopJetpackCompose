package com.technolearn.rasoulonlineshop.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.technolearn.rasoulonlineshop.screens.auth.LoginScreen
import com.technolearn.rasoulonlineshop.screens.home.ProductDetailScreen
import com.technolearn.rasoulonlineshop.screens.home.ProductScreen
import com.technolearn.rasoulonlineshop.screens.auth.SignUpScreen
import com.technolearn.rasoulonlineshop.screens.bag.BagScreen
import com.technolearn.rasoulonlineshop.screens.favorites.FavoritesScreen
import com.technolearn.rasoulonlineshop.screens.profile.ProfileScreen
import com.technolearn.rasoulonlineshop.screens.shop.ShopScreen
import com.technolearn.rasoulonlineshop.util.Constants

@Composable
fun SetupNavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = NavigationBarItemsGraph.Home.route
    ) {
        composable(route = Screen.SignUpScreen.route) {
            SignUpScreen(navController = navController)
        }

        composable(
            route = Screen.LoginScreen.route,
        ) {
            LoginScreen(navController = navController)
        }

        composable(route = NavigationBarItemsGraph.Home.route) {
            ProductScreen(navController = navController)
        }
        composable(route = NavigationBarItemsGraph.Shop.route) {
            ShopScreen(navController = navController)
        }
        composable(route = NavigationBarItemsGraph.Bag.route) {
            BagScreen(navController = navController)
        }
        composable(route = NavigationBarItemsGraph.Favorites.route) {
            FavoritesScreen(navController = navController)
        }
        composable(route = NavigationBarItemsGraph.Profile.route) {
            ProfileScreen(navController = navController)
        }
        composable(
            route = Screen.ProductDetailScreen.route,
            arguments = listOf(
                navArgument(
                    Constants.Argument.PRODUCT_ID
                ) {
                    type = NavType.IntType
                }
            )
        ) {
            val productId = it.arguments?.getInt(Constants.Argument.PRODUCT_ID)
            ProductDetailScreen(navController = navController, productId)
        }

    }
}