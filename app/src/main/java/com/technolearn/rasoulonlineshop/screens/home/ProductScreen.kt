package com.technolearn.rasoulonlineshop.screens.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.technolearn.rasoulonlineshop.MainActivity
import com.technolearn.rasoulonlineshop.R
import com.technolearn.rasoulonlineshop.helper.CustomButton
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
import com.technolearn.rasoulonlineshop.vo.res.SliderRes
import timber.log.Timber


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProductScreen(navController: NavController, viewModel: ShopViewModel) {
    val testSlider: ArrayList<SliderRes> = arrayListOf()
    testSlider.add(SliderRes(1, R.drawable.test_image_slider1, "", "subtitle1", "slider1"))
    testSlider.add(SliderRes(2, R.drawable.test_image_slider2, "", "subtitle2", "slider2"))
    testSlider.add(SliderRes(3, R.drawable.test_image_slider3, "", "subtitle3", "slider3"))
    testSlider.add(SliderRes(4, R.drawable.test_image_slider4, "", "subtitle4", "slider4"))

    val productList by viewModel.products.observeAsState()
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
                item {
                    val pagerState = rememberPagerState { testSlider.size }
                    HorizontalPager(
                        state = pagerState,
                        key = { index: Int -> testSlider[index].id!!.toInt() },
                    ) { index ->
                        SliderItem(sliderRes = testSlider[index], navController)
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
                                navController.navigate(Screen.MoreProductScreen.passWhatIsTitle("New"))
                            }
                        )
                    }
                    Spacer(modifier = Modifier.height(18.dp))
                }
                //New Product Items
                item {
                    LazyRow(contentPadding = PaddingValues(horizontal = 8.dp)) {
                        items(productList?.filter { it.label == "NEW" }.orEmpty()) { productRes ->
                            ProductItem(productRes = productRes, navController, viewModel)
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
                        items(
                            productList?.filter { it.rate!! >= 3.5 }.orEmpty().reversed()
                        ) { productRes ->
                            ProductItem(
                                productRes = productRes,
                                navController = navController,
                                viewModel
                            )
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
        Image(
            painter = painterResource(id = sliderRes.image ?: R.drawable.ic_cross),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(536.dp),
            contentScale = ContentScale.Crop
        )
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





