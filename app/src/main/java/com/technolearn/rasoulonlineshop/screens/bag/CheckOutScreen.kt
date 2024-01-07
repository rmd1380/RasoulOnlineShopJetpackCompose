package com.technolearn.rasoulonlineshop.screens.bag

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.technolearn.rasoulonlineshop.MainActivity
import com.technolearn.rasoulonlineshop.R
import com.technolearn.rasoulonlineshop.helper.CartItem
import com.technolearn.rasoulonlineshop.helper.CustomButton
import com.technolearn.rasoulonlineshop.helper.CustomTopAppBar
import com.technolearn.rasoulonlineshop.helper.LottieComponent
import com.technolearn.rasoulonlineshop.helper.ShippingAddressItem
import com.technolearn.rasoulonlineshop.helper.ShippingAddressItemCheckout
import com.technolearn.rasoulonlineshop.navigation.BottomNavigationBar
import com.technolearn.rasoulonlineshop.navigation.NavigationBarItemsGraph
import com.technolearn.rasoulonlineshop.navigation.Screen
import com.technolearn.rasoulonlineshop.ui.theme.Background
import com.technolearn.rasoulonlineshop.ui.theme.Black
import com.technolearn.rasoulonlineshop.ui.theme.FontSemiBold18
import com.technolearn.rasoulonlineshop.util.Extensions.orDefault
import com.technolearn.rasoulonlineshop.vm.ShopViewModel
import com.technolearn.rasoulonlineshop.vo.entity.UserAddressEntity
import com.technolearn.rasoulonlineshop.vo.model.helperComponent.CustomAction
import timber.log.Timber

@Composable
fun CheckOutScreen(navController: NavController, viewModel: ShopViewModel) {
    val context = LocalContext.current
    LaunchedEffect(Unit){
        viewModel.getLoggedInUser()
    }
    val userLoggedInInfo by remember { viewModel.getLoggedInUser() }.observeAsState()
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
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
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
                ) {

                }
            }
        }
    }
}