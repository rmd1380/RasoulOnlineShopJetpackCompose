package com.technolearn.rasoulonlineshop.screens.profile

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.technolearn.rasoulonlineshop.R
import com.technolearn.rasoulonlineshop.helper.CreditCard
import com.technolearn.rasoulonlineshop.helper.CustomButton
import com.technolearn.rasoulonlineshop.helper.CustomTopAppBar
import com.technolearn.rasoulonlineshop.helper.LottieComponent
import com.technolearn.rasoulonlineshop.navigation.NavigationBarItemsGraph
import com.technolearn.rasoulonlineshop.ui.theme.Background
import com.technolearn.rasoulonlineshop.ui.theme.Black
import com.technolearn.rasoulonlineshop.ui.theme.Error
import com.technolearn.rasoulonlineshop.ui.theme.FontMedium14
import com.technolearn.rasoulonlineshop.ui.theme.FontSemiBold16
import com.technolearn.rasoulonlineshop.ui.theme.FontSemiBold18
import com.technolearn.rasoulonlineshop.ui.theme.Gray
import com.technolearn.rasoulonlineshop.ui.theme.White
import com.technolearn.rasoulonlineshop.util.Extensions.orDefault
import com.technolearn.rasoulonlineshop.vm.ShopViewModel
import com.technolearn.rasoulonlineshop.vo.entity.UserCreditCardEntity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentMethodsScreen(navController: NavController, viewModel: ShopViewModel) {
//    val context = LocalContext.current

    val userCreditCardList by remember { viewModel.getAllUserCreditCard() }.observeAsState()
    LaunchedEffect(userCreditCardList) {
        viewModel.getAllUserCreditCard()
    }
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState()
    var showAddCreditCardBottomSheet by rememberSaveable { mutableStateOf(false) }

    val maxCharCardName = 30
    var cardName by remember { mutableStateOf("") }
    var cardNameHasError by remember { mutableStateOf(false) }
    var cardNameLabel by remember { mutableStateOf("Name on card") }

    val maxCharCardNumber = 16
    var cardNumber by remember { mutableStateOf("") }
    var cardNumberHasError by remember { mutableStateOf(false) }
    var cardNumberLabel by remember { mutableStateOf("Card Number") }

    val maxCharCardExpireDate = 4
    var cardExpireDate by remember { mutableStateOf("") }
    var cardExpireDateHasError by remember { mutableStateOf(false) }
    var cardExpireDateLabel by remember { mutableStateOf("Expire Date") }

    val maxCharCardCvv2 = 4
    var cardCvv2 by remember { mutableStateOf("") }
    var cardCvv2HasError by remember { mutableStateOf(false) }
    var cardCvv2Label by remember { mutableStateOf("Cvv2") }

    Scaffold(
        backgroundColor = Background,
        bottomBar = {},
        topBar = {
            CustomTopAppBar(
                title = stringResource(R.string.payment_methods),
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
            if (userCreditCardList.isNullOrEmpty()) {
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
                            item {
                                Text(
                                    text = stringResource(R.string.your_payment_card),
                                    style = FontSemiBold16(Black),
                                )
                                Spacer(modifier = Modifier.height(24.dp))
                            }
                            items(userCreditCardList?.size.orDefault()) { index ->
                                userCreditCardList?.get(index)?.let { userCreditCard ->
                                    val deletedCreditList =
                                        remember { mutableStateListOf<UserCreditCardEntity>() }
                                    Timber.d("userCreditCard:::${userCreditCard.id}:::index:::$index")
                                    val isSelected = userCreditCard.isCardSelected
                                    AnimatedVisibility(
                                        visible = !deletedCreditList.contains(userCreditCard),
                                        enter = fadeIn() + slideInHorizontally(),
                                        exit = fadeOut() + slideOutHorizontally(
                                            animationSpec = tween(
                                                durationMillis = 1000
                                            )
                                        )
                                    ) {
                                        CreditCard(
                                            userCreditCardEntity = userCreditCard,
                                            selected = isSelected,
                                            onClick = {
                                                viewModel.clearSelectedCreditCardExcept(
                                                    userCreditCard.id
                                                )
                                                viewModel.updateUserCreditCard(
                                                    userCreditCard.copy(
                                                        isCardSelected = true
                                                    )
                                                )
                                            },
                                            deleteCard = {
                                                deletedCreditList.add(userCreditCard)
                                                scope.launch {
                                                    delay(1000L)
                                                    viewModel.deleteUserCreditCard(userCreditCard)
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
            if (showAddCreditCardBottomSheet) {
                ModalBottomSheet(
                    sheetState = sheetState,
                    onDismissRequest = {
                        showAddCreditCardBottomSheet = false
                        cardName = ""
                        cardNameHasError=false
                        cardNameLabel="Name on card"
                        cardNumber = ""
                        cardNumberHasError=false
                        cardNumberLabel="Card Number"
                        cardExpireDate = ""
                        cardExpireDateHasError=false
                        cardExpireDateLabel="Expire Date"
                        cardCvv2 = ""
                        cardCvv2HasError=false
                        cardCvv2Label="Cvv2"
                    }
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 16.dp, start = 16.dp, bottom = 64.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = stringResource(R.string.add_new_card),
                            style = FontSemiBold18(Black),
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                        //region CardName
                        TextField(
                            value = cardName,
                            label = {
                                Text(
                                    text = cardNameLabel,
                                    style = FontMedium14(Gray),
                                )
                            },
                            onValueChange = { value ->
                                if (value.length <= maxCharCardName) cardName = value
                                cardNameHasError = cardName.isEmpty()
                                cardNameLabel =
                                    if (cardName.isNotEmpty()) "Name on card" else "Card Name Can Not Be Empty"
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .shadow(4.dp)
                                .border(
                                    width = 1.dp,
                                    color = if (cardNameHasError) Error else Color.Transparent,
                                    shape = RoundedCornerShape(4.dp)
                                ),
                            colors = TextFieldDefaults.textFieldColors(
                                backgroundColor = White,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                cursorColor = Black
                            ),
                            shape = RoundedCornerShape(4.dp),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Next),
                            singleLine = true
                        )
                        //endregion
                        Spacer(modifier = Modifier.height(20.dp))
                        //region CardNumber
                        TextField(
                            value = cardNumber,
                            label = {
                                Text(
                                    text = cardNumberLabel,
                                    style = FontMedium14(Gray),
                                )
                            },
                            onValueChange = { value ->
                                if (value.length <= maxCharCardNumber) cardNumber = value
                                cardNumberHasError = cardNumber.isEmpty()
                                cardNumberLabel =
                                    if (cardNumber.isNotEmpty()) "Card Number" else "Card Number Can Not Be Empty"
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .shadow(4.dp)
                                .border(
                                    width = 1.dp,
                                    color = if (cardNumberHasError) Error else Color.Transparent,
                                    shape = RoundedCornerShape(4.dp)
                                ),
                            colors = TextFieldDefaults.textFieldColors(
                                backgroundColor = White,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                cursorColor = Black
                            ),
                            shape = RoundedCornerShape(4.dp),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next),
                            singleLine = true
                        )
                        //endregion
                        Spacer(modifier = Modifier.height(20.dp))
                        //region CardExpireDate
                        TextField(
                            placeholder={ Text(text = "Y/M",style = FontMedium14(Gray))},
                            value = cardExpireDate,
                            label = {
                                Text(
                                    text = cardExpireDateLabel,
                                    style = FontMedium14(Gray),
                                )
                            },
                            onValueChange = { value ->
                                if (value.length <= maxCharCardExpireDate) cardExpireDate = value
                                cardExpireDateHasError = cardExpireDate.isEmpty()
                                cardExpireDateLabel =
                                    if (cardExpireDate.isNotEmpty()) "Expire Date" else "Expire Date Can Not Be Empty"
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .shadow(4.dp)
                                .border(
                                    width = 1.dp,
                                    color = if (cardExpireDateHasError) Error else Color.Transparent,
                                    shape = RoundedCornerShape(4.dp)
                                ),
                            colors = TextFieldDefaults.textFieldColors(
                                backgroundColor = White,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                cursorColor = Black
                            ),
                            shape = RoundedCornerShape(4.dp),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next),
                            singleLine = true
                        )
                        //endregion
                        Spacer(modifier = Modifier.height(20.dp))
                        //region CardCvv2
                        TextField(
                            value = cardCvv2,
                            label = {
                                Text(
                                    text = cardCvv2Label,
                                    style = FontMedium14(Gray),
                                )
                            },
                            onValueChange = { value ->
                                if (value.length <= maxCharCardCvv2) cardCvv2 = value
                                cardCvv2HasError = cardCvv2.isEmpty()
                                cardCvv2Label =
                                    if (cardCvv2.isNotEmpty()) "Cvv2" else "Cvv2 Can Not Be Empty"
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .shadow(4.dp)
                                .border(
                                    width = 1.dp,
                                    color = if (cardCvv2HasError) Error else Color.Transparent,
                                    shape = RoundedCornerShape(4.dp)
                                ),
                            colors = TextFieldDefaults.textFieldColors(
                                backgroundColor = White,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                cursorColor = Black
                            ),
                            shape = RoundedCornerShape(4.dp),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done),
                            singleLine = true
                        )
                        //endregion
                        Spacer(modifier = Modifier.height(20.dp))
                        CustomButton(
                            modifier = Modifier
                                .fillMaxWidth(),
                            text = stringResource(R.string.add_card),
                            onClick = {
                                when {
                                    cardName.isEmpty() -> {
                                        cardNameHasError = true
                                        cardNameLabel = "Card Name Can Not Be Empty"
                                    }

                                    cardNumber.isEmpty() -> {
                                        cardNumberHasError = true
                                        cardNumberLabel = "Card Number Can Not Be Empty"
                                    }

                                    cardNumber.length < 16 -> {
                                        cardNumberHasError = true
                                        cardNumberLabel = "Card Number Has 16 Number"
                                    }

                                    cardExpireDate.isEmpty() -> {
                                        cardExpireDateHasError = true
                                        cardExpireDateLabel = "Expire Date Can Not Be Empty"
                                    }

                                    cardCvv2.isEmpty() -> {
                                        cardCvv2HasError = true
                                        cardCvv2Label = "CVV2 Can Not Be Empty"
                                    }

                                    !cardNameHasError
                                            && !cardNumberHasError
                                            && !cardExpireDateHasError
                                            && !cardCvv2HasError -> {
                                        viewModel.insertUserCreditCard(
                                            UserCreditCardEntity(
                                                cardName = cardName,
                                                cardNumber = cardNumber,
                                                cardExpirationDate = cardExpireDate,
                                                cvv2 = cardCvv2,
                                            )
                                        )
                                        showAddCreditCardBottomSheet = false
                                        cardName = ""
                                        cardNameHasError=false
                                        cardNameLabel="Name on card"
                                        cardNumber = ""
                                        cardNumberHasError=false
                                        cardNumberLabel="Card Number"
                                        cardExpireDate = ""
                                        cardExpireDateHasError=false
                                        cardExpireDateLabel="Expire Date"
                                        cardCvv2 = ""
                                        cardCvv2HasError=false
                                        cardCvv2Label="Cvv2"
                                    }
                                }
                            }
                        )
                    }
                }
            }
            FloatingActionButton(
                onClick = {
                    showAddCreditCardBottomSheet = true
                },
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