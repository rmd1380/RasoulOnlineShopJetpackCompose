package com.technolearn.rasoulonlineshop.helper

import android.view.MenuItem
import android.widget.PopupMenu
import androidx.compose.foundation.background
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
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.skydoves.landscapist.glide.GlideImage
import com.technolearn.rasoulonlineshop.R
import com.technolearn.rasoulonlineshop.mapper.toUpdateUserReq
import com.technolearn.rasoulonlineshop.navigation.Screen
import com.technolearn.rasoulonlineshop.ui.theme.Black
import com.technolearn.rasoulonlineshop.ui.theme.Error
import com.technolearn.rasoulonlineshop.ui.theme.FontMedium14
import com.technolearn.rasoulonlineshop.ui.theme.FontRegular11
import com.technolearn.rasoulonlineshop.ui.theme.FontRegular14
import com.technolearn.rasoulonlineshop.ui.theme.FontSemiBold16
import com.technolearn.rasoulonlineshop.ui.theme.Gray
import com.technolearn.rasoulonlineshop.ui.theme.Primary
import com.technolearn.rasoulonlineshop.ui.theme.Success
import com.technolearn.rasoulonlineshop.ui.theme.Warning
import com.technolearn.rasoulonlineshop.ui.theme.White
import com.technolearn.rasoulonlineshop.util.Extensions.orDefault
import com.technolearn.rasoulonlineshop.util.Extensions.orFalse
import com.technolearn.rasoulonlineshop.vm.ShopViewModel
import com.technolearn.rasoulonlineshop.vo.entity.UserAddressEntity
import com.technolearn.rasoulonlineshop.vo.entity.UserCartEntity
import com.technolearn.rasoulonlineshop.vo.enums.ButtonSize
import com.technolearn.rasoulonlineshop.vo.enums.ButtonStyle
import com.technolearn.rasoulonlineshop.vo.enums.OrderState
import com.technolearn.rasoulonlineshop.vo.enums.Status
import com.technolearn.rasoulonlineshop.vo.res.ProductRes
import timber.log.Timber
import kotlin.math.roundToInt

@Composable
fun ProductItem(
    productRes: ProductRes?,
    navController: NavController,
    isNew: Boolean = false,
    viewModel: ShopViewModel
) {
    val isInNew by remember { mutableStateOf(isNew) }

    val isProductLiked by remember { viewModel.getAllFavorite() }.observeAsState(emptyList())
//    val isProductLiked by viewModel.getAllFavorite().observeAsState(emptyList())

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
                        imageModel = productRes?.image?.let { if (it.isNotEmpty()) it[0] else "" }
                            ?: "",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp)
                            .clip(shape = RoundedCornerShape(8.dp)),
                        contentScale = ContentScale.Fit,
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
                        }
                    )

                    //Label
                    if (isInNew) {
                        Label(
                            text = "NEW",
                            30.dp,
                            modifier = Modifier
                                .align(Alignment.TopStart)
                                .padding(top = 8.dp, start = 8.dp)
                        )
                    } else if (productRes?.hasDiscount != 0f) {
                        Label(
                            text = "${productRes?.hasDiscount.orDefault()}%",
                            30.dp,
                            modifier = Modifier
                                .align(Alignment.TopStart)
                                .padding(top = 8.dp, start = 8.dp)
                        )
                    }
                }

                AddToFavorite(
                    modifier = Modifier
                        .size(36.dp)
                        .align(Alignment.BottomEnd),
                    productRes = productRes,
                    onToggleFavorite = {
                        viewModel.toggleAddToFavorites(it)
                    },
                    isProductLikedState = isProductLiked.any { it.id == productRes?.id }
                )
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
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = productRes?.title.orDefault(),
                    style = FontSemiBold16(Black),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(2.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${productRes?.price}$",
                        style = FontMedium14(if (productRes?.hasDiscount.orDefault() > 0f) Gray else Black),
                        textDecoration = if (productRes?.hasDiscount.orDefault() > 0f) TextDecoration.LineThrough else TextDecoration.None,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    if (productRes?.hasDiscount.orDefault() > 0f) {
                        val discountAmount =
                            (productRes?.price?.times(productRes.hasDiscount.orDefault()))?.div(
                                100.0
                            )
                        val finalPrice = productRes?.price?.minus(discountAmount.orDefault())
                        Text(
                            modifier = Modifier.padding(start = 4.dp),
                            text = "${finalPrice?.roundToInt()}$",
                            style = FontMedium14(Primary),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
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
    isNew: Boolean = false,
    viewModel: ShopViewModel
) {
    val isInNew by remember { mutableStateOf(isNew) }
    val isProductLiked by remember { viewModel.getAllFavorite() }.observeAsState(emptyList())
//    val isProductLiked by viewModel.getAllFavorite().observeAsState(emptyList())
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
                    if (isInNew) {
                        Label(
                            text = "NEW",
                            30.dp,
                            modifier = Modifier
                                .align(Alignment.TopStart)
                                .padding(top = 8.dp, start = 8.dp)
                        )
                    } else if (productRes?.hasDiscount != 0f) {
                        Label(
                            text = "${productRes?.hasDiscount.orDefault()}%",
                            30.dp,
                            modifier = Modifier
                                .align(Alignment.TopStart)
                                .padding(top = 8.dp, start = 8.dp)
                        )
                    }
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
                                text = "${finalPrice?.roundToInt()}$",
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
            onToggleFavorite = {
                viewModel.toggleAddToFavorites(it)
            },
            isProductLikedState = isProductLiked.any { it.id == productRes?.id }
        )
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
    orderState: OrderState,
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
                Row() {
                    Text(text = "Quantity: ", style = FontRegular14(Gray))
                    Text(text = quantity, style = FontSemiBold16(Black))
                }
                Row {
                    Text(text = "TotalAmount: ", style = FontRegular14(Gray))
                    Text(text = "$totalAmount$", style = FontSemiBold16(Black))
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                CustomButton(
                    modifier = Modifier.wrapContentWidth(),
                    text = "Details",
                    size = ButtonSize.X_SMALL,
                    style = ButtonStyle.OUTLINED,
                    roundCorner = 24.dp,
                    onClick = {
                        onClick()
                    }
                )
                Text(
                    text = orderState.name,
                    color = when(orderState){
                        OrderState.Delivered->{Success}
                        OrderState.Processing->{Warning}
                        OrderState.Cancelled->{Error}
                    }
                )
            }

        }
    }
}

