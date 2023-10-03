package com.technolearn.rasoulonlineshop.screens

import android.util.Patterns
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
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.technolearn.rasoulonlineshop.R
import com.technolearn.rasoulonlineshop.navigation.Screen
import com.technolearn.rasoulonlineshop.ui.theme.Background
import com.technolearn.rasoulonlineshop.ui.theme.Black
import com.technolearn.rasoulonlineshop.ui.theme.Error
import com.technolearn.rasoulonlineshop.ui.theme.Gray
import com.technolearn.rasoulonlineshop.ui.theme.MetroPoliceFontFamily
import com.technolearn.rasoulonlineshop.ui.theme.Primary
import com.technolearn.rasoulonlineshop.ui.theme.Success
import com.technolearn.rasoulonlineshop.ui.theme.White

@Composable
fun SignUpScreen(navController: NavController) {
    val contextForToast = LocalContext.current.applicationContext
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
                fontFamily = MetroPoliceFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 34.sp,
                color = Black
            )
            ///////TextField
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
                        color = Gray,
                        fontFamily = MetroPoliceFontFamily,
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp,
                    )
                },
                onValueChange = { value ->
                    name = value
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
                singleLine = true
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
                        color = Gray,
                        fontFamily = MetroPoliceFontFamily,
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp,
                    )
                },
                onValueChange = { value ->
                    email = value
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
                        color = Gray,
                        fontFamily = MetroPoliceFontFamily,
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp,
                    )
                },
                onValueChange = { value ->
                    password = value
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
                    color = Black,
                    fontFamily = MetroPoliceFontFamily,
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp,
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
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_right),
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
            Button(
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Primary,
                ),
                shape = RoundedCornerShape(25.dp),
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

                        !nameHasError && !emailHasError && !passwordHasError -> {

                            navController.navigate(Screen.ProductScreen.route)

                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    text = "SIGN UP",
                    color = White,
                    fontFamily = MetroPoliceFontFamily,
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp
                )
            }

            Spacer(modifier = Modifier.height(125.dp))

            Text(
                text = "Or sign up with social account",
                color = Black,
                fontFamily = MetroPoliceFontFamily,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SignUpPreview() {
    SignUpScreen(rememberNavController())
}
