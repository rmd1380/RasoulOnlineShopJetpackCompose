package com.technolearn.rasoulonlineshop.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
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
import com.technolearn.rasoulonlineshop.ui.theme.FontRegular11
import com.technolearn.rasoulonlineshop.ui.theme.FontRegular16
import com.technolearn.rasoulonlineshop.ui.theme.FontSemiBold18
import com.technolearn.rasoulonlineshop.util.Extensions.orDefault
import com.technolearn.rasoulonlineshop.vm.ShopViewModel
import com.technolearn.rasoulonlineshop.vo.model.helperComponent.CustomAction
import com.technolearn.rasoulonlineshop.vo.res.ProductRes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoreProductScreen(
    navController: NavController,
    viewModel: ShopViewModel,
    whatIsTitle: String?
) {
    var isList by rememberSaveable { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()
    var showFilterOptionsBottomSheet by rememberSaveable { mutableStateOf(false) }
    val context = LocalContext.current
    val filterOptions = arrayListOf(
        stringResource(id = R.string.popular),
        stringResource(id = R.string.newest),
        stringResource(id = R.string.lowest_to_high),
        stringResource(id = R.string.highest_to_low),
    )
    var selectedFilterText by rememberSaveable {
        mutableStateOf(
            when (whatIsTitle) {
                context.getString(R.string.str_new) -> context.getString(R.string.newest)
                context.getString(R.string.popular) -> context.getString(R.string.popular)
                else -> ""
            }
        )
    }

    val productList by remember { viewModel.allProduct }.observeAsState()
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
                title = when (whatIsTitle) {
                    stringResource(id = R.string.str_new) -> {
                        stringResource(id = R.string.str_new)
                    }

                    stringResource(id = R.string.popular) -> {
                        stringResource(id = R.string.popular)
                    }

                    else -> ""
                },
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
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                //Filter
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 16.dp, start = 16.dp, top = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .clickable(
                                interactionSource = MutableInteractionSource(),
                                indication = null
                            ) {
                                showFilterOptionsBottomSheet = true
                            }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_swap_vert),
                            contentDescription = "ic_swap_vert",
                            tint = Black,
                            modifier = Modifier
                                .size(24.dp)
                                .clickable(
                                    interactionSource = MutableInteractionSource(),
                                    indication = null
                                ) {
                                    showFilterOptionsBottomSheet = true
                                }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = selectedFilterText,
                            style = FontRegular11(Black),
                            modifier = Modifier
                                .clickable(
                                    interactionSource = MutableInteractionSource(),
                                    indication = null
                                ) {
                                    showFilterOptionsBottomSheet = true
                                }
                        )
                    }
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
                if (showFilterOptionsBottomSheet) {
                    ModalBottomSheet(
                        sheetState = sheetState,
                        onDismissRequest = {
                            showFilterOptionsBottomSheet = false
                        }
                    ) {
                        Text(
                            text = stringResource(R.string.sort_by),
                            style = FontSemiBold18(Black),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(32.dp))
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 32.dp, start = 16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            items(filterOptions.size) { index ->
                                Text(
                                    text = filterOptions[index],
                                    style = FontRegular16(Black),
                                    textAlign = TextAlign.Start,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            showFilterOptionsBottomSheet =
                                                !showFilterOptionsBottomSheet
                                            selectedFilterText = filterOptions[index]
                                        }
                                )
                                Spacer(modifier = Modifier.height(32.dp))
                            }
                        }
                    }
                }

                //ProductItems
                if (isList) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        contentPadding = PaddingValues(bottom = 16.dp)
                    ) {
                        when (selectedFilterText) {
                            context.getString(R.string.newest) -> {
                                items(productList?.data?.data?.size.orDefault()) { index ->
                                    ProductItemHorizontal(
                                        productRes = productList?.data?.data?.sortedByDescending { productRes -> productRes.addDate }
                                            ?.get(index) ?: ProductRes(),
                                        navController,
                                        isNew = productList?.data?.data?.sortedByDescending { productRes -> productRes.addDate }
                                            ?.get(index)?.addDate.orDefault() > minTimeForBeNew,
                                        viewModel = viewModel
                                    )
                                }
                            }

                            context.getString(R.string.popular) -> {
                                items(productList?.data?.data?.filter { productRes -> productRes.rate.orDefault() >= 3.5 }?.size.orDefault()) { index ->
                                    ProductItemHorizontal(
                                        productRes = productList?.data?.data?.filter { productRes -> productRes.rate.orDefault() >= 3.5 }
                                            ?.sortedByDescending { productRes -> productRes.rate }
                                            ?.get(index)
                                            ?: ProductRes(),
                                        navController = navController,
                                        isNew = false,
                                        viewModel = viewModel
                                    )
                                }
                            }

                            context.getString(R.string.highest_to_low) -> {
                                items(productList?.data?.data?.size.orDefault()) { index ->
                                    ProductItemHorizontal(
                                        productRes = productList?.data?.data?.sortedByDescending { productRes -> productRes.price }
                                            ?.get(index) ?: ProductRes(),
                                        navController = navController,
                                        isNew = false,
                                        viewModel = viewModel
                                    )
                                }
                            }

                            context.getString(R.string.lowest_to_high) -> {
                                items(productList?.data?.data?.size.orDefault()) { index ->
                                    ProductItemHorizontal(
                                        productRes = productList?.data?.data?.sortedBy { productRes -> productRes.price }
                                            ?.get(index) ?: ProductRes(),
                                        navController = navController,
                                        isNew = false,
                                        viewModel = viewModel
                                    )
                                }
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
                        when (selectedFilterText) {
                            context.getString(R.string.newest) -> {
                                items(productList?.data?.data?.size.orDefault()) { index ->
                                    ProductItem(
                                        productRes = productList?.data?.data?.get(index)
                                            ?: ProductRes(),
                                        navController = navController,
                                        isNew = productList?.data?.data?.sortedByDescending { productRes -> productRes.addDate }
                                            ?.get(index)?.addDate.orDefault() > minTimeForBeNew,
                                        viewModel = viewModel
                                    )
                                }
                            }

                            context.getString(R.string.popular) -> {
                                items(productList?.data?.data?.filter { productRes -> productRes.rate.orDefault() >= 3.5 }?.size.orDefault()) { index ->
                                    ProductItem(
                                        productRes = productList?.data?.data?.filter { productRes -> productRes.rate.orDefault() >= 3.5 }
                                            ?.sortedByDescending { productRes -> productRes.rate }
                                            ?.get(index)
                                            ?: ProductRes(),
                                        navController = navController,
                                        isNew = false,
                                        viewModel = viewModel
                                    )
                                }
                            }

                            context.getString(R.string.highest_to_low) -> {
                                items(productList?.data?.data?.size.orDefault()) { index ->
                                    ProductItem(
                                        productRes = productList?.data?.data?.sortedByDescending { productRes -> productRes.price }
                                            ?.get(index) ?: ProductRes(),
                                        navController = navController,
                                        isNew = false,
                                        viewModel = viewModel
                                    )
                                }
                            }

                            context.getString(R.string.lowest_to_high) -> {
                                items(productList?.data?.data?.size.orDefault()) { index ->
                                    ProductItem(
                                        productRes = productList?.data?.data?.sortedBy { productRes -> productRes.price }
                                            ?.get(index) ?: ProductRes(),
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
}
