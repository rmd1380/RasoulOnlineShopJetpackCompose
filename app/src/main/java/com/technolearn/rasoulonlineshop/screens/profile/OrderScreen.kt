package com.technolearn.rasoulonlineshop.screens.profile

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.technolearn.rasoulonlineshop.R
import com.technolearn.rasoulonlineshop.helper.CustomTopAppBar
import com.technolearn.rasoulonlineshop.helper.LottieComponent
import com.technolearn.rasoulonlineshop.helper.OrderItem
import com.technolearn.rasoulonlineshop.helper.ProductItem
import com.technolearn.rasoulonlineshop.navigation.NavigationBarItemsGraph
import com.technolearn.rasoulonlineshop.navigation.Screen
import com.technolearn.rasoulonlineshop.ui.theme.Black
import com.technolearn.rasoulonlineshop.ui.theme.Error
import com.technolearn.rasoulonlineshop.ui.theme.FontBold34
import com.technolearn.rasoulonlineshop.ui.theme.FontMedium14
import com.technolearn.rasoulonlineshop.ui.theme.White
import com.technolearn.rasoulonlineshop.util.Extensions.orDefault
import com.technolearn.rasoulonlineshop.vm.ShopViewModel
import com.technolearn.rasoulonlineshop.vo.enums.OrderState
import com.technolearn.rasoulonlineshop.vo.enums.Status
import com.technolearn.rasoulonlineshop.vo.model.helperComponent.CustomAction
import com.technolearn.rasoulonlineshop.vo.res.InvoiceRes
import com.technolearn.rasoulonlineshop.vo.res.ProductRes
import timber.log.Timber

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OrderScreen(navController: NavController, viewModel: ShopViewModel) {
    val context = LocalContext.current
    var filterSelectedTabIndex by remember { mutableIntStateOf(0) }
    val filterList = listOf("Delivered", "Processing", "Cancelled")
    val pagerState = rememberPagerState { filterList.size }
    val userLoggedInInfo by remember { viewModel.getLoggedInUser() }.observeAsState()
    val userInvoiceList by remember { viewModel.invoiceByUserId }.observeAsState()

    Timber.d("userLoggedInInfoORDER:::$userLoggedInInfo")
    LaunchedEffect(filterSelectedTabIndex) {
        pagerState.animateScrollToPage(filterSelectedTabIndex)
    }
    LaunchedEffect(pagerState.currentPage, pagerState.isScrollInProgress) {
        if (!pagerState.isScrollInProgress) {
            filterSelectedTabIndex = pagerState.currentPage
        }
    }
    LaunchedEffect(userLoggedInInfo) {
        viewModel.getLoggedInUser()
    }
    LaunchedEffect(userInvoiceList, userLoggedInInfo) {
        if (userInvoiceList == null) {
            if (userLoggedInInfo != null) {
                viewModel.fetchInvoiceByUserId(
                    userLoggedInInfo?.id.orDefault(),
                    userLoggedInInfo?.token.orDefault()
                )
            }
        }
    }
    Scaffold(
        backgroundColor = White,
        bottomBar = {

        },
        topBar = {
            CustomTopAppBar(
                title = "",
                style = null,
                navigationIcon = ImageVector.vectorResource(R.drawable.ic_chevron_back),
                actionIcons = listOf(),
                navigationOnClick = {
                    navController.navigate(NavigationBarItemsGraph.Profile.route) {
                        popUpTo(NavigationBarItemsGraph.Profile.route) {
                            inclusive = true
                        }
                    }
                },
                actionOnclick = {}
            )
        },
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            Column {
                Text(
                    text = "My Order",
                    style = FontBold34(Black),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )
                Spacer(modifier = Modifier.height(24.dp))
                LazyRow(
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .clip(RoundedCornerShape(15.dp))
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    items(filterList.size) { index ->
                        TextButton(
                            onClick = { filterSelectedTabIndex = index },
                            shape = RoundedCornerShape(30.dp),
                            colors = ButtonDefaults.textButtonColors(
                                backgroundColor = if (filterSelectedTabIndex == index) Black else White,
                            )
                        ) {
                            Text(
                                text = filterList[index],
                                modifier = Modifier.padding(horizontal = 16.dp),
                                style = if (filterSelectedTabIndex == index) FontMedium14(White) else FontMedium14(
                                    Black
                                )
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                HorizontalPager(
                    state = pagerState, modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )
                { index ->
                    when (userInvoiceList?.status) {
                        Status.LOADING -> {
                            Timber.d("userInvoiceList:::::LOADING")
                        }

                        Status.SUCCESS -> {
                            Timber.d("userInvoiceList:::::SUCCESS::::${userInvoiceList?.data}")
                            when (userInvoiceList?.data?.status) {
                                in 100..199 -> {
                                    Toast.makeText(
                                        context,
                                        userInvoiceList?.data?.message,
                                        Toast.LENGTH_SHORT
                                    )
                                        .show()
                                }

                                in 200..299 -> {
                                    when (index) {
                                        0 -> Delivered(userInvoiceList?.data?.data)
                                        1 -> Processing(userInvoiceList?.data?.data)
                                        2 -> Cancelled(userInvoiceList?.data?.data)
                                    }
                                }

                                in 300..399 -> {
                                    Toast.makeText(
                                        context,
                                        userInvoiceList?.data?.message,
                                        Toast.LENGTH_SHORT
                                    )
                                        .show()
                                }

                                in 400..499 -> {
                                    Toast.makeText(
                                        context,
                                        userInvoiceList?.data?.message,
                                        Toast.LENGTH_SHORT
                                    )
                                        .show()
                                }

                                in 500..599 -> {
                                    Toast.makeText(
                                        context,
                                        userInvoiceList?.data?.message,
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }

                                else -> {
                                }
                            }
                        }

                        Status.ERROR -> {
                            Timber.d("userInvoiceList:::::ERROR::::${userInvoiceList}")
                        }

                        else -> {}
                    }

                }
            }
        }
    }
}

@Composable
fun Delivered(data: List<InvoiceRes>?) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
    ) {
        if (data?.filter { invoice -> invoice.status == "Delivered" }.isNullOrEmpty()) {
            item {
                LottieComponent(resId = R.raw.empty_list)
            }
        } else {
            items(data?.filter { invoice -> invoice.status == "Delivered" }?.size.orDefault()) { index ->
                OrderItem(
                    orderNumber = data?.filter { invoice -> invoice.status == "Delivered" }
                        ?.getOrNull(index)?.id.orDefault().toString(),
                    quantity = "---",
                    totalAmount = "---",
                    orderDate = data?.filter { invoice -> invoice.status == "Delivered" }
                        ?.getOrNull(index)?.addDate.orDefault(),
                    orderState = OrderState.Delivered
                ) {

                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }

    }
}

@Composable
fun Processing(data: List<InvoiceRes>?) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
    ) {
        if (data?.filter { invoice -> invoice.status == "Processing" }.isNullOrEmpty()) {
            item {
                LottieComponent(resId = R.raw.empty_list)
            }
        } else {
            items(data?.filter { invoice -> invoice.status == "Processing" }?.size.orDefault()) { index ->
                OrderItem(
                    orderNumber = data?.filter { invoice -> invoice.status == "Processing" }
                        ?.getOrNull(index)?.id.orDefault().toString(),
                    quantity = "---",
                    totalAmount = "---",
                    orderDate = data?.filter { invoice -> invoice.status == "Processing" }
                        ?.getOrNull(index)?.addDate.orDefault(),
                    orderState = OrderState.Processing
                ) {

                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }

    }
}

@Composable
fun Cancelled(data: List<InvoiceRes>?) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
    ) {
        if (data?.filter { invoice -> invoice.status == "Cancelled" }.isNullOrEmpty()) {
            item {
                LottieComponent(resId = R.raw.empty_list)
            }
        } else {
            items(data?.filter { invoice -> invoice.status == "Cancelled" }?.size.orDefault()) { index ->
                OrderItem(
                    orderNumber = data?.filter { invoice -> invoice.status == "Cancelled" }
                        ?.getOrNull(index)?.id.orDefault().toString(),
                    quantity = "---",
                    totalAmount = "---",
                    orderDate = data?.filter { invoice -> invoice.status == "Cancelled" }
                        ?.getOrNull(index)?.addDate.orDefault(),
                    orderState = OrderState.Cancelled
                ) {

                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OrderViewPreview() {
//    OrderScreen(rememberNavController(), viewModel())
}