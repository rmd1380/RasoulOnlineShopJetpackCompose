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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import com.skydoves.landscapist.glide.GlideImage
import com.technolearn.rasoulonlineshop.MainActivity
import com.technolearn.rasoulonlineshop.R
import com.technolearn.rasoulonlineshop.helper.CustomButton
import com.technolearn.rasoulonlineshop.helper.LoadingInColumn
import com.technolearn.rasoulonlineshop.helper.ProductItem
import com.technolearn.rasoulonlineshop.navigation.BottomNavigationBar
import com.technolearn.rasoulonlineshop.navigation.Screen
import com.technolearn.rasoulonlineshop.ui.theme.Background
import com.technolearn.rasoulonlineshop.ui.theme.Black
import com.technolearn.rasoulonlineshop.ui.theme.FontBold34
import com.technolearn.rasoulonlineshop.ui.theme.FontRegular11
import com.technolearn.rasoulonlineshop.ui.theme.FontRegular14
import com.technolearn.rasoulonlineshop.ui.theme.Gray
import com.technolearn.rasoulonlineshop.ui.theme.White
import com.technolearn.rasoulonlineshop.util.Extensions.orDefault
import com.technolearn.rasoulonlineshop.vm.ShopViewModel
import com.technolearn.rasoulonlineshop.vo.enums.ButtonSize
import com.technolearn.rasoulonlineshop.vo.enums.ButtonStyle
import com.technolearn.rasoulonlineshop.vo.enums.Status
import com.technolearn.rasoulonlineshop.vo.res.ProductRes
import com.technolearn.rasoulonlineshop.vo.res.SliderRes
import timber.log.Timber


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProductScreen(navController: NavController, viewModel: ShopViewModel) {
    val sliderData by remember { viewModel.getAllSlider() }.observeAsState()
//    val lifecycleOwner= LocalLifecycleOwner.current
    val productRes by remember { viewModel.getAllProduct(0, 10) }.observeAsState()

//    Timber.d("viewModelProducts:::${productList}")
    Scaffold(
        backgroundColor = Background,
        bottomBar = {
            BottomNavigationBar(
                list = MainActivity.navList,
                navController = navController,
            )
        },
        topBar = {

        }

    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                //Slider
                when (sliderData?.status) {
                    Status.LOADING -> {
                        item {
                            LoadingInColumn(
                                Modifier
                                    .fillMaxSize()
                                    .height(536.dp)
                            )
                            Spacer(modifier = Modifier.height(18.dp))
                        }
                        Timber.d("Slider::LOADING${sliderData?.message}")
                    }

                    Status.SUCCESS -> {
                        item {
                            val pagerState =
                                rememberPagerState { sliderData?.data?.data?.size ?: 0 }
                            HorizontalPager(
                                state = pagerState,
                                key = { index: Int -> sliderData?.data?.data?.get(index)?.id.orDefault() },
                            ) { index ->
                                SliderItem(
                                    sliderRes = sliderData?.data?.data?.get(index) ?: SliderRes(),
                                    navController
                                )
                            }
                            Spacer(modifier = Modifier.height(18.dp))
                        }
                        Timber.d("Slider::SUCCESS${sliderData?.data}")

                    }

                    Status.ERROR -> {
                        Timber.d("Slider::ERROR${sliderData?.message}")
                    }

                    else -> {}
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
                                navController.navigate(Screen.MoreProductScreen.passWhatIsTitle("New"))
                            }
                        )
                    }
                    Spacer(modifier = Modifier.height(18.dp))
                }
                //New Product Items
                item {
                    LazyRow(contentPadding = PaddingValues(horizontal = 8.dp)) {

                        when (productRes?.status) {
                            Status.LOADING -> {
                                item {
                                    LoadingInColumn(
                                        Modifier
                                            .fillMaxSize()
                                            .height(536.dp)
                                    )
                                    Spacer(modifier = Modifier.height(18.dp))
                                }
                                Timber.d("ProductNew::LOADING${productRes?.message}")
                            }

                            Status.SUCCESS -> {
                                items(productRes?.data?.data?.filter { it.label == "NEW" }?.size.orDefault()) { index ->
                                    ProductItem(
                                        productRes = productRes?.data?.data?.filter { it.label == "NEW" }
                                            ?.get(index) ?: ProductRes(),
                                        navController,
                                        isLiked = {
                                            viewModel.toggleAddToFavorites(
                                                productRes?.data?.data?.get(
                                                    index
                                                )?.id.orDefault()
                                            )
                                        })
                                }
                                Timber.d("ProductNew::SUCCESS${productRes?.data?.data?.filter { it.label == "NEW" }}")

                            }

                            Status.ERROR -> TODO()

                            else->{}
                        }

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
                                navController.navigate(Screen.MoreProductScreen.passWhatIsTitle("Popular"))
                            }
                        )
                    }
                    Spacer(modifier = Modifier.height(18.dp))
                }
                //Popular Product Items
                item {
                    LazyRow(contentPadding = PaddingValues(horizontal = 8.dp)) {
                        when (productRes?.status) {
                            Status.LOADING -> {
                                item {
                                    LoadingInColumn(
                                        Modifier
                                            .fillMaxSize()
                                            .height(536.dp)
                                    )
                                    Spacer(modifier = Modifier.height(18.dp))
                                }
                                Timber.d("ProductPopular::LOADING${productRes?.message}")
                            }

                            Status.SUCCESS -> {
                                items(
                                    productRes?.data?.data?.filter { it.rate.orDefault() >= 3.5 }?.size
                                        ?: 0
                                ) { index ->
                                    ProductItem(
                                        productRes = productRes?.data?.data?.filter { it.rate.orDefault() >= 3.5 }
                                            ?.sortedByDescending { it.rate }?.get(index)
                                            ?: ProductRes(),
                                        navController = navController,
                                    ) {
                                        viewModel.toggleAddToFavorites(
                                            productRes?.data?.data?.get(
                                                index
                                            )?.id.orDefault()
                                        )
                                    }
                                }
                                Timber.d("ProductPopular::SUCCESS${productRes?.data}")

                            }

                            Status.ERROR -> {
                                Timber.d("ProductPopular::ERROR${productRes?.message}")
                            }

                            else -> {}
                        }

                    }
                }
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
                style = FontBold34(White),
            )
            Text(
                text = sliderRes.subTitle.orDefault(),
                style = FontBold34(White),
            )

            CustomButton(
                onClick = {
                    navController.navigate(
                        route = Screen.ProductDetailScreen.passProductId(
                            sliderRes.id.orDefault()
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





