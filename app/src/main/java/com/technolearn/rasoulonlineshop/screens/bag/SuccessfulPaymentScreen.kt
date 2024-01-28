package com.technolearn.rasoulonlineshop.screens.bag

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.technolearn.rasoulonlineshop.R
import com.technolearn.rasoulonlineshop.helper.CustomButton
import com.technolearn.rasoulonlineshop.navigation.NavigationBarItemsGraph
import com.technolearn.rasoulonlineshop.navigation.Screen
import com.technolearn.rasoulonlineshop.ui.theme.Background
import com.technolearn.rasoulonlineshop.ui.theme.Black
import com.technolearn.rasoulonlineshop.ui.theme.FontBold34
import com.technolearn.rasoulonlineshop.ui.theme.FontRegular14

@Composable
fun SuccessfulPaymentScreen(navController: NavController) {
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
                    painter = painterResource(R.drawable.bags),
                    contentDescription = "bags",
                )
                Spacer(modifier = Modifier.height(36.dp))
                Text(
                    text = stringResource(R.string.success),
                    style = FontBold34(Black),
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "Your order will be delivered soon. \n Thank you for choosing our app!",
                    style = FontRegular14(Black),
                )
            }
            CustomButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 32.dp),
                text = stringResource(R.string.continue_shopping),
                onClick = {
                    navController.navigate(NavigationBarItemsGraph.Home.route){
                        popUpTo(NavigationBarItemsGraph.Home.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }
    }
    BackHandler {
        navController.navigate(NavigationBarItemsGraph.Home.route){
            popUpTo(NavigationBarItemsGraph.Home.route) {
                inclusive = true
            }
        }
    }
}