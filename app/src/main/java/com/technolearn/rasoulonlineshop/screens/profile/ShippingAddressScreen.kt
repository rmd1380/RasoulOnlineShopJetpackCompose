package com.technolearn.rasoulonlineshop.screens.profile

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
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
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
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
import com.technolearn.rasoulonlineshop.util.Constants
import com.technolearn.rasoulonlineshop.util.Extensions.orDefault
import com.technolearn.rasoulonlineshop.util.Extensions.orFalse
import com.technolearn.rasoulonlineshop.vm.ShopViewModel
import com.technolearn.rasoulonlineshop.vo.entity.UserAddressEntity
import com.technolearn.rasoulonlineshop.vo.entity.UserCreditCardEntity
import com.technolearn.rasoulonlineshop.vo.entity.UserLoginEntity
import com.technolearn.rasoulonlineshop.vo.enums.Status
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber

@Composable
fun ShippingAddressScreen(navController: NavController, viewModel: ShopViewModel) {
    val context = LocalContext.current
    val userAddressList by remember { viewModel.getAllUserAddress() }.observeAsState()
    val userLoggedInInfo by remember { viewModel.getLoggedInUser() }.observeAsState()
    val updateUserStatus by remember { viewModel.updateUserStatus }.observeAsState()
    val scope = rememberCoroutineScope()
    LaunchedEffect(userAddressList) {
        viewModel.getAllUserAddress()
    }
    LaunchedEffect(userLoggedInInfo) {
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
                                userAddressList?.get(index)?.let { userAddress ->
                                    val deletedAddressList =
                                        remember { mutableStateListOf<UserAddressEntity>() }
                                    Timber.d("userAddress:::${userAddress.id}:::index:::$index")
                                    val isSelected = userAddress.isAddressSelected
                                    AnimatedVisibility(
                                        visible = !deletedAddressList.contains(userAddress),
                                        enter = fadeIn() + slideInHorizontally(),
                                        exit = fadeOut() + slideOutHorizontally(
                                            animationSpec = tween(
                                                durationMillis = 1000
                                            )
                                        )
                                    ) {
                                        ShippingAddressItem(
                                            userAddressEntity = userAddress,
                                            navController = navController,
                                            selected = isSelected,
                                            onClick = {
                                                viewModel.clearSelectedAddressesExcept(userAddress.id)
                                                viewModel.updateUserAddress(
                                                    userAddress.copy(
                                                        isAddressSelected = true
                                                    )
                                                )
                                                if (!isSelected) {
                                                    viewModel.updateUser(
                                                        userLoggedInInfo?.token.orDefault(),
                                                        updateReq = toUpdateUserReq(userAddress)
                                                    )
                                                    viewModel.updateUserDB(
                                                        user = UserLoginEntity(
                                                            id = userAddress.userId.orDefault(),
                                                            username = userLoggedInInfo?.username.orDefault(),
                                                            oldPassword = userLoggedInInfo?.oldPassword.orDefault(),
                                                            password = userLoggedInInfo?.password.orDefault(),
                                                            repeatPassword = userLoggedInInfo?.repeatPassword.orDefault(),
                                                            email = userLoggedInInfo?.email.orDefault(),
                                                            firstName = userAddress.firstName.orDefault(),
                                                            lastName = userAddress.lastName.orDefault(),
                                                            phone = userAddress.phone.orDefault(),
                                                            addressName = userAddress.addressName.orDefault(),
                                                            address = userAddress.address.orDefault(),
                                                            city = userAddress.city.orDefault(),
                                                            province = userAddress.province.orDefault(),
                                                            postalCode = userAddress.postalCode.orDefault(),
                                                            country = userAddress.country.orDefault(),
                                                            customerId = userLoggedInInfo?.customerId.orDefault(),
                                                            token = userLoggedInInfo?.token.orDefault(),
                                                            isLogin = userLoggedInInfo?.isLogin.orFalse(),
                                                            dateOfBirth = userLoggedInInfo?.dateOfBirth.orDefault()
                                                        )
                                                    )
                                                }
                                            },
                                            edit = {
                                                navController.navigate(
                                                    Screen.AddShippingAddressScreen.passIsEditAndId(
                                                        true,
                                                        userAddress.id.toInt()
                                                    )
                                                )
                                            },
                                            delete = {
                                                deletedAddressList.add(userAddress)
                                                scope.launch {
                                                    delay(1000L)
                                                    viewModel.deleteUserAddress(userAddress)
                                                }

                                            }
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(24.dp))
                                }
                            }
                        }
                    }
                }
            }
            FloatingActionButton(
                onClick = { navController.navigate(Screen.AddShippingAddressScreen.passIsEditAndId(false,0)) },
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