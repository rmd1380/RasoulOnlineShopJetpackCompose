package com.technolearn.rasoulonlineshop.screens.shop

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.technolearn.rasoulonlineshop.MainActivity
import com.technolearn.rasoulonlineshop.MainActivity.Companion.minTimeForBeNew
import com.technolearn.rasoulonlineshop.R
import com.technolearn.rasoulonlineshop.helper.CustomTopAppBar
import com.technolearn.rasoulonlineshop.helper.ProductItem
import com.technolearn.rasoulonlineshop.helper.ProductItemHorizontal
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
fun ProductWith3DViewScreen(
    navController: NavController,
    viewModel: ShopViewModel,
) {
    var isList by rememberSaveable { mutableStateOf(false) }
    val context = LocalContext.current

    val productList by remember { viewModel.allProduct }.observeAsState()
    val searchWidgetState by remember { viewModel.searchWidgetState }
    val searchTextState by remember { viewModel.searchTextState }
    var query by remember { mutableStateOf("") }
    LaunchedEffect(Unit) {
        viewModel.fetchAllProduct(0, 160)
    }
    DisposableEffect(Unit) {
        viewModel.resetTextAndState()
        onDispose {
        }
    }
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
                },
                onSearchClicked = {
                    Timber.d("Searched Text::$it")
                    query = it
                },
                title ="3D Product",
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
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 16.dp, start = 16.dp, top = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End
                ) {
                    Icon(
                        painter = if (isList) painterResource(id = R.drawable.ic_view_grid)
                        else painterResource(id = R.drawable.ic_view_list),
                        contentDescription = "ic_view_list",
                        tint = Black,
                        modifier = Modifier
                            .size(24.dp)
                            .clickable(
                                interactionSource = MutableInteractionSource(),
                                indication = null
                            ) {
                                isList = !isList
                            }
                    )
                }
                //ProductItems
                if (isList) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        contentPadding = PaddingValues(bottom = 16.dp)
                    ) {
                        if (query.isEmpty()) {
                            items(productList?.data?.data?.filter { threeDProduct->threeDProduct.threeDModel.orDefault().isNotEmpty() }?.size.orDefault()) { index ->
                                ProductItemHorizontal(
                                    productRes = productList?.data?.data?.filter { threeDProduct->threeDProduct.threeDModel.orDefault().isNotEmpty() }?.get(index) ?: ProductRes(),
                                    navController,
                                    isNew = false,
                                    viewModel = viewModel
                                )
                            }
                        }else{
                            items(productList?.data?.data?.filter { threeDProduct->threeDProduct.threeDModel.orDefault().isNotEmpty() &&
                                    threeDProduct.title?.contains(query, ignoreCase = true).orFalse() }?.size.orDefault()) { index ->
                                ProductItemHorizontal(
                                    productRes = productList?.data?.data?.filter { threeDProduct->threeDProduct.threeDModel.orDefault().isNotEmpty() }?.get(index) ?: ProductRes(),
                                    navController,
                                    isNew = false,
                                    viewModel = viewModel
                                )
                            }
                        }

                    }
                } else {
                    LazyVerticalGrid(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp),
                        columns = GridCells.Fixed(2)
                    ) {
                        if(query.isEmpty()){
                            items(productList?.data?.data?.filter { threeDProduct->threeDProduct.threeDModel.orDefault().isNotEmpty() }?.size.orDefault()) { index ->
                                ProductItem(
                                    productRes =productList?.data?.data?.filter { threeDProduct->threeDProduct.threeDModel.orDefault().isNotEmpty() }?.get(index) ?: ProductRes(),
                                    navController = navController,
                                    isNew = false,
                                    viewModel = viewModel
                                )
                            }
                        }else{
                            items(productList?.data?.data?.filter { threeDProduct->threeDProduct.threeDModel.orDefault().isNotEmpty()&&
                                    threeDProduct.title?.contains(query, ignoreCase = true).orFalse() }?.size.orDefault()) { index ->
                                ProductItem(
                                    productRes =productList?.data?.data?.filter { threeDProduct->threeDProduct.threeDModel.orDefault().isNotEmpty() }?.get(index) ?: ProductRes(),
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
