package com.technolearn.rasoulonlineshop.screens.auth

import android.util.Patterns
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.technolearn.rasoulonlineshop.R
import com.technolearn.rasoulonlineshop.helper.CustomButton
import com.technolearn.rasoulonlineshop.navigation.NavigationBarItemsGraph
import com.technolearn.rasoulonlineshop.ui.theme.Background
import com.technolearn.rasoulonlineshop.ui.theme.Black
import com.technolearn.rasoulonlineshop.ui.theme.Error
import com.technolearn.rasoulonlineshop.ui.theme.FontBold34
import com.technolearn.rasoulonlineshop.ui.theme.FontMedium14
import com.technolearn.rasoulonlineshop.ui.theme.Gray
import com.technolearn.rasoulonlineshop.ui.theme.Success
import com.technolearn.rasoulonlineshop.ui.theme.White
import com.technolearn.rasoulonlineshop.util.Extensions.orDefault
import com.technolearn.rasoulonlineshop.vm.ShopViewModel
import com.technolearn.rasoulonlineshop.vo.entity.UserLoginEntity
import com.technolearn.rasoulonlineshop.vo.enums.ButtonSize
import com.technolearn.rasoulonlineshop.vo.enums.ButtonStyle
import com.technolearn.rasoulonlineshop.vo.enums.Status
import com.technolearn.rasoulonlineshop.vo.req.LoginReq
import timber.log.Timber

object LoginState {
    var isLogin: Boolean by mutableStateOf(false)
}

