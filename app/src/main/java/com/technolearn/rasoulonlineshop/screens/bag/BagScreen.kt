package com.technolearn.rasoulonlineshop.screens.bag

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import com.technolearn.rasoulonlineshop.MainActivity
import com.technolearn.rasoulonlineshop.navigation.BottomNavigationBar
import com.technolearn.rasoulonlineshop.ui.theme.Background

@Composable
fun BagScreen(navController: NavController) {

    Scaffold(
        backgroundColor = Background,
        bottomBar = {
            BottomNavigationBar(
                list = MainActivity.navList,
                navController = navController,
            )
        },
        topBar = {

        }

    ){
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {

            Text(text = "BagScreen", textAlign = TextAlign.Center)
        }
    }

}