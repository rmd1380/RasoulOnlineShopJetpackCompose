package com.technolearn.rasoulonlineshop.screens.auth

import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.technolearn.rasoulonlineshop.R
import com.technolearn.rasoulonlineshop.helper.CustomButton
import com.technolearn.rasoulonlineshop.navigation.Screen
import com.technolearn.rasoulonlineshop.ui.theme.Background
import com.technolearn.rasoulonlineshop.ui.theme.Black
import com.technolearn.rasoulonlineshop.ui.theme.Error
import com.technolearn.rasoulonlineshop.ui.theme.FontBold34
import com.technolearn.rasoulonlineshop.ui.theme.FontMedium14
import com.technolearn.rasoulonlineshop.ui.theme.Gray
import com.technolearn.rasoulonlineshop.ui.theme.Success
import com.technolearn.rasoulonlineshop.ui.theme.White
import com.technolearn.rasoulonlineshop.vm.ShopViewModel
import com.technolearn.rasoulonlineshop.vo.enums.ButtonSize
import com.technolearn.rasoulonlineshop.vo.enums.ButtonStyle
import com.technolearn.rasoulonlineshop.vo.enums.Status
import com.technolearn.rasoulonlineshop.vo.req.SignUpReq
import timber.log.Timber

@Composable
fun SignUpScreen(navController: NavController, viewModel: ShopViewModel) {
    val registerStatus by remember { viewModel.registerStatus }.observeAsState()
    val context = LocalContext.current
    LaunchedEffect(registerStatus) {
        when (registerStatus?.status) {
            Status.LOADING -> {
                Timber.d("Register:::LOADING:::")
            }

            Status.SUCCESS -> {
                Timber.d("Register:::SUCCESS:::${registerStatus?.data?.status}")
                when (registerStatus?.data?.status) {
                    in 100..199 -> {
                        Toast.makeText(context, registerStatus?.data?.message, Toast.LENGTH_SHORT)
                            .show()
                    }

                    in 200..299 -> {
                        navController.navigate(Screen.LoginScreen.route)
                    }

                    in 300..399 -> {
                        Toast.makeText(context, registerStatus?.data?.message, Toast.LENGTH_SHORT)
                            .show()
                    }

                    in 400..499 -> {
                        Toast.makeText(context, registerStatus?.data?.message, Toast.LENGTH_SHORT)
                            .show()
                    }

                    in 500..599 -> {
                        Toast.makeText(context, registerStatus?.data?.message, Toast.LENGTH_SHORT)
                            .show()
                    }

                    else -> {
                    }
                }
            }

            Status.ERROR -> {
                Timber.d("Register:::ERROR:::${registerStatus}")

            }

            else -> {

            }
        }
    }
    DisposableEffect(Unit) {
        onDispose {
            viewModel.resetRegisterStatus() // Add a function to reset the status in your ViewModel
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Background),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp, 16.dp, 8.dp, 0.dp)
        ) {
            ///////SignUpText
            Text(
                text = "SignUp",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 14.dp),
                style = FontBold34(Black),
            )
            ///////TextField
            val maxChar = 50
            var name by remember { mutableStateOf("") }
            var nameHasError by remember { mutableStateOf(false) }
            var nameLabel by remember { mutableStateOf("Name") }

            var email by remember { mutableStateOf("") }
            var emailHasError by remember { mutableStateOf(false) }
            var emailIsValid by remember { mutableStateOf(false) }
            var emailLabel by remember { mutableStateOf("Email") }

            var password by remember { mutableStateOf("") }
            var passwordHasError by remember { mutableStateOf(false) }
            var passwordLabel by remember { mutableStateOf("Password") }

            TextField(
                value = name,
                label = {
                    Text(
                        text = nameLabel,
                        style = FontMedium14(Gray),
                    )
                },
                onValueChange = { value ->
                    if (value.length <= maxChar) name = value
                    nameHasError = name.isEmpty()
                    nameLabel = if (name.isNotEmpty()) "Name" else "Name Can Not Be Empty"
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 16.dp, start = 16.dp, top = 72.dp)
                    .shadow(4.dp)
                    .border(
                        width = 1.dp,
                        color = if (nameHasError) Error else Color.Transparent,
                        shape = RoundedCornerShape(4.dp)
                    ),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    cursorColor = Black
                ),
                shape = RoundedCornerShape(4.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                singleLine = true,
            )
            Spacer(modifier = Modifier.height(8.dp))
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
                        style = FontMedium14(Gray),
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
                    .padding(end = 16.dp, start = 16.dp)
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
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "Already Have An Account?",
                    style = FontMedium14(Black),
                    modifier = Modifier
                        .padding(end = 4.dp)
                        .clickable(
                            interactionSource = MutableInteractionSource(),
                            indication = null
                        ) {
                            navController.navigate(Screen.LoginScreen.route)
                        },
                    textAlign = TextAlign.End,
                )
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_right_arrow),
                    contentDescription = "ic_right",
                    tint = Error,
                    modifier = Modifier
                        .clickable(
                            interactionSource = MutableInteractionSource(),
                            indication = null
                        ) {
                            navController.navigate(Screen.LoginScreen.route)
                        }
                )
            }

            Spacer(modifier = Modifier.height(64.dp))
            /////////Button
            CustomButton(
                onClick = {
                    when {
                        name.isEmpty() -> {
                            nameHasError = true
                            nameLabel = "Name Can Not Be Empty"
                        }

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

                        !nameHasError && !emailHasError && !passwordHasError -> {
                            viewModel.register(
                                SignUpReq(
                                    username = name,
                                    password = password,
                                    email = email
                                )
                            )
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                text = "SIGN UP",
                style = ButtonStyle.CONTAINED,
                size = ButtonSize.BIG,
                roundCorner = 25.dp,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SignUpPreview() {
    SignUpScreen(rememberNavController(), viewModel())
}
