package com.technolearn.rasoulonlineshop.screens.bag

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
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
import com.technolearn.rasoulonlineshop.navigation.BottomNavigationBar
import com.technolearn.rasoulonlineshop.navigation.NavigationBarItemsGraph
import com.technolearn.rasoulonlineshop.navigation.Screen
import com.technolearn.rasoulonlineshop.screens.auth.LoginState.isLogin
import com.technolearn.rasoulonlineshop.ui.theme.Background
import com.technolearn.rasoulonlineshop.ui.theme.Black
import com.technolearn.rasoulonlineshop.ui.theme.FontSemiBold18
import com.technolearn.rasoulonlineshop.util.Extensions.orDefault
import com.technolearn.rasoulonlineshop.util.Extensions.orFalse
import com.technolearn.rasoulonlineshop.vm.ShopViewModel
import com.technolearn.rasoulonlineshop.vo.entity.UserCartEntity
import com.technolearn.rasoulonlineshop.vo.enums.SearchWidgetState
import com.technolearn.rasoulonlineshop.vo.model.helperComponent.CustomAction
import kotlinx.coroutines.launch
import timber.log.Timber

@Composable
fun BagScreen(navController: NavController, viewModel: ShopViewModel) {

    val productInCartList by remember { viewModel.getAllUserCart() }.observeAsState()
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    var expanded by remember { mutableStateOf(false) }
    val isLoggedIn by remember { viewModel.isLoggedIn }.observeAsState()
    val searchWidgetState by remember { viewModel.searchWidgetState }
    val searchTextState by remember { viewModel.searchTextState }
    var query by remember { mutableStateOf("") }
    LaunchedEffect(productInCartList) {
        viewModel.getAllUserCart()
    }
    DisposableEffect(Unit) {
        viewModel.resetTextAndState()
        onDispose {
        }
    }
    Timber.d("productInCartListBagScreen:::$productInCartList")
    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        backgroundColor = Background,
        bottomBar = {
            BottomNavigationBar(
                list = MainActivity.navList,
                navController = navController,
            )
        },
        topBar = {
            CustomTopAppBar(
                searchWidgetState = searchWidgetState,
                searchTextState = searchTextState,
                onTextChange = {
                    viewModel.updateSearchTextState(newValue = it)
                },
                onCloseClicked = {
                    viewModel.updateSearchWidgetState(newValue = SearchWidgetState.CLOSED)
                    query = ""
                },
                onSearchClicked = {
                    Timber.d("Searched Text::$it")
                    query = it
                },
                title = stringResource(R.string.my_bag),
                style = FontSemiBold18(Black),
                navigationIcon = ImageVector.vectorResource(R.drawable.ic_chevron_back),
                actionIcons = listOf(
                    CustomAction(
                        "search",
                        painterResource(id = R.drawable.ic_search)
                    )
                ),
                navigationOnClick = {
                    navController.navigate(NavigationBarItemsGraph.Home.route) {
                        popUpTo(NavigationBarItemsGraph.Home.route) {
                            inclusive = true
                        }
                    }
                },
                actionOnclick = {
                    when (it.name) {
                        "search" -> {
                            viewModel.updateSearchWidgetState(newValue = SearchWidgetState.OPENED)
                        }
                    }
                }
            )
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            if (productInCartList.isNullOrEmpty()) {
                LottieComponent(R.raw.empty_bag)
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        contentPadding = PaddingValues(bottom = 16.dp)
                    ) {
                        if (query.isEmpty()) {
                            items(productInCartList?.size.orDefault()) { index ->
                                productInCartList?.get(index)?.let { userCart ->
                                    CartItem(
                                        userCartEntity = userCart,
                                        navController = navController,
                                        viewModel = viewModel,
                                        more = {

                                        },
                                        increase = {
                                            viewModel.incrementQuantity(userCart.id.orDefault())
                                        },
                                        decrease = {
                                            viewModel.decrementQuantity(userCart.id.orDefault())
                                        }
                                    )

                                }
                            }
                        } else {
                            items(productInCartList?.filter { userCartEntity: UserCartEntity ->
                                userCartEntity.name?.contains(
                                    query.orDefault()
                                ).orFalse()
                            }?.size.orDefault()) { index ->
                                productInCartList?.filter { userCartEntity: UserCartEntity ->
                                    userCartEntity.name?.contains(
                                        query.orDefault()
                                    ).orFalse()
                                }?.get(index)?.let { userCart ->
                                    CartItem(
                                        userCartEntity = userCart,
                                        navController = navController,
                                        viewModel = viewModel,
                                        more = {

                                        },
                                        increase = {
                                            viewModel.incrementQuantity(userCart.id.orDefault())
                                        },
                                        decrease = {
                                            viewModel.decrementQuantity(userCart.id.orDefault())
                                        }
                                    )

                                }
                            }
                        }

                    }
                }

                CustomButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 16.dp),
                    text = stringResource(R.string.check_out),
                    onClick = {
                        if (isLoggedIn.orFalse()) {
                            navController.navigate(route = Screen.CheckOutScreen.route)
                        } else {
                            scope.launch {
                                val result = snackbarHostState
                                    .showSnackbar(
                                        message = "First, log in to your account",
                                        actionLabel = "Log In",
                                        duration = SnackbarDuration.Short
                                    )
                                when (result) {
                                    SnackbarResult.ActionPerformed -> {
                                        navController.navigate(route = NavigationBarItemsGraph.Profile.route)
                                    }

                                    SnackbarResult.Dismissed -> {
                                        snackbarHostState.currentSnackbarData?.dismiss()
                                    }
                                }
                            }
                        }
                    }
                )
            }
        }
    }
}