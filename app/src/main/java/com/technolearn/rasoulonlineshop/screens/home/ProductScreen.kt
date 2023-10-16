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
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.technolearn.rasoulonlineshop.MainActivity
import com.technolearn.rasoulonlineshop.R
import com.technolearn.rasoulonlineshop.helper.AddToFavorite
import com.technolearn.rasoulonlineshop.helper.CustomButton
import com.technolearn.rasoulonlineshop.helper.CustomRatingBar
import com.technolearn.rasoulonlineshop.helper.Label
import com.technolearn.rasoulonlineshop.navigation.BottomNavigationBar
import com.technolearn.rasoulonlineshop.navigation.Screen
import com.technolearn.rasoulonlineshop.ui.theme.Background
import com.technolearn.rasoulonlineshop.ui.theme.Black
import com.technolearn.rasoulonlineshop.ui.theme.FontBold34
import com.technolearn.rasoulonlineshop.ui.theme.FontMedium14
import com.technolearn.rasoulonlineshop.ui.theme.FontRegular11
import com.technolearn.rasoulonlineshop.ui.theme.FontRegular14
import com.technolearn.rasoulonlineshop.ui.theme.FontSemiBold16
import com.technolearn.rasoulonlineshop.ui.theme.Gray
import com.technolearn.rasoulonlineshop.ui.theme.Primary
import com.technolearn.rasoulonlineshop.ui.theme.White
import com.technolearn.rasoulonlineshop.util.Extensions.orDefault
import com.technolearn.rasoulonlineshop.vo.enums.ButtonSize
import com.technolearn.rasoulonlineshop.vo.enums.ButtonStyle
import com.technolearn.rasoulonlineshop.vo.res.ProductRes
import com.technolearn.rasoulonlineshop.vo.res.SliderRes
import timber.log.Timber


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProductScreen(navController: NavController) {
    val testSlider: ArrayList<SliderRes> = arrayListOf()
    val testProduct: ArrayList<ProductRes> = arrayListOf()
    testSlider.add(SliderRes(1, R.drawable.test_image_slider1, "", "subtitle1", "slider1"))
    testSlider.add(SliderRes(2, R.drawable.test_image_slider2, "", "subtitle2", "slider2"))
    testSlider.add(SliderRes(3, R.drawable.test_image_slider3, "", "subtitle3", "slider3"))
    testSlider.add(SliderRes(4, R.drawable.test_image_slider4, "", "subtitle4", "slider4"))

    testProduct.add(
        ProductRes(
            11,
            "Adidas",
            "Shirt",
            arrayListOf(R.drawable.test_image_slider1, R.drawable.test_image_slider2),
            "",
            19.9,
            3.0,
            "NEW",
            "Short dress in soft cotton jersey with decorative buttons down the front and a wide, " +
                    "frill-trimmed square neckline with concealed elasticated." +
                    " Elasticated seam under the bust and short puff sleeves with a small frill trim.",
            0f
        )
    )
    testProduct.add(
        ProductRes(
            12,
            "Nike",
            "Shoes",
            arrayListOf(R.drawable.test_image_slider2, R.drawable.test_image_slider3),
            "",
            25.0,
            3.5,
            "5%",
            "Short dress in soft cotton jersey with decorative buttons down the front and a wide, " +
                    "frill-trimmed square neckline with concealed elasticated." +
                    " Elasticated seam under the bust and short puff sleeves with a small frill trim.",
            5f
        )
    )
    testProduct.add(
        ProductRes(
            13,
            "D&G",
            "Shirt",
            arrayListOf(R.drawable.test_image_slider3, R.drawable.test_image_slider4),
            "",
            12.0,
            2.0,
            "NEW",
            "Short dress in soft cotton jersey with decorative buttons down the front and a wide, " +
                    "frill-trimmed square neckline with concealed elasticated." +
                    " Elasticated seam under the bust and short puff sleeves with a small frill trim.",
            25f
        )
    )
    testProduct.add(
        ProductRes(
            14,
            "Jack&Jones",
            "pant",
            arrayListOf(R.drawable.test_image_slider4, R.drawable.test_image_slider1),
            "",
            16.3,
            4.0,
            "30%",
            "Short dress in soft cotton jersey with decorative buttons down the front and a wide, " +
                    "frill-trimmed square neckline with concealed elasticated." +
                    " Elasticated seam under the bust and short puff sleeves with a small frill trim.",
            30f
        )
    )

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

                            }
                        )
                    }
                    Spacer(modifier = Modifier.height(18.dp))
                }
                //New Product Items
                item {
                    LazyRow(contentPadding = PaddingValues(start = 18.dp)) {
                        items(testProduct.filter { it.label == "NEW" }) { productRes ->
                            ProductItem(productRes = productRes, navController)
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
                            }
                        )
                    }
                    Spacer(modifier = Modifier.height(18.dp))
                }
                //Popular Product Items
                item {
                    LazyRow(contentPadding = PaddingValues(start = 18.dp)) {
                        items(testProduct.filter { it.rate!! >= 3.5 }.reversed()) { productRes ->
                            ProductItem(productRes = productRes, navController = navController)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ProductItem(productRes: ProductRes, navController: NavController) {
    var isAddToFavorite by remember { mutableStateOf(false) }
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .height(360.dp)
            .padding(end = 16.dp)
            .aspectRatio(15f / 26f, true)
            .clickable {
                navController.navigate(
                    route = Screen.ProductDetailScreen.passProductId(
                        productRes.id
                    )
                )
                Timber.d("ProductScreen::::${productRes.id}")
            },
        elevation = 0.dp
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .height(260.dp)
                    .fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Image(
                        painter = painterResource(id = productRes.image[0]),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp)
                            .clip(shape = RoundedCornerShape(8.dp)),
                        contentScale = ContentScale.Crop,
                    )
                    //////////
                    //Label
                    Label(
                        productRes.label.orDefault(),
                        30.dp,
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(top = 8.dp, start = 8.dp)
                    )
                    //////////
                }

                AddToFavorite(
                    modifier = Modifier
                        .clickable {
                            isAddToFavorite = !isAddToFavorite
                        }
                        .size(36.dp)
                        .align(Alignment.BottomEnd),
                    isAddToFavorite = isAddToFavorite
                )

            }
            CustomRatingBar(
                rating = productRes.rate.orDefault(),
                stars = 5
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = productRes.brand.orDefault(),
                style = FontRegular11(Gray),
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = productRes.title.orDefault(),
                style = FontSemiBold16(Black),
            )
            Spacer(modifier = Modifier.height(2.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${productRes.price}$",
                    style = FontMedium14(if (productRes.hasDiscount!! > 0f) Gray else Black),
                    textDecoration = if (productRes.hasDiscount!! > 0f) TextDecoration.LineThrough else TextDecoration.None
                )
                if (productRes.hasDiscount!! > 0f) {
                    val discountAmount =
                        (productRes.price?.times(productRes.hasDiscount!!))?.div(100.0)
                    val finalPrice = productRes.price?.minus(discountAmount!!)
                    Text(
                        modifier = Modifier.padding(start = 4.dp),
                        text = "${finalPrice}$",
                        style = FontMedium14(Primary),
                    )
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





