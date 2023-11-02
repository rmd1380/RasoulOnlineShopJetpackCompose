package com.technolearn.rasoulonlineshop.helper

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.progressSemantics
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
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
import com.skydoves.landscapist.glide.GlideImage
import com.technolearn.rasoulonlineshop.R
import com.technolearn.rasoulonlineshop.navigation.Screen
import com.technolearn.rasoulonlineshop.ui.theme.Black
import com.technolearn.rasoulonlineshop.ui.theme.FontMedium14
import com.technolearn.rasoulonlineshop.ui.theme.FontRegular11
import com.technolearn.rasoulonlineshop.ui.theme.FontRegular14
import com.technolearn.rasoulonlineshop.ui.theme.FontSemiBold16
import com.technolearn.rasoulonlineshop.ui.theme.Gray
import com.technolearn.rasoulonlineshop.ui.theme.Primary
import com.technolearn.rasoulonlineshop.ui.theme.White
import com.technolearn.rasoulonlineshop.util.Extensions.orDefault
import com.technolearn.rasoulonlineshop.util.Extensions.orFalse
import com.technolearn.rasoulonlineshop.vm.ShopViewModel
import com.technolearn.rasoulonlineshop.vo.enums.ButtonStyle
import com.technolearn.rasoulonlineshop.vo.res.ProductRes
import timber.log.Timber

@Composable
fun ProductItem(productRes: ProductRes?, navController: NavController, isLiked: (Int) -> Unit) {
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
                        productRes?.id.orDefault()
                    )
                )
                Timber.d("ProductScreen::::${productRes?.id.orDefault()}")
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
                    GlideImage(
                        imageModel = productRes?.image?.get(0) ?: "",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp)
                            .clip(shape = RoundedCornerShape(8.dp)),
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
                            Text(text = "image request failed.${it}")
                        })
