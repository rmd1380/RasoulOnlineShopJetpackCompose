package com.technolearn.rasoulonlineshop.screens.bag

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.technolearn.rasoulonlineshop.MainActivity
import com.technolearn.rasoulonlineshop.R
import com.technolearn.rasoulonlineshop.helper.CartItem
import com.technolearn.rasoulonlineshop.helper.CustomTopAppBar
import com.technolearn.rasoulonlineshop.helper.LottieComponent
import com.technolearn.rasoulonlineshop.navigation.BottomNavigationBar
import com.technolearn.rasoulonlineshop.navigation.NavigationBarItemsGraph
import com.technolearn.rasoulonlineshop.ui.theme.Background
import com.technolearn.rasoulonlineshop.ui.theme.Black
import com.technolearn.rasoulonlineshop.ui.theme.FontSemiBold18
import com.technolearn.rasoulonlineshop.util.Extensions.orDefault
import com.technolearn.rasoulonlineshop.vm.ShopViewModel
import com.technolearn.rasoulonlineshop.vo.model.helperComponent.CustomAction
import timber.log.Timber

@Composable
fun BagScreen(navController: NavController, viewModel: ShopViewModel) {
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.getAllUserCart()
    }
    val productInCartList by remember { viewModel.userCartLiveData }.observeAsState()
    Timber.d("productInCartListBagScreen:::$productInCartList")
    Scaffold(
        backgroundColor = Background,
        bottomBar = {
            BottomNavigationBar(
                list = MainActivity.navList,
                navController = navController,
            )
        },
        topBar = {
            CustomTopAppBar(
                title = context.getString(R.string.my_bag),
                style = FontSemiBold18(Black),
                navigationIcon = ImageVector.vectorResource(R.drawable.ic_chevron_back),
                actionIcons = listOf(
                    CustomAction(
                        "search",
                        ImageVector.vectorResource(R.drawable.ic_search)
                    )
                ),
                navigationOnClick = {
                    navController.navigate(NavigationBarItemsGraph.Home.route) {
                        popUpTo(NavigationBarItemsGraph.Home.route) {
                            inclusive = true
                        }
                    }
                },
                actionOnclick = { /*TODO*/ }
            )
        }

    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            if (productInCartList.isNullOrEmpty()) {
                LottieComponent(R.raw.empty_bag)
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        contentPadding = PaddingValues(bottom = 16.dp)
                    ) {
                        items(productInCartList?.size.orDefault()) { index ->
                            CartItem(
                                userCartEntity = productInCartList?.get(index),
                                navController = navController,
                                viewModel = viewModel,
                                more = {}
                            )
                        }
                    }
                }
            }

        }
    }
}