package com.technolearn.rasoulonlineshop.screens.shop

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.progressSemantics
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.skydoves.landscapist.glide.GlideImage
import com.technolearn.rasoulonlineshop.MainActivity
import com.technolearn.rasoulonlineshop.R
import com.technolearn.rasoulonlineshop.helper.CustomTopAppBar
import com.technolearn.rasoulonlineshop.helper.LottieComponent
import com.technolearn.rasoulonlineshop.navigation.BottomNavigationBar
import com.technolearn.rasoulonlineshop.navigation.NavigationBarItemsGraph
import com.technolearn.rasoulonlineshop.navigation.Screen
import com.technolearn.rasoulonlineshop.ui.theme.Background
import com.technolearn.rasoulonlineshop.ui.theme.Black
import com.technolearn.rasoulonlineshop.ui.theme.FontMedium14
import com.technolearn.rasoulonlineshop.ui.theme.FontSemiBold18
import com.technolearn.rasoulonlineshop.ui.theme.FontSemiBold24
import com.technolearn.rasoulonlineshop.ui.theme.Primary
import com.technolearn.rasoulonlineshop.ui.theme.White
import com.technolearn.rasoulonlineshop.util.Extensions.orDefault
import com.technolearn.rasoulonlineshop.util.Extensions.orFalse
import com.technolearn.rasoulonlineshop.vm.ShopViewModel
import com.technolearn.rasoulonlineshop.vo.enums.SearchWidgetState
import com.technolearn.rasoulonlineshop.vo.enums.Status
import com.technolearn.rasoulonlineshop.vo.model.helperComponent.CustomAction
import com.technolearn.rasoulonlineshop.vo.res.CategoryRes
import timber.log.Timber

@Composable
fun ShopScreen(navController: NavController, viewModel: ShopViewModel) {

    val category by remember { viewModel.allCategory }.observeAsState()
    val searchWidgetState by remember { viewModel.searchWidgetState }
    val searchTextState by remember { viewModel.searchTextState }
    var query by remember { mutableStateOf("") }
    LaunchedEffect(Unit) {
        viewModel.fetchAllCategory()
    }
    DisposableEffect(Unit) {
        viewModel.resetTextAndState()
        onDispose {
        }
    }
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
                title = stringResource(R.string.categories),
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
                },
            )
        }

    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            when (category?.status) {
                Status.LOADING -> {
                    LottieComponent(resId = R.raw.main_progress)
                }

                Status.SUCCESS -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        //Ads
                        item {
                            Card(
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(8.dp))
                                    .padding(horizontal = 16.dp, vertical = 16.dp)
                                    .clickable {
                                        navController.navigate(Screen.ProductWith3DViewScreen.route)
                                    },
                                elevation = 2.dp,
                                backgroundColor = Primary
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 28.dp)
                                ) {
                                    Text(
                                        text = "PRODUCT IN 3D",
                                        style = FontSemiBold24(White),
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(
                                        text = "see product in 3D view",
                                        style = FontMedium14(White),
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                }
                            }
                        }
                        //Categories
                        if (query.isEmpty()) {
                            items(category?.data?.data?.size.orDefault()) { categoryRes ->
                                CategoryItem(
                                    categoryRes = category?.data?.data?.get(categoryRes)
                                        ?: CategoryRes(),
                                    navController
                                )
                            }
                        } else {
                            items(category?.data?.data?.filter { categoryRes ->
                                categoryRes.title?.contains(query, ignoreCase = true).orFalse()
                            }?.size.orDefault()) { categoryRes ->
                                CategoryItem(
                                    categoryRes = category?.data?.data?.filter { category ->
                                        category.title?.contains(query, ignoreCase = true).orFalse()
                                    }?.get(categoryRes) ?: CategoryRes(),
                                    navController
                                )
                            }
//                            items(category?.data?.data?.filter { categoryRes ->
//                                categoryRes.title?.contains(
//                                    query
//                                ).orFalse()
//                            }?.size.orDefault()) { categoryRes ->
//                                CategoryItem(
//                                    categoryRes = category?.data?.data?.filter { category ->
//                                        category.title?.contains(
//                                            query
//                                        ).orFalse()
//                                    }?.get(categoryRes) ?: CategoryRes(),
//                                    navController
//                                )
//                            }
                        }
                    }
                }
                Status.ERROR -> {}
                else -> {}
            }

        }
    }
}

@Composable
fun CategoryItem(categoryRes: CategoryRes, navController: NavController) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .aspectRatio(343f / 100f, true)
            .height(100.dp)
            .padding(horizontal = 16.dp, vertical = 16.dp)
            .clickable {
                navController.navigate(Screen.ProductByCategoryScreen.passCategoryId(categoryId = categoryRes.id.orDefault())) {
                    restoreState = false
                }
            },
        elevation = 2.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = categoryRes.title.orDefault(),
                style = FontSemiBold18(Black),
                modifier = Modifier
                    .weight(1.5f)
                    .padding(start = 20.dp),
            )
            GlideImage(
                imageModel = categoryRes.image.orDefault(),
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(topEnd = 8.dp, bottomEnd = 8.dp)),
                contentScale = ContentScale.Crop,
                loading = {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(15.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .progressSemantics()
                                .size(24.dp),
                            color = Black,
                            strokeWidth = 2.dp
                        )
                    }
                },
                // shows an error text message when request failed.
                failure = {
                    Text(text = "image request failed.")
                })
        }
    }
}
