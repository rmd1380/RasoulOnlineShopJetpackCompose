package com.technolearn.rasoulonlineshop.screens.bag

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.technolearn.rasoulonlineshop.R
import com.technolearn.rasoulonlineshop.helper.CustomButton
import com.technolearn.rasoulonlineshop.helper.CustomTopAppBar
import com.technolearn.rasoulonlineshop.helper.ShippingAddressItemCheckout
import com.technolearn.rasoulonlineshop.mapper.toProductResByUserCartEntity
import com.technolearn.rasoulonlineshop.mapper.toSignUpRes
import com.technolearn.rasoulonlineshop.navigation.NavigationBarItemsGraph
import com.technolearn.rasoulonlineshop.navigation.Screen
import com.technolearn.rasoulonlineshop.ui.theme.Background
import com.technolearn.rasoulonlineshop.ui.theme.Black
import com.technolearn.rasoulonlineshop.ui.theme.FontMedium14
import com.technolearn.rasoulonlineshop.ui.theme.FontRegular11
import com.technolearn.rasoulonlineshop.ui.theme.FontSemiBold16
import com.technolearn.rasoulonlineshop.ui.theme.FontSemiBold18
import com.technolearn.rasoulonlineshop.ui.theme.Gray
import com.technolearn.rasoulonlineshop.ui.theme.Primary
import com.technolearn.rasoulonlineshop.ui.theme.White
import com.technolearn.rasoulonlineshop.util.CardNumberParser
import com.technolearn.rasoulonlineshop.util.CreditCardTypeFinder
import com.technolearn.rasoulonlineshop.util.Extensions.orDefault
import com.technolearn.rasoulonlineshop.vm.ShopViewModel
import com.technolearn.rasoulonlineshop.vo.entity.UserAddressEntity
import com.technolearn.rasoulonlineshop.vo.entity.UserLoginEntity
import com.technolearn.rasoulonlineshop.vo.enums.CreditCardType
import com.technolearn.rasoulonlineshop.vo.enums.Status
import com.technolearn.rasoulonlineshop.vo.model.InvoiceItems
import com.technolearn.rasoulonlineshop.vo.req.InvoiceReq
import com.technolearn.rasoulonlineshop.vo.res.ProductRes
import com.technolearn.rasoulonlineshop.vo.res.SignUpRes
import timber.log.Timber
import kotlin.math.roundToInt

