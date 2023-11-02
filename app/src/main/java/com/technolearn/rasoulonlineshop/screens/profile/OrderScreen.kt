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
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.technolearn.rasoulonlineshop.MainActivity
import com.technolearn.rasoulonlineshop.R
import com.technolearn.rasoulonlineshop.helper.CustomTopAppBar
import com.technolearn.rasoulonlineshop.helper.ProfileItem
import com.technolearn.rasoulonlineshop.navigation.BottomNavigationBar
import com.technolearn.rasoulonlineshop.screens.auth.LoginScreen
import com.technolearn.rasoulonlineshop.ui.theme.Background
import com.technolearn.rasoulonlineshop.ui.theme.Black
import com.technolearn.rasoulonlineshop.ui.theme.FontBold34
import com.technolearn.rasoulonlineshop.ui.theme.FontRegular11
import com.technolearn.rasoulonlineshop.ui.theme.FontRegular14
import com.technolearn.rasoulonlineshop.ui.theme.FontSemiBold18
import com.technolearn.rasoulonlineshop.ui.theme.Gray
import com.technolearn.rasoulonlineshop.ui.theme.White
import com.technolearn.rasoulonlineshop.vo.model.helperComponent.CustomAction

@Composable
fun OrderScreen(navController: NavController) {

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
                navigationIcon = ImageVector.vectorResource(R.drawable.ic_chevron_back),
                actionIcons = listOf(
                    CustomAction(
                        "search",
                        ImageVector.vectorResource(R.drawable.ic_search)
                    )
                ),
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
                    text = "My Order",
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
                        Text(text = "rasoul", style = FontSemiBold18(Black))
                        Text(text = "rasoul@gmail.com", style = FontRegular14(Gray))
                    }
                }
                Spacer(modifier = Modifier.height(28.dp))
                ProfileItem(title = "My orders", subtitle = "Already have 12 orders") {}
                Spacer(modifier = Modifier.height(16.dp))
                ProfileItem(title = "Shipping addresses", subtitle = "3 addresses") {}
                Spacer(modifier = Modifier.height(16.dp))
                ProfileItem(title = "Payment methods", subtitle = "Visa **34") {}
                Spacer(modifier = Modifier.height(16.dp))
                ProfileItem(title = "Settings", subtitle = "password") {}

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun orderViewPreview() {
    OrderScreen(rememberNavController())
}