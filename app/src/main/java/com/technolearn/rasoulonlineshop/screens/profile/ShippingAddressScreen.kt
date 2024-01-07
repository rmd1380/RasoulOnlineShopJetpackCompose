package com.technolearn.rasoulonlineshop.screens.profile

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.technolearn.rasoulonlineshop.R
import com.technolearn.rasoulonlineshop.helper.CustomTopAppBar
import com.technolearn.rasoulonlineshop.helper.LottieComponent
import com.technolearn.rasoulonlineshop.helper.ShippingAddressItem
import com.technolearn.rasoulonlineshop.mapper.toUpdateUserReq
import com.technolearn.rasoulonlineshop.navigation.NavigationBarItemsGraph
import com.technolearn.rasoulonlineshop.navigation.Screen
import com.technolearn.rasoulonlineshop.ui.theme.Background
import com.technolearn.rasoulonlineshop.ui.theme.Black
import com.technolearn.rasoulonlineshop.ui.theme.FontSemiBold18
import com.technolearn.rasoulonlineshop.ui.theme.White
import com.technolearn.rasoulonlineshop.util.Extensions.orDefault
import com.technolearn.rasoulonlineshop.util.Extensions.orFalse
import com.technolearn.rasoulonlineshop.vm.ShopViewModel
import com.technolearn.rasoulonlineshop.vo.entity.UserAddressEntity
import com.technolearn.rasoulonlineshop.vo.enums.Status
import com.technolearn.rasoulonlineshop.vo.req.UpdateReq
import kotlinx.coroutines.delay
import timber.log.Timber

@Composable
fun ShippingAddressScreen(navController: NavController, viewModel: ShopViewModel) {
    val context = LocalContext.current
    val userAddressList by remember { viewModel.getAllUserAddress() }.observeAsState()
    var selectedAddressIndex by remember { mutableStateOf<Int?>(null) }
    val userLoggedInInfo by remember { viewModel.getLoggedInUser() }.observeAsState()
    val updateUserStatus by remember { viewModel.updateUserStatus }.observeAsState()
    LaunchedEffect(Unit) {
        viewModel.getAllUserAddress()
        viewModel.getLoggedInUser()
    }
    LaunchedEffect(updateUserStatus) {
        when (updateUserStatus?.status) {
            Status.LOADING -> {
                Timber.d("Update:::LOADING:::${updateUserStatus?.data?.status}")
            }
            Status.SUCCESS -> {
                Timber.d("Update:::SUCCESS:::${updateUserStatus?.data}")
            }
            Status.ERROR -> {
                Timber.d("Update:::ERROR:::${updateUserStatus?.data?.status}")
            }
            else -> {}
        }
    }
    Scaffold(
        backgroundColor = Background,
        bottomBar = {},
        topBar = {
            CustomTopAppBar(
                title = stringResource(R.string.shipping_address),
                style = FontSemiBold18(Black),
                navigationIcon = ImageVector.vectorResource(R.drawable.ic_chevron_back),
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
            if (userAddressList.isNullOrEmpty()) {
                LottieComponent(R.raw.empty_list)
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {

                        LazyColumn(
                            modifier = Modifier
                                .fillMaxWidth(),
                            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 24.dp)
                        ) {
                            items(userAddressList?.size.orDefault()) { index ->
                                userAddressList?.get(index)?.let { it1 ->
                                    ShippingAddressItem(
                                        userAddressEntity = userAddressList.orEmpty()[index],
                                        navController = navController,
                                        isChecked = userAddressList.orEmpty()[index].isAddressSelected.orFalse(),
                                        onCheckedChange = { isChecked ->
                                            userAddressList.orEmpty()[index].isAddressSelected = isChecked
                                            if(isChecked){
                                                viewModel.updateUserAddress(userAddressList.orEmpty()[index])
                                                viewModel.updateUser(userLoggedInInfo?.token.orDefault(), updateReq = toUpdateUserReq(userAddressList.orEmpty()[index]))
                                            }else{
                                                viewModel.updateUserAddress(userAddressList.orEmpty()[index])
                                            }
                                            selectedAddressIndex = if (isChecked) index else null
                                        },
                                        edit = {},
                                        delete = {
                                            viewModel.deleteUserAddress(userAddressList.orEmpty()[index])
                                        }
                                    )
                                    Spacer(modifier = Modifier.height(24.dp))
                                }
                            }


                        }
                    }
                }
            }
            FloatingActionButton(
                onClick = { navController.navigate(Screen.AddShippingAddressScreen.route) },
                shape = CircleShape,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 32.dp),
                contentColor = White,
                containerColor = Black
            ) {
                Icon(Icons.Filled.Add, "Add floating action button")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ShippingAddressScreen() {
    ShippingAddressScreen(rememberNavController(), viewModel())
}