@Composable
fun CheckOutScreen(navController: NavController, viewModel: ShopViewModel) {
    val context = LocalContext.current
    val userLoggedInInfo by remember { viewModel.getLoggedInUser() }.observeAsState()
    val selectedUserCreditCard by remember { viewModel.getSelectedUserCreditCard() }.observeAsState()
    var selectedDeliveryIndex by remember { mutableIntStateOf(0) }
    val productInCartList by remember { viewModel.getAllUserCart() }.observeAsState()
    val (sumOfOrder, finalPrice) = remember(productInCartList, selectedDeliveryIndex) {
        var orderSum = 0
        val deliveryCost = when (selectedDeliveryIndex) {
            0 -> 50
            1 -> 35
            else -> 65
        }

        // Update the sum of the order
        productInCartList.orEmpty().forEach { userCart ->
            orderSum += (userCart.price.orDefault().roundToInt()) * userCart.quantity.orDefault()
        }

        // Return the sum of order and the final price
        orderSum to (orderSum + deliveryCost)
    }
    LaunchedEffect(productInCartList) {
        viewModel.getAllUserCart()
    }
    LaunchedEffect(userLoggedInInfo) {
        viewModel.getLoggedInUser()
    }
    LaunchedEffect(selectedUserCreditCard) {
        viewModel.getSelectedUserCreditCard()
    }

    Scaffold(
        backgroundColor = Background,
        bottomBar = {},
        topBar = {
            CustomTopAppBar(
                title = stringResource(R.string.checkout),
                style = FontSemiBold18(Black),
                navigationIcon = ImageVector.vectorResource(R.drawable.ic_chevron_back),
                actionIcons = listOf(),
                navigationOnClick = {
                    navController.navigate(NavigationBarItemsGraph.Bag.route) {
                        popUpTo(NavigationBarItemsGraph.Bag.route) {
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
                    .padding(horizontal = 16.dp, vertical = 16.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.shipping_address),
                    style = FontSemiBold16(Black),
                )
                Spacer(modifier = Modifier.height(24.dp))
                ShippingAddressItemCheckout(
                    userAddressEntity = UserAddressEntity(
                        userId = userLoggedInInfo?.customerId.orDefault(),
                        firstName = userLoggedInInfo?.firstName.orDefault(),
                        lastName = userLoggedInInfo?.lastName.orDefault(),
                        phone = userLoggedInInfo?.phone.orDefault(),
                        addressName = userLoggedInInfo?.addressName.orDefault(),
                        address = userLoggedInInfo?.address.orDefault(),
                        city = userLoggedInInfo?.city.orDefault(),
                        province = userLoggedInInfo?.province.orDefault(),
                        postalCode = userLoggedInInfo?.postalCode.orDefault(),
                        country = userLoggedInInfo?.country.orDefault(),
                    ),
                    change = {
                        navController.navigate(Screen.ShippingAddressScreen.route)
                    }
                )
                Spacer(modifier = Modifier.height(60.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(R.string.payment),
                        style = FontSemiBold16(Black),
                    )
                    Text(
                        text = stringResource(R.string.change),
                        style = FontMedium14(Black),
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(end = 16.dp)
                            .clickable {
                                navController.navigate(Screen.PaymentMethodsScreen.route)
                            },
                        color = Primary
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val img =
                        when (CreditCardTypeFinder.detect(selectedUserCreditCard?.cardNumber.orDefault())) {
                            CreditCardType.MELLI -> R.drawable.melli_logo
                            CreditCardType.SEPAH -> R.drawable.sepah_logo
                            CreditCardType.MELLAT -> R.drawable.mellat_logo
                            CreditCardType.TEJARAT -> R.drawable.tejarat_logo
                            CreditCardType.OTHER -> null
                        }

                    Image(
                        painter = if (img != null) painterResource(img) else painterResource(R.drawable.exclamationmark),
                        contentDescription = null,
                        modifier = Modifier
                            .width(79.dp)
                            .height(53.dp)
                            .shadow(elevation = 2.dp, shape = RoundedCornerShape(8.dp))
                            .background(White, shape = RoundedCornerShape(8.dp))
                            .clip(RoundedCornerShape(8.dp))
                            .padding(vertical = 8.dp)
                    )
                    Spacer(modifier = Modifier.width(24.dp))
                    val cardNumber = CardNumberParser(
                        number = selectedUserCreditCard?.cardNumber.orDefault(),
                        emptyChar = 'x'
                    )
                    Text(
                        text = "${cardNumber.first} ${cardNumber.second} ${cardNumber.third} ${cardNumber.fourth}",
                        style = FontMedium14(Black),
                        modifier = Modifier.wrapContentSize(),
                        color = Black
                    )
                }
                Spacer(modifier = Modifier.height(60.dp))
                Text(
                    text = stringResource(R.string.delivery_method),
                    style = FontSemiBold16(Black),
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {

                    deliveryItem(
                        painter = painterResource(id = R.drawable.fedex),
                        deliverTime = "2-3 days",
                        isSelected = selectedDeliveryIndex == 0
                    ) {
                        selectedDeliveryIndex = 0
                    }
                    Spacer(modifier = Modifier.width(24.dp))
                    deliveryItem(
                        painter = painterResource(id = R.drawable.usps),
                        deliverTime = "3-5 days",
                        isSelected = selectedDeliveryIndex == 1
                    ) {
                        selectedDeliveryIndex = 1
                    }
                    Spacer(modifier = Modifier.width(24.dp))
                    deliveryItem(
                        painter = painterResource(id = R.drawable.dhl),
                        deliverTime = "1-2 days",
                        isSelected = selectedDeliveryIndex == 2
                    ) {
                        selectedDeliveryIndex = 2
                    }
                }
                Spacer(modifier = Modifier.height(60.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Order:",
                        style = FontMedium14(Gray),
                    )
                    Text(
                        text = "${sumOfOrder}$",
                        style = FontSemiBold16(Black),
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Delivery:",
                        style = FontMedium14(Gray),
                    )
                    Text(
                        text = if (selectedDeliveryIndex == 0) "50$" else if (selectedDeliveryIndex == 1) "35$" else "65$",
                        style = FontSemiBold16(Black),
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Summary:",
                        style = FontSemiBold16(Gray),
                    )
                    Text(
                        text = "${finalPrice}$",
                        style = FontSemiBold16(Black),
                    )
                }
            }
            CustomButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 32.dp),
                text = stringResource(R.string.submit_order),
                onClick = {
                    when {
                        userLoggedInInfo?.address.isNullOrEmpty() -> {
                            Toast.makeText(context, "Please Add Address", Toast.LENGTH_SHORT).show()
                        }

                        selectedUserCreditCard == null -> {
                            Toast.makeText(context, "Please Add CreditCard", Toast.LENGTH_SHORT)
                                .show()
                        }
                        productInCartList.isNullOrEmpty()->{
                            Toast.makeText(context, "Cart is Empty", Toast.LENGTH_SHORT).show()
                        }
                        else -> {
                            navController.navigate(Screen.PaymentGatewayScreen.route)
                        }
                    }
                }
            )
        }
    }
}

@Composable
private fun deliveryItem(
    painter: Painter,
    deliverTime: String,
    isSelected: Boolean,
    onclick: () -> Unit
) {
    Card(
        modifier = Modifier
            .width(100.dp)
            .height(72.dp)
            .shadow(4.dp, RoundedCornerShape(8.dp))
            .border(1.dp, if (isSelected) Primary else Color.Transparent, RoundedCornerShape(8.dp))
            .clickable { onclick() },
        shape = RoundedCornerShape(8.dp)
    ) {
        Box(
            modifier = Modifier
                .wrapContentSize()
                .padding(vertical = 16.dp, horizontal = 16.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painter,
                    contentDescription = null,
                    modifier = Modifier
                        .wrapContentSize()
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = deliverTime,
                    style = FontRegular11(Gray),
                )
            }
        }
    }
}