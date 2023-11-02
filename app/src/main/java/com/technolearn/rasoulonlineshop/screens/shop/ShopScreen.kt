package com.technolearn.rasoulonlineshop.screens.shop

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.progressSemantics
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.room.PrimaryKey
import com.skydoves.landscapist.glide.GlideImage
import com.technolearn.rasoulonlineshop.MainActivity
import com.technolearn.rasoulonlineshop.R
import com.technolearn.rasoulonlineshop.helper.CustomTopAppBar
import com.technolearn.rasoulonlineshop.navigation.BottomNavigationBar
import com.technolearn.rasoulonlineshop.navigation.Screen
import com.technolearn.rasoulonlineshop.ui.theme.Background
import com.technolearn.rasoulonlineshop.ui.theme.Black
import com.technolearn.rasoulonlineshop.ui.theme.FontMedium14
import com.technolearn.rasoulonlineshop.ui.theme.FontSemiBold18
import com.technolearn.rasoulonlineshop.ui.theme.FontSemiBold24
import com.technolearn.rasoulonlineshop.ui.theme.Primary
import com.technolearn.rasoulonlineshop.ui.theme.White
import com.technolearn.rasoulonlineshop.util.Extensions.orDefault
import com.technolearn.rasoulonlineshop.vm.ShopViewModel
import com.technolearn.rasoulonlineshop.vo.res.CategoryRes
import timber.log.Timber

@Composable
fun ShopScreen(navController: NavController, viewModel: ShopViewModel) {

    val category by remember { viewModel.getAllCategory() }.observeAsState()
    Scaffold(
        backgroundColor = Background,
        bottomBar = {
            BottomNavigationBar(
                list = MainActivity.navList,
                navController = navController,
            )
        },
        topBar = {
            CustomTopAppBar(
                title = stringResource(R.string.categories),
                style = FontSemiBold18(Black),
                navigationIcon = ImageVector.vectorResource(R.drawable.ic_chevron_back),
                navigationOnClick = { /*TODO*/ },
                actionOnclick = { /*TODO*/ }
            )
        }

    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                //Ads
                item {
                    Card(
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .padding(horizontal = 16.dp, vertical = 16.dp)
                            .clickable {

                            },
                        elevation = 2.dp,
                        backgroundColor = Primary
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 28.dp)
                        ) {
                            Text(
                                text = "AUTUMN SALES",
                                style = FontSemiBold24(White),
                                textAlign = TextAlign.Center,
                                modifier = Modifier.fillMaxWidth()
                            )
                            Text(
                                text = "Up to 50% off",
                                style = FontMedium14(White),
                                textAlign = TextAlign.Center,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }
                //Categories
                items(category?.data?.data?.size.orDefault()) { categoryRes ->
                    CategoryItem(
                        categoryRes = category?.data?.data?.get(categoryRes) ?: CategoryRes(),
                        navController
                    )
                }
            }
        }
    }
}

@Composable
fun CategoryItem(categoryRes: CategoryRes, navController: NavController) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .padding(horizontal = 16.dp, vertical = 16.dp)
            .clickable {

            },
        elevation = 2.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 22.dp)
                .aspectRatio(343f / 100f, true)
                .height(100.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = categoryRes.title.orDefault(),
                style = FontSemiBold18(Black),
                modifier = Modifier.weight(1f)
            )
            GlideImage(
                imageModel = categoryRes.image.orDefault(),
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(topEnd = 8.dp, bottomEnd = 8.dp)),
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
        }
    }
}