@Composable
fun CartItem(
    userCartEntity: UserCartEntity?,
    navController: NavController,
    viewModel: ShopViewModel,
    more: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Card(
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
                .clip(RoundedCornerShape(8.dp))
                .clickable {
                    navController.navigate(
                        route = Screen.ProductDetailScreen.passProductId(
                            userCartEntity?.id.orDefault()
                        )
                    )
                    Timber.d("ProductScreen::::${userCartEntity?.id.orDefault()}")
                },
            elevation = 0.dp,
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 8.dp)
                    .aspectRatio(343f / 100f, true)
                    .height(100.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                ) {
                    GlideImage(
                        imageModel = userCartEntity?.image ?: "",
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
                        failure = {
                            Text(text = "image request failed.")
                        })
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1.5f)
                        .padding(top = 8.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(start = 12.dp)
                    ) {
                        Text(
                            text = userCartEntity?.name.orDefault(),
                            style = FontSemiBold16(Black),
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Row(
                            modifier = Modifier.wrapContentWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = stringResource(R.string.color),
                                style = FontRegular11(Gray),
                            )
                            Text(
                                text = userCartEntity?.color.orDefault(),
                                style = FontRegular11(Black),
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = stringResource(R.string.size),
                                style = FontRegular11(Gray),
                            )
                            Text(
                                text = userCartEntity?.size.orDefault(),
                                style = FontRegular11(Black),
                            )
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(
                                modifier = Modifier.wrapContentWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                CircleIconCard(
                                    modifier = Modifier
                                        .size(36.dp),
                                    icon = painterResource(id = R.drawable.ic_subtract)
                                ) {
                                    viewModel.decrementQuantity(userCartEntity?.id.orDefault())
                                }
                                Spacer(modifier = Modifier.width(16.dp))
                                Text(
                                    text = userCartEntity?.quantity.orDefault().toString(),
                                    style = FontMedium14(Black),
                                )
                                Spacer(modifier = Modifier.width(16.dp))
                                CircleIconCard(
                                    modifier = Modifier
                                        .size(36.dp),
                                    icon = painterResource(id = R.drawable.ic_add)
                                ) {
                                    viewModel.incrementQuantity(userCartEntity?.id.orDefault())
                                }
                            }
                            Text(
                                text = "${userCartEntity?.price?.roundToInt()}$",
                                style = FontMedium14(Black),
                            )
                        }
                    }

                    Icon(
                        painter = painterResource(id = R.drawable.ic_more),
                        contentDescription = "ic_more",
                        tint = Gray,
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .size(24.dp)
                            .clickable {
                                more()
                            }
                    )
                }
            }
        }
    }
}

