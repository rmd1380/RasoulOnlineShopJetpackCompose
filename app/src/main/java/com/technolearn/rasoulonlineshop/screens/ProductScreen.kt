package com.technolearn.rasoulonlineshop.screens

import android.accessibilityservice.AccessibilityService.ScreenshotResult
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.technolearn.rasoulonlineshop.R
import com.technolearn.rasoulonlineshop.Screen
import com.technolearn.rasoulonlineshop.ui.theme.Black
import com.technolearn.rasoulonlineshop.ui.theme.Gray
import com.technolearn.rasoulonlineshop.ui.theme.MetroPoliceFontFamily
import com.technolearn.rasoulonlineshop.ui.theme.Primary
import com.technolearn.rasoulonlineshop.ui.theme.White
import com.technolearn.rasoulonlineshop.vo.res.SliderRes
import kotlin.math.ceil
import kotlin.math.floor


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProductScreen(navController: NavController) {
    val test: ArrayList<SliderRes> = arrayListOf()
    test.add(SliderRes(1, R.drawable.test_image_slider, "", "subtitle1", "slider1"))
    test.add(SliderRes(2, R.drawable.test_image_slider2, "", "subtitle2", "slider2"))
    test.add(SliderRes(3, R.drawable.test_image_slider3, "", "subtitle3", "slider3"))
    test.add(SliderRes(4, R.drawable.test_image_slider4, "", "subtitle4", "slider4"))
    val test2= arrayListOf(
        R.drawable.test_image_slider,
        R.drawable.test_image_slider2,
        R.drawable.test_image_slider3,
        R.drawable.test_image_slider4,
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
        ) {

            //Slider
            item {
                val pagerState = rememberPagerState { test.size }
                HorizontalPager(
                    state = pagerState,
                    key = { index: Int -> test[index].id!!.toInt() },
                ){index->
                    SliderItem(sliderRes = test[index])
                }
//                LazyRow(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                ) {
//                    items(test) { sliderRes ->
//                        SliderItem(sliderRes = sliderRes)
//                    }
//                }
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
                            text = "New",
                            color = Black,
                            fontSize = 34.sp,
                            fontFamily = MetroPoliceFontFamily,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "You've never seen it before!",
                            color = Gray,
                            fontSize = 11.sp,
                            fontFamily = MetroPoliceFontFamily,
                            fontWeight = FontWeight.Normal
                        )
                    }
                    Text(
                        text = "View all",
                        color = Black,
                        fontSize = 14.sp,
                        fontFamily = MetroPoliceFontFamily,
                        fontWeight = FontWeight.Normal,

                        )
                }
                Spacer(modifier = Modifier.height(18.dp))
            }
            //New Product Items
            item {
                LazyRow(contentPadding = PaddingValues(start = 18.dp)) {
                    items(test) { productRes ->
                        ProductItem(productRes = productRes,navController)
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
                            text = "Popular",
                            color = Black,
                            fontSize = 34.sp,
                            fontFamily = MetroPoliceFontFamily,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Best products ever",
                            color = Gray,
                            fontSize = 11.sp,
                            fontFamily = MetroPoliceFontFamily,
                            fontWeight = FontWeight.Normal
                        )
                    }
                    Text(
                        text = "View all",
                        color = Black,
                        fontSize = 14.sp,
                        fontFamily = MetroPoliceFontFamily,
                        fontWeight = FontWeight.Normal,

                        )
                }
                Spacer(modifier = Modifier.height(18.dp))
            }
            //Popular Product Items
            item {
                LazyRow(contentPadding = PaddingValues(start = 18.dp)) {
                    items(test) { productRes ->
                        ProductItem(productRes = productRes, navController = navController)
                    }
                }
            }
        }
    }
}

