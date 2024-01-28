package com.technolearn.rasoulonlineshop.screens.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.technolearn.rasoulonlineshop.MainActivity
import com.technolearn.rasoulonlineshop.MainActivity.Companion.minTimeForBeNew
import com.technolearn.rasoulonlineshop.R
import com.technolearn.rasoulonlineshop.helper.CustomTopAppBar
import com.technolearn.rasoulonlineshop.helper.LottieComponent
import com.technolearn.rasoulonlineshop.helper.ProductItem
import com.technolearn.rasoulonlineshop.navigation.BottomNavigationBar
import com.technolearn.rasoulonlineshop.navigation.NavigationBarItemsGraph
import com.technolearn.rasoulonlineshop.ui.theme.Background
import com.technolearn.rasoulonlineshop.ui.theme.Black
import com.technolearn.rasoulonlineshop.ui.theme.FontSemiBold18
import com.technolearn.rasoulonlineshop.util.Extensions.orDefault
import com.technolearn.rasoulonlineshop.util.Extensions.orFalse
import com.technolearn.rasoulonlineshop.vm.ShopViewModel
import com.technolearn.rasoulonlineshop.vo.enums.SearchWidgetState
import com.technolearn.rasoulonlineshop.vo.model.helperComponent.CustomAction
import com.technolearn.rasoulonlineshop.vo.res.ProductRes
import timber.log.Timber

@Composable
fun SearchProductScreen(
    navController: NavController,
    viewModel: ShopViewModel,
    whatIsTitleForSearch: String?
) {
    val productList by remember { viewModel.allProduct }.observeAsState()
    val searchWidgetState by remember { viewModel.searchWidgetState }
    val searchTextState by remember { viewModel.searchTextState }
    var query by remember { mutableStateOf("") }
    var queryFromAi by remember { mutableStateOf(whatIsTitleForSearch) }
    LaunchedEffect(Unit) {
        viewModel.fetchAllProduct(0, 160)
    }
    DisposableEffect(Unit) {
        viewModel.resetTextAndState()
        onDispose {
        }
    }
    Timber.d("whatIsTitleForSearch::$whatIsTitleForSearch")
    Scaffold(
        backgroundColor = Background,
        bottomBar = {},
        topBar = {
            CustomTopAppBar(
                searchWidgetState = searchWidgetState,
                searchTextState = searchTextState,
                onTextChange = {
                    viewModel.updateSearchTextState(newValue = it)
                },
                onCloseClicked = {
                    viewModel.updateSearchWidgetState(newValue = SearchWidgetState.CLOSED)
                    query=""
                    queryFromAi=""
                },
                onSearchClicked = {
                    Timber.d("Searched Text::$it")
                    query = it
                },
                title = "SEARCH",
                style = FontSemiBold18(Black),
                navigationIcon = ImageVector.vectorResource(R.drawable.ic_chevron_back),
                actionIcons = listOf(
                    CustomAction(
                        "search",
                        painterResource(id = R.drawable.ic_search)
                    )
                ),
                navigationOnClick = {
                    navController.navigate(NavigationBarItemsGraph.Home.route) {
                        popUpTo(NavigationBarItemsGraph.Home.route) {
                            inclusive = true
                        }
                    }
                },
                actionOnclick = {
                    when (it.name) {
                        "search" -> {
                            viewModel.updateSearchWidgetState(newValue = SearchWidgetState.OPENED)
                        }
                    }
                }
            )
        }
    ) {
        if (productList?.data?.data?.none { productRes ->
                productRes.title?.contains(
                    queryFromAi.orDefault()
                ).orFalse()
            }.orFalse()){
            LottieComponent(R.raw.empty_list)
        }else{
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    //ProductItems
                    LazyVerticalGrid(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp),
                        columns = GridCells.Fixed(2)
                    ) {
                        if(query.isEmpty()){
                            if(queryFromAi.orDefault().isEmpty()){
                                items(productList?.data?.data?.size.orDefault()) { index ->
                                    ProductItem(
                                        productRes = productList?.data?.data?.get(index) ?: ProductRes(),
                                        navController = navController,
                                        isNew = false,
                                        viewModel = viewModel
                                    )
                                }
                            }else{
                                items(productList?.data?.data?.filter { productRes ->
                                    productRes.title?.contains(
                                        queryFromAi.orDefault(),ignoreCase = true
                                    ).orFalse()
                                }?.size.orDefault()) { index ->
                                    ProductItem(
                                        productRes = productList?.data?.data?.filter { productRes ->
                                            productRes.title?.contains(
                                                queryFromAi.orDefault()
                                            ).orFalse()
                                        }?.get(index) ?: ProductRes(),
                                        navController = navController,
                                        isNew = false,
                                        viewModel = viewModel
                                    )
                                }
                            }
                        }else{
                            items(productList?.data?.data?.filter { productRes ->
                                productRes.title?.contains(query, ignoreCase = true).orFalse()
                            }?.size.orDefault()) { index ->
                                ProductItem(
                                    productRes = productList?.data?.data?.filter { productRes ->
                                        productRes.title?.contains(
                                            query.orDefault(), ignoreCase = true
                                        ).orFalse()
                                    }?.get(index) ?: ProductRes(),
                                    navController = navController,
                                    isNew = false,
                                    viewModel = viewModel
                                )
                            }
                        }

                    }
                }
            }
        }
    }
}