@Composable
fun ShippingAddressItem(
    userAddressEntity: UserAddressEntity,
    navController: NavController,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    edit: () -> Unit,
    delete: () -> Unit
) {

//    val userLoggedInInfo by remember { viewModel.getLoggedInUser() }.observeAsState()
//    val updateUserStatus by remember { viewModel.updateUserStatus }.observeAsState()
//    var myState by remember { mutableStateOf(false) }
//    val addresses by remember { viewModel.getAllUserAddress() }.observeAsState()
//    val addressIndex = addresses?.indexOf(userAddressEntity)?:-1
//    myState = if (addressIndex != -1) {
//        addresses?.get(addressIndex)?.isAddressSelected.orFalse()
//    } else {
//        false // or some default value
//    }

//    LaunchedEffect(updateUserStatus) {
//        when (updateUserStatus?.status) {
//            Status.LOADING -> {
//                Timber.d("Update:::LOADING:::${updateUserStatus?.data?.status}")
//            }
//
//            Status.SUCCESS -> {
//                Timber.d("Update:::SUCCESS:::${updateUserStatus?.data}")
//                if (myState) {
//                    viewModel.updateUser(
//                        userLoggedInInfo?.token.orDefault(),
//                        toUpdateUserReq(userAddressEntity)
//                    )
//                    viewModel.updateUserAddress(userAddressEntity)
//                }
//            }
//
//            Status.ERROR -> {
//                Timber.d("Update:::ERROR:::${updateUserStatus?.data?.status}")
//            }
//
//            else -> {}
//        }
//    }
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .clickable {},
        elevation = 8.dp,
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .aspectRatio(343f / 140f, true)
                    .height(140.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1.5f)
                        .padding(top = 8.dp)
                ) {
                    Column(
                        modifier = Modifier
                    ) {
                        Text(
                            text = "${userAddressEntity.firstName.orDefault()} ${userAddressEntity.lastName.orDefault()}",
                            style = FontMedium14(Black),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = userAddressEntity.addressName.orDefault(),
                            style = FontRegular14(Black),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = userAddressEntity.address.orDefault(),
                            style = FontRegular14(Black),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(
                                modifier = Modifier.wrapContentWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Checkbox(
                                    checked = isChecked,
                                    colors = CheckboxDefaults.colors(
                                        uncheckedColor = Gray,
                                        checkedColor = Black,
                                        checkmarkColor = White
                                    ),
                                    onCheckedChange = { isChecked ->
                                        onCheckedChange(isChecked)
                                        // If you want to update the user address immediately when the checkbox is checked
//                                        if (isChecked) {
//                                            // Perform the necessary actions, e.g., update user address in the ViewModel
//                                            viewModel.updateUserAddress(userAddressEntity.copy(isAddressSelected = true))
//                                        }
                                    }
                                )


                                Text(
                                    text = stringResource(R.string.use_as_the_shipping_address),
                                    style = FontRegular14(Black),
                                )
                            }
                            Icon(
                                painter = painterResource(id = R.drawable.ic_trash),
                                contentDescription = "ic_trash",
                                tint = Black,
                                modifier = Modifier
                                    .size(24.dp)
                                    .clickable {
                                        delete()
                                    }
                            )
                        }
                    }
                    Text(
                        text = "Edit",
                        style = FontMedium14(Black),
                        modifier = Modifier
                            .wrapContentSize()
                            .align(Alignment.TopEnd)
                            .clickable {
                                edit()
                            },
                        color = Primary
                    )
                }
            }}
    }
}

@Composable
fun ShippingAddressItemCheckout(
    userAddressEntity: UserAddressEntity,
    change: () -> Unit,
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .clickable {},
        elevation = 8.dp,
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .aspectRatio(343f / 140f, true)
                    .height(140.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1.5f)
                        .padding(top = 8.dp)
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "${userAddressEntity.firstName.orDefault()} ${userAddressEntity.lastName.orDefault()}",
                            style = FontMedium14(Black),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = userAddressEntity.addressName.orDefault(),
                            style = FontRegular14(Black),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = userAddressEntity.address.orDefault(),
                            style = FontRegular14(Black),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    Text(
                        text ="Change",
                        style = FontMedium14(Black),
                        modifier = Modifier
                            .wrapContentSize()
                            .align(Alignment.TopEnd)
                            .clickable {
                                change()
                            },
                        color = Primary
                    )
                }
            }}
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    val sampleProductRes = ProductRes(
        id = 1,
        image = arrayListOf(),
        rate = 3.0,
        brand = "Sample Brand",
        title = "Sample Product Title",
        price = 100.0,
        hasDiscount = 10.0f
    )
    val sampleUserCart = UserCartEntity(
        id = 1,
        name = "Test",
        image = "",
        color = "Black",
        size = "XL",
        price = 900.0,
        quantity = 5
    )
    val sampleUserLogin = UserAddressEntity(
        id = 1,
        userId = 1,
        firstName = "Rasoul",
        lastName = "Motie",
        phone = "String",
        addressName = "Home",
        address = "Qom-HazratMasomeh Boulevard",
        city = "Qom",
        province = "Qom",
        postalCode = "123456789",
        country = "String",
    )
    Column {
//        ProductItemHorizontal(sampleProductRes, rememberNavController()) {}
//        ProductItem(sampleProductRes, rememberNavController()) {}
//        profileItem("My orders", "Already have 12 orders") {}
        OrderItem("25697", "5", "120", "20/3/2023", OrderState.Cancelled) {}
//        CartItem(sampleUserCart, rememberNavController(), viewModel()) {}
//        ShippingAddressItem(
//            userLoginEntity = sampleUserLogin,
//            navController = rememberNavController(),
//            viewModel(),
//            change = {},
//            delete = {}
//        )
    }
}