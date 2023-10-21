package com.technolearn.rasoulonlineshop.helper

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.technolearn.rasoulonlineshop.R
import com.technolearn.rasoulonlineshop.navigation.Screen
import com.technolearn.rasoulonlineshop.ui.theme.Black
import com.technolearn.rasoulonlineshop.ui.theme.FontMedium14
import com.technolearn.rasoulonlineshop.ui.theme.FontRegular11
import com.technolearn.rasoulonlineshop.ui.theme.FontSemiBold16
import com.technolearn.rasoulonlineshop.ui.theme.Gray
import com.technolearn.rasoulonlineshop.ui.theme.Primary
import com.technolearn.rasoulonlineshop.util.Extensions.orDefault
import com.technolearn.rasoulonlineshop.util.Extensions.orFalse
import com.technolearn.rasoulonlineshop.vm.ShopViewModel
import com.technolearn.rasoulonlineshop.vo.res.ProductRes
import timber.log.Timber

@Composable
fun ProductItem(productRes: ProductRes, navController: NavController, viewModel: ShopViewModel) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .wrapContentSize()
            .width(190.dp)
            .padding(8.dp)
            .clip(RoundedCornerShape(8.dp))
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
        Column(
            modifier = Modifier
                .wrapContentSize()
                .padding(bottom = 4.dp)
        ) {
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

//                AddToFavorite(
//                    modifier = Modifier
//                        .size(36.dp)
//                        .align(Alignment.BottomEnd),
////                    isAddToFavorite = isAddToFavorite
//                )
                AddToFavorite(
                    modifier = Modifier
                        .size(36.dp)
                        .align(Alignment.BottomEnd),
                    productRes = productRes,
                ) { productId ->
                    Timber.d("productId1111:::$productId")
                    viewModel.toggleAddToFavorites(productId)
                }

            }
            Column(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(start = 4.dp)
            ) {
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
}

@Composable
fun ProductItemHorizontal(
    productRes: ProductRes,
    navController: NavController,
    viewModel: ShopViewModel
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Card(
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
                .clip(RoundedCornerShape(8.dp))
                .clickable {
                    navController.navigate(
                        route = Screen.ProductDetailScreen.passProductId(
                            productRes.id
                        )
                    )
                    Timber.d("ProductScreen::::${productRes.id}")
                },
            elevation = 0.dp,
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(343f / 100f, true)
                    .height(100.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Box(
                    modifier = Modifier
                        .weight(1f)
                ) {
                    Image(
                        painter = painterResource(id = productRes.image[0]),
                        contentDescription = productRes.title,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .clip(RoundedCornerShape(topStart = 8.dp, bottomStart = 8.dp))
                    )
                    //////////
                    //Label
                    Label(
                        productRes.label.orDefault(),
                        30.dp,
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(top = 4.dp, start = 4.dp)
                    )
                    //////////
                }
                Column(
                    modifier = Modifier
                        .weight(1.5f)
                        .padding(start = 12.dp)
                ) {
                    Text(
                        text = productRes.title.orDefault(),
                        style = FontSemiBold16(Black),
                    )
                    Spacer(modifier = Modifier.height(3.dp))
                    Text(
                        text = productRes.brand.orDefault(),
                        style = FontRegular11(Gray),
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    CustomRatingBar(
                        rating = productRes.rate.orDefault(),
                        stars = 5
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
        AddToFavorite(
            modifier = Modifier
                .size(36.dp)
                .align(Alignment.BottomEnd),
            productRes = productRes,
        ) { productId ->
            viewModel.toggleAddToFavorites(productId)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    val sampleProductRes = ProductRes(
        id = 1,
        image = arrayListOf(R.drawable.test_image_slider1),
        label = "Sample Label",
        rate = 3.0,
        brand = "Sample Brand",
        title = "Sample Product Title",
        price = 100.0,
        hasDiscount = 10.0f
    )
    Column {
        ProductItemHorizontal(sampleProductRes, rememberNavController(), viewModel())
        ProductItem(sampleProductRes, rememberNavController(), viewModel())
    }
}