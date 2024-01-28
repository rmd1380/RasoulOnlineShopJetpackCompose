package com.technolearn.rasoulonlineshop.screens.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.progressSemantics
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.skydoves.landscapist.glide.GlideImage
import com.technolearn.rasoulonlineshop.MainActivity
import com.technolearn.rasoulonlineshop.MainActivity.Companion.minTimeForBeNew
import com.technolearn.rasoulonlineshop.R
import com.technolearn.rasoulonlineshop.helper.CustomButton
import com.technolearn.rasoulonlineshop.helper.CustomTopAppBar
import com.technolearn.rasoulonlineshop.helper.LottieComponent
import com.technolearn.rasoulonlineshop.helper.ProductItem
import com.technolearn.rasoulonlineshop.mapper.toSliderRes
import com.technolearn.rasoulonlineshop.navigation.BottomNavigationBar
import com.technolearn.rasoulonlineshop.navigation.NavigationBarItemsGraph
import com.technolearn.rasoulonlineshop.navigation.Screen
import com.technolearn.rasoulonlineshop.ui.theme.Background
import com.technolearn.rasoulonlineshop.ui.theme.Black
import com.technolearn.rasoulonlineshop.ui.theme.FontBold34
import com.technolearn.rasoulonlineshop.ui.theme.FontRegular11
import com.technolearn.rasoulonlineshop.ui.theme.FontRegular14
import com.technolearn.rasoulonlineshop.ui.theme.FontSemiBold18
import com.technolearn.rasoulonlineshop.ui.theme.Gray
import com.technolearn.rasoulonlineshop.ui.theme.Primary
import com.technolearn.rasoulonlineshop.ui.theme.White
import com.technolearn.rasoulonlineshop.util.Extensions.orDefault
import com.technolearn.rasoulonlineshop.util.Extensions.toHumanReadableDate
import com.technolearn.rasoulonlineshop.vm.ShopViewModel
import com.technolearn.rasoulonlineshop.vo.enums.ButtonSize
import com.technolearn.rasoulonlineshop.vo.enums.ButtonStyle
import com.technolearn.rasoulonlineshop.vo.enums.Status
import com.technolearn.rasoulonlineshop.vo.model.helperComponent.CustomAction
import com.technolearn.rasoulonlineshop.vo.res.ProductRes
import com.technolearn.rasoulonlineshop.vo.res.SliderRes
import timber.log.Timber

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProductScreen(navController: NavController, viewModel: ShopViewModel) {
    val productList by remember { viewModel.allProduct }.observeAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchAllProduct(0, 160)
    }
    Timber.d("viewModelProducts:::${productList}")
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
                title = stringResource(R.string.buy_now),
                style = FontSemiBold18(Black),
                actionIcons = listOf(
                    CustomAction(
                        "visualSearch",
                        painterResource(id = R.drawable.ic_visual_search),
                    )
                ),
                navigationOnClick = {},
                actionOnclick = {
                    when (it.name) {
                        "visualSearch" -> {
                            navController.navigate(Screen.VisualSearchScreen.route)
                        }
                    }
                },
                tint = Primary
            )
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            when (productList?.status) {
                Status.LOADING -> {
                    LottieComponent(R.raw.main_progress)
                }

                Status.SUCCESS -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Timber.d("SUCCESSSUCCESSProduct:::$productList")
                        //Slider
                        item {
                            val pagerState = rememberPagerState {
                                minOf(
                                    productList?.data?.data?.filter { productRes -> productRes.rate.orDefault() >= 4 }?.size.orDefault(),
                                    7
                                )
                            }
                            HorizontalPager(
                                state = pagerState,
                                key = { index: Int ->
                                    productList?.data?.data?.filter { productRes -> productRes.rate.orDefault() >= 4 }
                                        ?.get(index)?.id.orDefault()
                                },
                            ) { index ->
                                SliderItem(
                                    sliderRes = toSliderRes(productList?.data?.data?.filter { productRes -> productRes.rate.orDefault() >= 4 }
                                        ?.get(index) ?: ProductRes()),
                                    navController
                                )
                            }
                            Spacer(modifier = Modifier.height(18.dp))
                        }
                        //SomeText New-You've never seen it before!-View all
                        item {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(end = 14.dp, start = 18.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column {
                                    Text(
                                        text = stringResource(R.string.str_new),
                                        style = FontBold34(Black),
                                    )
                                    Text(
                                        text = stringResource(R.string.you_ve_never_seen_it_before),
                                        style = FontRegular11(Gray),
                                    )
                                }
                                Text(
                                    text = stringResource(R.string.view_all),
                                    style = FontRegular14(Black),
                                    modifier = Modifier.clickable {
                                        navController.navigate(
                                            Screen.MoreProductScreen.passWhatIsTitle(
                                                "New"
                                            )
                                        )
                                    }
                                )
                            }
                            Spacer(modifier = Modifier.height(18.dp))
                        }
                        //New Product Items
                        item {
                            LazyRow(contentPadding = PaddingValues(horizontal = 8.dp)) {
                                items(5) { index ->
                                    ProductItem(
                                        productRes = productList?.data?.data?.sortedByDescending { productRes -> productRes.addDate }
                                            ?.get(index) ?: ProductRes(),
                                        navController = navController,
                                        isNew = productList?.data?.data?.sortedByDescending { productRes -> productRes.addDate }
                                            ?.get(index)?.addDate.orDefault() > minTimeForBeNew,
                                        viewModel = viewModel
                                    )
//                                    isLiked = {
//                                        viewModel.toggleAddToFavorites(
//                                            productList?.data?.data?.sortedByDescending { productRes -> productRes.addDate }?.get(index)?.id.orDefault()
//                                        )
//                                    }
                                }
                                Timber.d("ProductNew::SUCCESS${productList?.data?.data?.sortedByDescending { productRes -> productRes.addDate }}")
                                Timber.d("minTimeForBeNew${minTimeForBeNew.toHumanReadableDate()}")
                            }
                            Spacer(modifier = Modifier.height(18.dp))
                        }
                        //SomeText Popular-Best products ever-View all
                        item {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(end = 14.dp, start = 18.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column {
                                    Text(
                                        text = stringResource(R.string.popular),
                                        style = FontBold34(Black),
                                    )
                                    Text(
                                        text = stringResource(R.string.best_products_ever),
                                        style = FontRegular11(Gray),
                                    )
                                }
                                Text(
                                    text = stringResource(id = R.string.view_all),
                                    style = FontRegular14(Black),
                                    modifier = Modifier.clickable {
                                        navController.navigate(
                                            Screen.MoreProductScreen.passWhatIsTitle(
                                                "Popular"
                                            )
                                        )
                                    }
                                )
                            }
                            Spacer(modifier = Modifier.height(18.dp))
                        }
                        //Popular Product Items
                        item {
                            LazyRow(contentPadding = PaddingValues(horizontal = 8.dp)) {
                                items(5) { index ->
                                    ProductItem(
                                        productRes = productList?.data?.data?.sortedByDescending { productRes -> productRes.rate }
                                            ?.getOrNull(index) ?: ProductRes(),
                                        navController = navController,
                                        isNew = false,
                                        viewModel = viewModel
                                    )
//                                    {
//                                        viewModel.toggleAddToFavorites(
//                                            productList?.data?.data?.sortedByDescending { productRes -> productRes.rate }?.get(index)?.id.orDefault()
//                                        )
//                                    }
                                }
                            }
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
fun SliderItem(sliderRes: SliderRes, navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        GlideImage(
            imageModel = sliderRes.image ?: "",
            modifier = Modifier
                .fillMaxWidth()
                .height(536.dp),
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
        Column(
            modifier = Modifier
                .height(536.dp)
                .padding(16.dp),
            verticalArrangement = Arrangement.Bottom
        ) {
            Text(
                text = sliderRes.title.orDefault(),
                style = FontBold34(Black),
            )
            Text(
                text = sliderRes.subTitle.orDefault(),
                style = FontBold34(Black),
            )

            CustomButton(
                onClick = {
                    navController.navigate(
                        route = Screen.ProductDetailScreen.passProductIdAndCategoryId(
                            sliderRes.id.orDefault(),
                            sliderRes.category?.id.orDefault()
                        )
                    )
                },
                modifier = Modifier
                    .padding(top = 16.dp),
                text = stringResource(R.string.check),
                style = ButtonStyle.CONTAINED,
                size = ButtonSize.SMALL,
                roundCorner = 25.dp,
                buttonTextStyle = FontRegular14(White)
            )
        }
    }
}