@Composable
fun LoginScreen(navController: NavController, viewModel: ShopViewModel) {
    val loginStatus by remember { viewModel.loginStatus }.observeAsState()
    val context = LocalContext.current
    LaunchedEffect(loginStatus) {
        when (loginStatus?.status) {
            Status.LOADING -> {
                Timber.d("Login:::LOADING:::")
            }

            Status.SUCCESS -> {
                Timber.d("Login:::SUCCESS:::${loginStatus?.data}")
                when (loginStatus?.data?.status) {
                    in 100..199 -> {
                        Toast.makeText(context, loginStatus?.data?.message, Toast.LENGTH_SHORT)
                            .show()
                    }

                    in 200..299 -> {
                        LoginState.isLogin = true
                        viewModel.insertUser(
                            UserLoginEntity(
                                id = loginStatus?.data?.data?.id.orDefault(),
                                username = loginStatus?.data?.data?.username.orDefault(),
                                oldPassword = loginStatus?.data?.data?.oldPassword.orDefault(),
                                password = loginStatus?.data?.data?.password.orDefault(),
                                repeatPassword = loginStatus?.data?.data?.repeatPassword.orDefault(),
                                email = loginStatus?.data?.data?.email.orDefault(),
                                firstName = loginStatus?.data?.data?.firstName.orDefault(),
                                lastName = loginStatus?.data?.data?.lastName.orDefault(),
                                phone = loginStatus?.data?.data?.phone.orDefault(),
                                addressName = loginStatus?.data?.data?.addressName.orDefault(),
                                address = loginStatus?.data?.data?.address.orDefault(),
                                city = loginStatus?.data?.data?.city.orDefault(),
                                province = loginStatus?.data?.data?.province.orDefault(),
                                postalCode = loginStatus?.data?.data?.postalCode.orDefault(),
                                country = loginStatus?.data?.data?.country.orDefault(),
                                customerId = loginStatus?.data?.data?.customerId.orDefault(),
                                token = loginStatus?.data?.data?.token.orDefault(),
                                isLogin = LoginState.isLogin
                            )
                        )
                        navController.navigate(NavigationBarItemsGraph.Home.route) {
                            popUpTo(NavigationBarItemsGraph.Home.route) {
                                inclusive = true
                            }
                        }
                    }

                    in 300..399 -> {
                        Toast.makeText(context, loginStatus?.data?.message, Toast.LENGTH_SHORT)
                            .show()
                    }

                    in 400..499 -> {
                        Toast.makeText(context, loginStatus?.data?.message, Toast.LENGTH_SHORT)
                            .show()
                    }

                    in 500..599 -> {
                        Toast.makeText(context, loginStatus?.data?.message, Toast.LENGTH_SHORT)
                            .show()
                    }

                    else -> {
                    }
                }
            }

            Status.ERROR -> {
                Timber.d("Login:::ERROR:::${loginStatus}")

            }

            else -> {

            }
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            viewModel.resetUpdateUserPasswordStatus()
            viewModel.resetLoginStatus()
            // Additional cleanup or dispose of resources if needed
        }
    }

    Scaffold(
        backgroundColor = Background,
        bottomBar = {
        },
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "")
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigate(NavigationBarItemsGraph.Profile.route) {
                            popUpTo(NavigationBarItemsGraph.Profile.route) {
                                inclusive = true
                            }
                        }
                    }) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.ic_chevron_back),
                            contentDescription = "ic_back",
                        )
                    }
                },
                backgroundColor = Color.Transparent,
                elevation = 0.dp,
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
                    .padding(8.dp, 16.dp, 8.dp, 0.dp)
            ) {
                ///////LoginText
                Text(
                    text = "Login",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 14.dp),
                    style = FontBold34(Black),
                )
                ///////TextField
                val maxChar = 50
                var email by remember { mutableStateOf("") }
                var emailHasError by remember { mutableStateOf(false) }
                var emailIsValid by remember { mutableStateOf(false) }
                var emailLabel by remember { mutableStateOf("Email") }

                var password by remember { mutableStateOf("") }
                var passwordHasError by remember { mutableStateOf(false) }
                var passwordLabel by remember { mutableStateOf("Password") }

                TextField(
                    trailingIcon = {
                        if (emailIsValid) {
                            Icon(
                                imageVector = ImageVector.vectorResource(id = R.drawable.ic_tick),
                                contentDescription = "ic_tick",
                                tint = Success
                            )
                        } else if (email.isNotEmpty()) {
                            Icon(
                                imageVector = ImageVector.vectorResource(id = R.drawable.ic_cross),
                                contentDescription = "ic_cross",
                                tint = Error
                            )
                        }
                    },
                    value = email,
                    label = {
                        Text(
                            text = emailLabel,
                            style = FontMedium14(Gray)
                        )
                    },
                    onValueChange = { value ->
                        if (value.length <= maxChar) email = value
                        emailHasError = email.isEmpty()
                        emailIsValid = Patterns.EMAIL_ADDRESS.matcher(email).matches()
                        emailLabel = if (email.isNotEmpty()) "Email" else "Email Can Not Be Empty"
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 16.dp, start = 16.dp, top = 72.dp)
                        .shadow(4.dp)
                        .border(
                            width = 1.dp,
                            color = if (emailHasError) Error else Color.Transparent,
                            shape = RoundedCornerShape(4.dp)
                        ),
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = White,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        cursorColor = Black
                    ),
                    shape = RoundedCornerShape(4.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = password,
                    label = {
                        Text(
                            text = passwordLabel,
                            style = FontMedium14(Gray),
                        )
                    },
                    onValueChange = { value ->
                        if (value.length <= maxChar) password = value
                        passwordHasError = password.isEmpty()
//                        when {
//                            password.isNotEmpty() -> {
//                                passwordLabel = "Password"
//                            }
//                            password.length < 8 -> {
//                                passwordLabel = "Password Is Weak AtLeast Contain 8 Character"
//                            }
//                        }
                        if (password.isNotEmpty() && password.length < 8) {
                            passwordLabel = "Password Is Weak AtLeast Contain 8 Character"
                            passwordHasError = true
                        } else if (password.isEmpty()) {
                            passwordLabel = "Password Can Not Be Empty"
                        } else {
                            passwordLabel = "Password"
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 16.dp, start = 16.dp)
                        .shadow(4.dp)
                        .border(
                            width = 1.dp,
                            color = if (passwordHasError) Error else Color.Transparent,
                            shape = RoundedCornerShape(4.dp)
                        ),
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = White,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        cursorColor = Black
                    ),
                    shape = RoundedCornerShape(4.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    visualTransformation = PasswordVisualTransformation(),
                    singleLine = true,
                )
                ////////TextFieldEnd
                Spacer(modifier = Modifier.height(64.dp))
                /////////Button
                CustomButton(
                    onClick = {
                        when {
                            email.isEmpty() -> {
                                emailHasError = true
                                emailLabel = "Email Can Not Be Empty"
                            }

                            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                                emailHasError = true
                            }

                            password.isEmpty() -> {
                                passwordHasError = true
                                passwordLabel = "Password Can Not Be Empty"
                            }

                            password.length < 8 -> {
                                passwordHasError = true
                                passwordLabel = "Password Is Weak AtLeast Contain 8 Character"
                            }

                            !emailHasError && !passwordHasError -> {
                                viewModel.login(
                                    LoginReq(
                                        email = email,
                                        password = password,
                                    )
                                )
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    text = "LOGIN",
                    style = ButtonStyle.CONTAINED,
                    size = ButtonSize.BIG,
                    roundCorner = 25.dp,
                )
            }
        }
    }
    BackHandler {
        navController.navigate(NavigationBarItemsGraph.Profile.route) {
            popUpTo(NavigationBarItemsGraph.Profile.route) {
                inclusive = true
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginViewPreview() {
    LoginScreen(rememberNavController(), viewModel())
}