@Composable
fun ProductItem(productRes: SliderRes, navController: NavController) {
    var haveDiscount by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .width(200.dp)
            .height(310.dp)
            .padding(end = 16.dp)
            .clickable {
                navController.navigate(Screen.ProductDetailScreen.route)
            },
        shape = RoundedCornerShape(8.dp),
        elevation = 0.dp
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .height(210.dp)
                    .fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Image(
                        painter = painterResource(id = productRes.image ?: R.drawable.ic_cross),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .clip(shape = RoundedCornerShape(8.dp)),
                        contentScale = ContentScale.Crop,
                    )
                    Button(
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Primary,
                        ),
                        shape = RoundedCornerShape(25.dp),
                        onClick = {},
                        modifier = Modifier
                            .padding(top = 8.dp, start = 8.dp)
                            .align(Alignment.TopStart)
                            .clickable(
                                interactionSource = MutableInteractionSource(),
                                indication = null,
                                enabled = false
                            ) {}
                    ) {
                        Text(
                            modifier = Modifier.clickable(
                                interactionSource = MutableInteractionSource(),
                                indication = null,
                                enabled = false
                            ) { },
                            text = "15%",
                            color = White,
                            fontFamily = MetroPoliceFontFamily,
                            fontWeight = FontWeight.Medium,
                            fontSize = 14.sp,
                        )
                    }
                }
                AddToFavorite(
                    modifier = Modifier
                        .size(36.dp)
                        .align(Alignment.BottomEnd)
                )
            }
            CustomRatingBar(
                rating = 3.0,
                stars = 5
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "Mango",
                color = Gray,
                fontSize = 11.sp,
                fontFamily = MetroPoliceFontFamily,
                fontWeight = FontWeight.Normal
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = "Shirt",
                color = Black,
                fontSize = 16.sp,
                fontFamily = MetroPoliceFontFamily,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(2.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "12$",
                    color = if (haveDiscount) Gray else Black,
                    fontSize = 14.sp,
                    fontFamily = MetroPoliceFontFamily,
                    fontWeight = FontWeight.Medium,
                    textDecoration = if (haveDiscount) TextDecoration.LineThrough else TextDecoration.None
                )
                if (haveDiscount) {
                    Text(
                        modifier = Modifier.padding(start = 4.dp),
                        text = "8$",
                        color = Primary,
                        fontSize = 14.sp,
                        fontFamily = MetroPoliceFontFamily,
                        fontWeight = FontWeight.Medium,
                    )
                }

            }


        }


    }

}

@Composable
fun SliderItem(sliderRes: SliderRes) {
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
                text = sliderRes.title ?: "",
                color = White,
                fontSize = 34.sp,
                fontFamily = MetroPoliceFontFamily,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = sliderRes.subTitle ?: "",
                color = White,
                fontSize = 34.sp,
                fontFamily = MetroPoliceFontFamily,
                fontWeight = FontWeight.Bold
            )

            Button(
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Primary,
                ),
                shape = RoundedCornerShape(25.dp),
                onClick = {

                },
                modifier = Modifier
                    .padding(top = 18.dp)
            ) {
                Text(
                    text = "Check",
                    color = White,
                    fontFamily = MetroPoliceFontFamily,
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(horizontal = 60.dp, vertical = 8.dp)
                )
            }
        }
    }
}


//Helper
@Composable
fun CustomRatingBar(
    rating: Double = 0.0,
    stars: Int = 5,
) {
    val filledStars = floor(rating).toInt()
    val unfilledStars = (stars - ceil(rating)).toInt()
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.Bottom) {
        repeat(filledStars) {
            Image(
                painter = painterResource(R.drawable.ic_start_active),
                contentDescription = null,
                modifier = Modifier.size(16.dp)
            )
        }
        repeat(unfilledStars) {
            Image(
                painter = painterResource(R.drawable.ic_start_inactive),
                contentDescription = null,
                modifier = Modifier.size(16.dp)
            )
        }
        Text(
            modifier = Modifier.padding(start = 2.dp),
            text = "(${rating})",
            color = Gray,
            fontSize = 11.sp,
            fontFamily = MetroPoliceFontFamily,
            fontWeight = FontWeight.Normal
        )

    }
}

@Composable
fun AddToFavorite(modifier: Modifier) {
    var isLiked by remember { mutableStateOf(false) }

    Card(
        modifier = modifier,
        elevation = 4.dp,
        shape = CircleShape
    ) {
        IconButton(
            modifier = Modifier
                .fillMaxSize(),
            onClick = {
                isLiked = !isLiked
            }
        ) {
            Icon(
                painter = painterResource(id = if (isLiked) R.drawable.ic_heart_filled else R.drawable.ic_heart_not_filled),
                contentDescription = if (isLiked) "Liked" else "Not Liked",
                tint = if (isLiked) Primary else Gray
            )
        }
    }
}

//Helper