//                    Image(
//                        painter = painterResource(id = productRes.image[0]),
//                        contentDescription = null,
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .height(250.dp)
//                            .clip(shape = RoundedCornerShape(8.dp)),
//                        contentScale = ContentScale.Crop,
//                    )
                    //////////
                    //Label
                    Label(
                        text = if (productRes?.label.isNullOrEmpty()) {
                            "${productRes?.hasDiscount.orDefault()}%"
                        } else productRes?.label.orDefault(),
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
                    isLiked(productId)
                }

            }
            Column(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(start = 4.dp)
            ) {
                CustomRatingBar(
                    rating = productRes?.rate.orDefault(),
                    stars = 5
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = productRes?.brand.orDefault(),
                    style = FontRegular11(Gray),
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = productRes?.title.orDefault(),
                    style = FontSemiBold16(Black),
                )
                Spacer(modifier = Modifier.height(2.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${productRes?.price}$",
                        style = FontMedium14(if (productRes?.hasDiscount.orDefault() > 0f) Gray else Black),
                        textDecoration = if (productRes?.hasDiscount.orDefault() > 0f) TextDecoration.LineThrough else TextDecoration.None
                    )
                    if (productRes?.hasDiscount.orDefault() > 0f) {
                        val discountAmount =
                            (productRes?.price?.times(productRes.hasDiscount.orDefault()))?.div(
                                100.0
                            )
                        val finalPrice = productRes?.price?.minus(discountAmount.orDefault())
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
fun ProductFavoriteItem(
    productRes: ProductRes?,
    navController: NavController,
    isLiked: (Int) -> Unit
) {
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
                        productRes?.id.orDefault()
                    )
                )
                Timber.d("ProductScreen::::${productRes?.id.orDefault()}")
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
                    GlideImage(
                        imageModel = productRes?.image?.get(0) ?: "",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp)
                            .clip(shape = RoundedCornerShape(8.dp)),
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
//                    Image(
//                        painter = painterResource(id = productRes.image[0]),
//                        contentDescription = null,
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .height(250.dp)
//                            .clip(shape = RoundedCornerShape(8.dp)),
//                        contentScale = ContentScale.Crop,
//                    )
                    //////////
                    //Label
                    Label(
                        productRes?.label.orDefault(),
                        30.dp,
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(top = 8.dp, start = 8.dp)
                    )
                    //////////
                }
                AddToFavorite(
                    modifier = Modifier
                        .size(36.dp)
                        .align(Alignment.BottomEnd),
                    productRes = productRes ?: ProductRes(),
                ) { productId ->
                    Timber.d("productId1111:::$productId")
                    isLiked(productId)
                }

            }
            Column(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(start = 4.dp)
            ) {
                CustomRatingBar(
                    rating = productRes?.rate.orDefault(),
                    stars = 5
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = productRes?.brand.orDefault(),
                    style = FontRegular11(Gray),
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = productRes?.title.orDefault(),
                    style = FontSemiBold16(Black),
                )
                Spacer(modifier = Modifier.height(2.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${productRes?.price}$",
                        style = FontMedium14(if (productRes?.hasDiscount.orDefault() > 0f) Gray else Black),
                        textDecoration = if (productRes?.hasDiscount.orDefault() > 0f) TextDecoration.LineThrough else TextDecoration.None
                    )
                    if (productRes?.hasDiscount.orDefault() > 0f) {
                        val discountAmount =
                            (productRes?.price?.times(productRes.hasDiscount.orDefault()))?.div(
                                100.0
                            )
                        val finalPrice = productRes?.price?.minus(discountAmount.orDefault())
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
    productRes: ProductRes?,
    navController: NavController,
    isLiked: (Int) -> Unit
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
                            productRes?.id.orDefault()
                        )
                    )
                    Timber.d("ProductScreen::::${productRes?.id.orDefault()}")
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
                    GlideImage(
                        imageModel = productRes?.image?.get(0) ?: "",
                        modifier = Modifier
                            .clip(RoundedCornerShape(topStart = 8.dp, bottomStart = 8.dp)),
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
//                    Image(
//                        painter = painterResource(id = productRes.image[0]),
//                        contentDescription = productRes.title,
//                        contentScale = ContentScale.Crop,
//                        modifier = Modifier
//                            .clip(RoundedCornerShape(topStart = 8.dp, bottomStart = 8.dp))
//                    )
                    //////////
                    //Label
                    Label(
                        productRes?.label.orDefault(),
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
                        text = productRes?.title.orDefault(),
                        style = FontSemiBold16(Black),
                    )
                    Spacer(modifier = Modifier.height(3.dp))
                    Text(
                        text = productRes?.brand.orDefault(),
                        style = FontRegular11(Gray),
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    CustomRatingBar(
                        rating = productRes?.rate.orDefault(),
                        stars = 5
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "${productRes?.price}$",
                            style = FontMedium14(if (productRes?.hasDiscount.orDefault() > 0f) Gray else Black),
                            textDecoration = if (productRes?.hasDiscount.orDefault() > 0f) TextDecoration.LineThrough else TextDecoration.None
                        )
                        if (productRes?.hasDiscount.orDefault() > 0f) {
                            val discountAmount =
                                (productRes?.price?.times(productRes.hasDiscount.orDefault()))?.div(
                                    100.0
                                )
                            val finalPrice = productRes?.price?.minus(discountAmount.orDefault())
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
            isLiked(productId)
        }
    }
}

@Composable
fun ProfileItem(
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .clickable {
                    onClick()
                }, horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                Modifier
                    .wrapContentWidth()
            ) {
                Text(text = title, style = FontSemiBold16(Black))
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = subtitle, style = FontRegular11(Gray))
            }
            Icon(
                painter = painterResource(id = R.drawable.ic_chevron_right),
                contentDescription = "ic_chevron_right",
                tint = Gray,
                modifier = Modifier
                    .size(24.dp)
            )
        }
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
                .alpha(0.3f),
            thickness = 1.dp,
            color = Gray
        )
    }

}

@Composable
fun OrderItem(
    orderNumber: String,
    quantity: String,
    totalAmount: String,
    orderDate: String,
    onClick: () -> Unit
) {
    Card {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "OrderNo:$orderNumber", style = FontSemiBold16(Black))
                Text(text = orderDate, style = FontRegular14(Gray))
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Quantity: $quantity", style = FontRegular14(Gray))
                Text(text = "TotalAmount: $totalAmount$", style = FontRegular14(Gray))
            }
            Spacer(modifier = Modifier.height(24.dp))
            CustomButton(
                modifier = Modifier.fillMaxWidth(),
                text = "Details",
                style = ButtonStyle.OUTLINED,
                roundCorner = 24.dp,
                onClick = onClick
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    val sampleProductRes = ProductRes(
        id = 1,
        image = arrayListOf(),
        label = "Sample Label",
        rate = 3.0,
        brand = "Sample Brand",
        title = "Sample Product Title",
        price = 100.0,
        hasDiscount = 10.0f
    )
    Column {
//        ProductItemHorizontal(sampleProductRes, rememberNavController()) {}
//        ProductItem(sampleProductRes, rememberNavController()) {}
//        profileItem("My orders", "Already have 12 orders") {}
        OrderItem("25697", "5", "120", "20/3/2023") {}
    }
}