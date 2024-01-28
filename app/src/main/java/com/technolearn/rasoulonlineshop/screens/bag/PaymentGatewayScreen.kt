package com.technolearn.rasoulonlineshop.screens.bag

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.technolearn.rasoulonlineshop.R
import com.technolearn.rasoulonlineshop.helper.CustomButton
import com.technolearn.rasoulonlineshop.mapper.toProductResByUserCartEntity
import com.technolearn.rasoulonlineshop.mapper.toSignUpRes
import com.technolearn.rasoulonlineshop.navigation.NavigationBarItemsGraph
import com.technolearn.rasoulonlineshop.navigation.Screen
import com.technolearn.rasoulonlineshop.ui.theme.Background
import com.technolearn.rasoulonlineshop.ui.theme.Black
import com.technolearn.rasoulonlineshop.ui.theme.FontBold34
import com.technolearn.rasoulonlineshop.ui.theme.FontRegular14
import com.technolearn.rasoulonlineshop.ui.theme.Primary
import com.technolearn.rasoulonlineshop.ui.theme.Success
import com.technolearn.rasoulonlineshop.util.Extensions.orDefault
import com.technolearn.rasoulonlineshop.vm.ShopViewModel
import com.technolearn.rasoulonlineshop.vo.entity.UserLoginEntity
import com.technolearn.rasoulonlineshop.vo.enums.ButtonSize
import com.technolearn.rasoulonlineshop.vo.enums.InvoiceStatus
import com.technolearn.rasoulonlineshop.vo.enums.Status
import com.technolearn.rasoulonlineshop.vo.model.InvoiceItems
import com.technolearn.rasoulonlineshop.vo.req.InvoiceReq
import timber.log.Timber

@Composable
fun PaymentGatewayScreen(navController: NavController, viewModel: ShopViewModel) {
    val context = LocalContext.current
    val invoiceStatus by remember { viewModel.invoiceStatus }.observeAsState()
    val userLoggedInInfo by remember { viewModel.getLoggedInUser() }.observeAsState()
    val productInCartList by remember { viewModel.getAllUserCart() }.observeAsState()
    var btnIsEnabled by remember { mutableStateOf(true) }
    var whichBtnClicked by remember { mutableStateOf("") }
    LaunchedEffect(productInCartList) {
        viewModel.getAllUserCart()
    }
    LaunchedEffect(userLoggedInInfo) {
        viewModel.getLoggedInUser()
    }
    LaunchedEffect(invoiceStatus) {
        when (invoiceStatus?.status) {
            Status.LOADING -> {
                Timber.d("AddInvoice:::LOADING:::")
                btnIsEnabled = false
            }

            Status.SUCCESS -> {
                Timber.d("AddInvoice:::SUCCESS:::${invoiceStatus?.data}")
                when (invoiceStatus?.data?.status) {
                    in 100..199 -> {
                        Toast.makeText(context, invoiceStatus?.data?.message, Toast.LENGTH_SHORT)
                            .show()
                    }

                    in 200..299 -> {
                        when (whichBtnClicked) {
                            context.getString(R.string.pay) -> {
                                viewModel.deleteAllUserCart()
                                navController.navigate(Screen.SuccessfulPaymentScreen.route)
                            }

                            context.getString(R.string.cancel) -> {
                                navController.navigate(NavigationBarItemsGraph.Bag.route)
                            }
                        }
                    }

                    in 300..399 -> {
                        Toast.makeText(context, invoiceStatus?.data?.message, Toast.LENGTH_SHORT)
                            .show()
                    }

                    in 400..499 -> {
                        Toast.makeText(context, invoiceStatus?.data?.message, Toast.LENGTH_SHORT)
                            .show()
                    }

                    in 500..599 -> {
                        Toast.makeText(context, invoiceStatus?.data?.message, Toast.LENGTH_SHORT)
                            .show()
                        navController.navigate(NavigationBarItemsGraph.Bag.route) {
                            popUpTo(NavigationBarItemsGraph.Bag.route) {
                                inclusive = true
                            }
                        }
                    }

                    else -> {
                    }
                }
            }

            Status.ERROR -> {
                Timber.d("AddInvoice:::ERROR:::${invoiceStatus}")
                Timber.d("AddInvoice:::ERROR:::${invoiceStatus?.data}")

            }

            else -> {

            }
        }
    }
    DisposableEffect(Unit) {
        onDispose {
            viewModel.resetInvoiceStatus() // Add a function to reset the status in your ViewModel
        }
    }
    Scaffold(
        backgroundColor = Background,
        bottomBar = {},
        topBar = {}
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    modifier = Modifier.size(300.dp),
                    painter = painterResource(R.drawable.payment_gateway),
                    contentDescription = "payment_gateway",
                )
                Spacer(modifier = Modifier.height(36.dp))
                Text(
                    text = stringResource(R.string.payment_gateway),
                    style = FontBold34(Black),
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "Finalize your payment",
                    style = FontRegular14(Black),
                )
                Spacer(modifier = Modifier.height(32.dp))
                Row(
                    Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    CustomButton(
                        enable = btnIsEnabled,
                        buttonColor = Success,
                        size = ButtonSize.SMALL,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        text = stringResource(R.string.pay),
                        onClick = {
                            whichBtnClicked = context.getString(R.string.pay)
                            val invoiceItemsList = productInCartList?.map { userCart ->
                                InvoiceItems(
                                    product = toProductResByUserCartEntity(userCart),
                                    quantity = userCart.quantity
                                )
                            }
                            viewModel.addInvoice(
                                token = userLoggedInInfo?.token.orDefault(),
                                invoiceReq = InvoiceReq(
                                    user = toSignUpRes(userLoggedInInfo ?: UserLoginEntity()),
                                    invoiceItems = invoiceItemsList,
                                ),
                                status = InvoiceStatus.Payed.ordinal
                            )
                        }
                    )
                    CustomButton(
                        enable = btnIsEnabled,
                        size = ButtonSize.SMALL,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        text = stringResource(R.string.cancel),
                        onClick = {
                            whichBtnClicked = context.getString(R.string.cancel)
                            val invoiceItemsList = productInCartList?.map { userCart ->
                                InvoiceItems(
                                    product = toProductResByUserCartEntity(userCart),
                                    quantity = userCart.quantity
                                )
                            }
                            viewModel.addInvoice(
                                token = userLoggedInInfo?.token.orDefault(),
                                invoiceReq = InvoiceReq(
                                    user = toSignUpRes(userLoggedInInfo ?: UserLoginEntity()),
                                    invoiceItems = invoiceItemsList,
                                ),
                                status = InvoiceStatus.NotPayed.ordinal
                            )
                            Toast.makeText(
                                context,
                                "Your payment has been cancelled",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    )
                }
            }
        }
    }
    if (!btnIsEnabled) {
        BackHandler {
            navController.navigate(NavigationBarItemsGraph.Home.route)
        }
    }

}
