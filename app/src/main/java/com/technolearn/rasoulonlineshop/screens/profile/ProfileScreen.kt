package com.technolearn.rasoulonlineshop.screens.profile

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
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.technolearn.rasoulonlineshop.MainActivity
import com.technolearn.rasoulonlineshop.R
import com.technolearn.rasoulonlineshop.helper.CustomTopAppBar
import com.technolearn.rasoulonlineshop.helper.ProfileItem
import com.technolearn.rasoulonlineshop.navigation.BottomNavigationBar
import com.technolearn.rasoulonlineshop.navigation.Screen
import com.technolearn.rasoulonlineshop.ui.theme.Black
import com.technolearn.rasoulonlineshop.ui.theme.FontBold34
import com.technolearn.rasoulonlineshop.ui.theme.FontRegular14
import com.technolearn.rasoulonlineshop.ui.theme.FontSemiBold18
import com.technolearn.rasoulonlineshop.ui.theme.Gray
import com.technolearn.rasoulonlineshop.ui.theme.White
import com.technolearn.rasoulonlineshop.util.Extensions.orDefault
import com.technolearn.rasoulonlineshop.vm.ShopViewModel

@Composable
fun ProfileScreen(navController: NavController,viewModel: ShopViewModel) {
    val userLoggedInInfo by remember { viewModel.getLoggedInUser() }.observeAsState()

    Scaffold(
        backgroundColor = White,
        bottomBar = {
            BottomNavigationBar(
                list = MainActivity.navList,
                navController = navController,
            )
        },
        topBar = {
            CustomTopAppBar(
                title = "",
                style = null,
                navigationOnClick = { /*TODO*/ },
                actionOnclick = {}
            )
        },
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = "My profile",
                    style = FontBold34(Black),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )
                Spacer(modifier = Modifier.height(24.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_profile_activated),
                        contentDescription = "ic_profile",
                        modifier = Modifier.size(64.dp)
                    )
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Text(text = userLoggedInInfo?.username.orDefault(), style = FontSemiBold18(Black))
                        Text(text = userLoggedInInfo?.email.orDefault(), style = FontRegular14(Gray))
                    }
                }
                Spacer(modifier = Modifier.height(28.dp))
                ProfileItem(title = "My orders", subtitle = "See Your Orders") {
                    navController.navigate(Screen.OrderScreen.route)
                }
                Spacer(modifier = Modifier.height(16.dp))
                ProfileItem(title = "Shipping addresses", subtitle = "Submit Addresses") {
                    navController.navigate(Screen.ShippingAddressScreen.route)
                }
                Spacer(modifier = Modifier.height(16.dp))
                ProfileItem(title = "Payment methods", subtitle = "Credit Card") {
                    navController.navigate(Screen.PaymentMethodsScreen.route)
                }
                Spacer(modifier = Modifier.height(16.dp))
                ProfileItem(title = "Settings", subtitle = "Password") {
                    navController.navigate(Screen.SettingsScreen.route)
                }

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileViewPreview() {
    ProfileScreen(rememberNavController(), viewModel())
}