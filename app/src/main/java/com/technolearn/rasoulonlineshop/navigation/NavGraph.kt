package com.technolearn.rasoulonlineshop.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.technolearn.rasoulonlineshop.screens.auth.LoginScreen
import com.technolearn.rasoulonlineshop.screens.auth.LoginState
import com.technolearn.rasoulonlineshop.screens.auth.SignUpScreen
import com.technolearn.rasoulonlineshop.screens.bag.BagScreen
import com.technolearn.rasoulonlineshop.screens.bag.CheckOutScreen
import com.technolearn.rasoulonlineshop.screens.favorites.FavoritesScreen
import com.technolearn.rasoulonlineshop.screens.home.MoreProductScreen
import com.technolearn.rasoulonlineshop.screens.home.ProductARScreen
import com.technolearn.rasoulonlineshop.screens.home.ProductDetailScreen
import com.technolearn.rasoulonlineshop.screens.home.ProductScreen
import com.technolearn.rasoulonlineshop.screens.profile.AddShippingAddressScreen
import com.technolearn.rasoulonlineshop.screens.profile.OrderScreen
import com.technolearn.rasoulonlineshop.screens.profile.PaymentMethodsScreen
import com.technolearn.rasoulonlineshop.screens.profile.ProfileScreen
import com.technolearn.rasoulonlineshop.screens.profile.SettingsScreen
import com.technolearn.rasoulonlineshop.screens.profile.ShippingAddressScreen
import com.technolearn.rasoulonlineshop.screens.shop.ProductByCategoryScreen
import com.technolearn.rasoulonlineshop.screens.shop.ShopScreen
import com.technolearn.rasoulonlineshop.util.Constants
import com.technolearn.rasoulonlineshop.util.Extensions.orDefault
import com.technolearn.rasoulonlineshop.util.Extensions.orFalse
import com.technolearn.rasoulonlineshop.vm.ShopViewModel
import timber.log.Timber

@Composable
fun SetupNavGraph(
    navController: NavHostController,
    viewModel: ShopViewModel = hiltViewModel()
) {
    NavHost(
        navController = navController,
        startDestination = NavigationBarItemsGraph.Home.route
    ) {
        composable(
            route = Screen.LoginScreen.route,
        ) {
            LoginScreen(navController = navController, viewModel)
        }

        composable(route = NavigationBarItemsGraph.Home.route) {
            ProductScreen(navController = navController, viewModel)
        }
        composable(route = NavigationBarItemsGraph.Shop.route) {
            ShopScreen(navController = navController, viewModel)
        }
        composable(route = NavigationBarItemsGraph.Bag.route) {
            BagScreen(navController = navController, viewModel)
        }
        composable(route = NavigationBarItemsGraph.Favorites.route) {
            FavoritesScreen(navController = navController, viewModel)
        }
        composable(route = NavigationBarItemsGraph.Profile.route) {
            val isLoggedIn by remember {viewModel.isLoggedIn}.observeAsState()
            Timber.d("isLoggedInNav:::$isLoggedIn")
            if (isLoggedIn.orFalse()) {
                ProfileScreen(navController = navController,viewModel)
            } else {
                SignUpScreen(navController = navController, viewModel)
            }
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
            MoreProductScreen(navController = navController, viewModel, whatIsTitle)
        }

        composable(
            route = Screen.ProductByCategoryScreen.route,
            arguments = listOf(
                navArgument(
                    Constants.Argument.CATEGORY_ID
                ) {
                    type = NavType.IntType
                }
            )
        ) {
            val categoryId = it.arguments?.getInt(Constants.Argument.CATEGORY_ID)
            ProductByCategoryScreen(
                navController = navController,
                viewModel = viewModel,
                categoryId = categoryId.orDefault()
            )
        }

        composable(
            route = Screen.ProductARScreen.route,
        ) {
            ProductARScreen(navController = navController)
        }

        composable(
            route = Screen.ShippingAddressScreen.route,
        ) {
            ShippingAddressScreen(navController = navController, viewModel)
        }

        composable(
            route = Screen.AddShippingAddressScreen.route,
        ) {
            AddShippingAddressScreen(navController = navController, viewModel)
        }
        composable(
            route = Screen.OrderScreen.route,
        ) {
            OrderScreen(navController = navController, viewModel)
        }

        composable(
            route = Screen.PaymentMethodsScreen.route,
        ) {
            PaymentMethodsScreen(navController = navController, viewModel)
        }

        composable(
            route = Screen.CheckOutScreen.route,
        ) {
            CheckOutScreen(navController = navController, viewModel)
        }

        composable(
            route = Screen.SettingsScreen.route,
        ) {
            SettingsScreen(navController = navController, viewModel)
        }
    }
}