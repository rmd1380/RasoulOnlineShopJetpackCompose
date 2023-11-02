package com.technolearn.rasoulonlineshop.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.technolearn.rasoulonlineshop.screens.auth.LoginScreen
import com.technolearn.rasoulonlineshop.screens.auth.SignUpScreen
import com.technolearn.rasoulonlineshop.screens.bag.BagScreen
import com.technolearn.rasoulonlineshop.screens.favorites.FavoritesScreen
import com.technolearn.rasoulonlineshop.screens.home.MoreProductScreen
import com.technolearn.rasoulonlineshop.screens.home.ProductDetailScreen
import com.technolearn.rasoulonlineshop.screens.home.ProductScreen
import com.technolearn.rasoulonlineshop.screens.profile.ProfileScreen
import com.technolearn.rasoulonlineshop.screens.shop.ShopScreen
import com.technolearn.rasoulonlineshop.util.Constants
import com.technolearn.rasoulonlineshop.vm.ShopViewModel

@Composable
fun SetupNavGraph(
    navController: NavHostController,
    viewModel: ShopViewModel= hiltViewModel()
) {
    NavHost(
        navController = navController,
        startDestination = NavigationBarItemsGraph.Home.route
    ) {
        composable(route = Screen.SignUpScreen.route) {
//            SignUpScreen(navController = navController)
        }

        composable(
            route = Screen.LoginScreen.route,
        ) {
            LoginScreen(navController = navController)
        }

        composable(route = NavigationBarItemsGraph.Home.route) {
            ProductScreen(navController = navController, viewModel)
        }
        composable(route = NavigationBarItemsGraph.Shop.route) {
            ShopScreen(navController = navController,viewModel)
        }
        composable(route = NavigationBarItemsGraph.Bag.route) {
            BagScreen(navController = navController)
        }
        composable(route = NavigationBarItemsGraph.Favorites.route) {
            FavoritesScreen(navController = navController, viewModel)
        }
        composable(route = NavigationBarItemsGraph.Profile.route) {
            SignUpScreen(navController = navController,viewModel)
//            ProfileScreen(navController = navController)
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
            ProductDetailScreen(navController = navController, productId, viewModel)
        }
        composable(
            route = Screen.MoreProductScreen.route,
            arguments = listOf(
                navArgument(
                    Constants.Argument.WHAT_IS_TITLE
                ) {
                    type = NavType.StringType
                }
            )
        ) {
            val whatIsTitle = it.arguments?.getString(Constants.Argument.WHAT_IS_TITLE)
            MoreProductScreen(navController = navController, viewModel,whatIsTitle)
        }

    }
}