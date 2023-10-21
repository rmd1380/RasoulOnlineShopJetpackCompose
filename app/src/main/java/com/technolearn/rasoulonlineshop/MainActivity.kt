package com.technolearn.rasoulonlineshop

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.technolearn.rasoulonlineshop.navigation.NavigationBarItemsGraph
import com.technolearn.rasoulonlineshop.navigation.SetupNavGraph
import com.technolearn.rasoulonlineshop.ui.theme.RasoulOnlineShopTheme
import com.technolearn.rasoulonlineshop.vm.ShopViewModel
import com.technolearn.rasoulonlineshop.vo.model.BottomNavigationItem
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var navController: NavHostController
    private val viewModel: ShopViewModel by viewModels()
    companion object{
        val navList = listOf(
            BottomNavigationItem(
                "Home",
                NavigationBarItemsGraph.Home.route,
                NavigationBarItemsGraph.Home.selectedIcon,
                NavigationBarItemsGraph.Home.unSelectedIcon,
            ),
            BottomNavigationItem(
                "Shop",
                NavigationBarItemsGraph.Shop.route,
                NavigationBarItemsGraph.Shop.selectedIcon,
                NavigationBarItemsGraph.Shop.unSelectedIcon,
            ),
            BottomNavigationItem(
                "Bag",
                NavigationBarItemsGraph.Bag.route,
                NavigationBarItemsGraph.Bag.selectedIcon,
                NavigationBarItemsGraph.Bag.unSelectedIcon,
                5
            ),
            BottomNavigationItem(
                "Favorites",
                NavigationBarItemsGraph.Favorites.route,
                NavigationBarItemsGraph.Favorites.selectedIcon,
                NavigationBarItemsGraph.Favorites.unSelectedIcon,
            ),
            BottomNavigationItem(
                "Profile",
                NavigationBarItemsGraph.Profile.route,
                NavigationBarItemsGraph.Profile.selectedIcon,
                NavigationBarItemsGraph.Profile.unSelectedIcon,
            )
        )
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            navController = rememberNavController()

            RasoulOnlineShopTheme {
                Scaffold(
                    bottomBar = {

                    }
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(it)
                    ) {
                        SetupNavGraph(navController = navController,viewModel)
                    }
                }
                // A surface container using the 'background' color from the theme
//                Surface(
//                    modifier = Modifier.fillMaxSize(),
//                    color = MaterialTheme.colors.background
//                ) {
//
//                }
            }
        }
    }
}





