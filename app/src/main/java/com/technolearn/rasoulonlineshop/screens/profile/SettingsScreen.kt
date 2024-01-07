package com.technolearn.rasoulonlineshop.screens.profile

import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.technolearn.rasoulonlineshop.R
import com.technolearn.rasoulonlineshop.helper.CustomButton
import com.technolearn.rasoulonlineshop.helper.CustomTopAppBar
import com.technolearn.rasoulonlineshop.helper.DatePicker
import com.technolearn.rasoulonlineshop.navigation.NavigationBarItemsGraph
import com.technolearn.rasoulonlineshop.ui.theme.Black
import com.technolearn.rasoulonlineshop.ui.theme.Error
import com.technolearn.rasoulonlineshop.ui.theme.FontBold34
import com.technolearn.rasoulonlineshop.ui.theme.FontMedium14
import com.technolearn.rasoulonlineshop.ui.theme.FontSemiBold16
import com.technolearn.rasoulonlineshop.ui.theme.FontSemiBold18
import com.technolearn.rasoulonlineshop.ui.theme.Gray
import com.technolearn.rasoulonlineshop.ui.theme.White
import com.technolearn.rasoulonlineshop.util.Extensions.orDefault
import com.technolearn.rasoulonlineshop.util.Extensions.orFalse
import com.technolearn.rasoulonlineshop.vm.ShopViewModel
import com.technolearn.rasoulonlineshop.vo.entity.UserLoginEntity
import com.technolearn.rasoulonlineshop.vo.enums.Status
import com.technolearn.rasoulonlineshop.vo.req.UpdatePasswordReq
import timber.log.Timber

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(navController: NavController, viewModel: ShopViewModel) {
    val context = LocalContext.current

    val sheetState = rememberModalBottomSheetState()
    var showChangePasswordBottomSheet by rememberSaveable { mutableStateOf(false) }
    val userLoggedInInfo by remember { viewModel.getLoggedInUser() }.observeAsState()
    val updateUserPasswordStatus by remember { viewModel.updateUserPasswordStatus }.observeAsState()

    var userName by remember { mutableStateOf(userLoggedInInfo?.username.orEmpty()) }
    var userNameHasError by remember { mutableStateOf(false) }
    var userNameLabel by remember { mutableStateOf("User Name") }

    var dateOfBirth by remember { mutableStateOf(userLoggedInInfo?.dateOfBirth.orEmpty()) }
    var dateOfBirthHasError by remember { mutableStateOf(false) }
    var dateOfBirthLabel by remember { mutableStateOf("Date Of Birth") }

    var oldPassword by remember { mutableStateOf("") }
    var oldPasswordHasError by remember { mutableStateOf(false) }
    var oldPasswordLabel by remember { mutableStateOf("Old Password") }

    var newPassword by remember { mutableStateOf("") }
    var newPasswordHasError by remember { mutableStateOf(false) }
    var newPasswordLabel by remember { mutableStateOf("New Password") }

    var repeatNewPassword by remember { mutableStateOf("") }
    var repeatNewPasswordHasError by remember { mutableStateOf(false) }
    var repeatNewPasswordLabel by remember { mutableStateOf("Repeat New Password") }


    Timber.d("userLoggedInInfo123324:::$userLoggedInInfo")
    LaunchedEffect(updateUserPasswordStatus) {
        when (updateUserPasswordStatus?.status) {
            Status.LOADING -> {
                Timber.d("UpdatePassword:::LOADING:::${updateUserPasswordStatus?.data?.status}")
            }

            Status.SUCCESS -> {
                Timber.d("UpdatePassword:::SUCCESS:::${updateUserPasswordStatus?.data}")
                when (updateUserPasswordStatus?.data?.status) {
                    in 100..199 -> {
                        Toast.makeText(
                            context,
                            updateUserPasswordStatus?.data?.message,
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }

                    in 200..299 -> {
                        Toast.makeText(context, "Password Changed successfully", Toast.LENGTH_SHORT)
                            .show()
                        showChangePasswordBottomSheet = false
                        oldPassword = ""
                        newPassword = ""
                        repeatNewPassword = ""
                        viewModel.resetUpdateUserPasswordStatus()
                    }

                    in 300..399 -> {
                        Toast.makeText(
                            context,
                            updateUserPasswordStatus?.data?.message,
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }

                    in 400..499 -> {
                        Toast.makeText(
                            context,
                            updateUserPasswordStatus?.data?.message,
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }

                    in 500..599 -> {
                        Toast.makeText(
                            context,
                            updateUserPasswordStatus?.data?.message,
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }

                    else -> {
                    }
                }
            }

            Status.ERROR -> {
                Timber.d("UpdatePassword:::ERROR:::${updateUserPasswordStatus?.data?.status}")
            }

            else -> {}
        }

    }
    LaunchedEffect(userLoggedInInfo) {
        if (userLoggedInInfo == null) {
            viewModel.getLoggedInUser()
        } else {
            userName = userLoggedInInfo?.username.orEmpty()
            dateOfBirth = userLoggedInInfo?.dateOfBirth.orEmpty()
        }
    }
    Scaffold(
        backgroundColor = White,
        bottomBar = {
        },
        topBar = {
            CustomTopAppBar(
                title = "",
                style = null,
                navigationIcon = ImageVector.vectorResource(R.drawable.ic_chevron_back),
                actionIcons = listOf(),
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
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    text = "Settings",
                    style = FontBold34(Black),
                    modifier = Modifier
                        .fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = stringResource(R.string.personal_information),
                    style = FontSemiBold16(Black),
                    modifier = Modifier
                        .fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(20.dp))

                TextField(
                    value = userName,
                    label = {
                        Text(
                            text = userNameLabel,
                            style = FontMedium14(Gray),
                        )
                    },
                    onValueChange = { value ->
                        userName = value
                        userNameHasError = userName.isEmpty()
                        userNameLabel =
                            if (userName.isNotEmpty()) "User Name" else "User Name Can Not Be Empty"
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(4.dp)
                        .border(
                            width = 1.dp,
                            color = if (userNameHasError) Error else Color.Transparent,
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
                Spacer(modifier = Modifier.height(24.dp))
                DatePicker(
                    value = dateOfBirth.orDefault(),
                    label = dateOfBirthLabel,
                    onValueChange = { value ->
                        dateOfBirth = value
                        dateOfBirthHasError = dateOfBirth.orDefault().isEmpty()
                        dateOfBirthLabel =
                            if (dateOfBirth.orDefault()
                                    .isNotEmpty()
                            ) "DateOf Birth" else "Date Of Birth Can Not Be Empty"
                    },
                )
                Spacer(modifier = Modifier.height(36.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(R.string.password),
                        style = FontSemiBold16(Black),
                        modifier = Modifier
                            .wrapContentWidth()
                    )

                    Text(
                        text = stringResource(R.string.change),
                        style = FontMedium14(Gray),
                        modifier = Modifier
                            .wrapContentWidth()
                            .clickable {
                                showChangePasswordBottomSheet = true
                            }
                    )
                }
                Spacer(modifier = Modifier.height(24.dp))
                TextField(
                    value = stringResource(R.string.star),
                    label = {
                        Text(
                            text = stringResource(R.string.password),
                            style = FontMedium14(Gray),
                        )
                    },
                    onValueChange = {},
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(4.dp),
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = White,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        cursorColor = Black
                    ),
                    shape = RoundedCornerShape(4.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    singleLine = true,
                    enabled = false
                )
                Spacer(modifier = Modifier.height(20.dp))
                CustomButton(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = stringResource(R.string.save_changes),
                    onClick = {
                        when{
                            userName.isEmpty() -> {
                                userNameHasError = true
                                userNameLabel = "User Name Can Not Be Empty"
                            }
                            !userNameHasError->{
                                viewModel.updateUserDB(
                                    user = UserLoginEntity(
                                        id = userLoggedInInfo?.id.orDefault(),
                                        username = userName.orDefault(),
                                        oldPassword = userLoggedInInfo?.oldPassword.orDefault(),
                                        password = userLoggedInInfo?.password.orDefault(),
                                        repeatPassword = userLoggedInInfo?.repeatPassword.orDefault(),
                                        email = userLoggedInInfo?.email.orDefault(),
                                        firstName = userLoggedInInfo?.firstName.orDefault(),
                                        lastName = userLoggedInInfo?.lastName.orDefault(),
                                        phone = userLoggedInInfo?.phone.orDefault(),
                                        addressName = userLoggedInInfo?.addressName.orDefault(),
                                        address = userLoggedInInfo?.address.orDefault(),
                                        city = userLoggedInInfo?.city.orDefault(),
                                        province = userLoggedInInfo?.province.orDefault(),
                                        postalCode = userLoggedInInfo?.postalCode.orDefault(),
                                        country = userLoggedInInfo?.country.orDefault(),
                                        customerId = userLoggedInInfo?.customerId.orDefault(),
                                        token = userLoggedInInfo?.token.orDefault(),
                                        isLogin = userLoggedInInfo?.isLogin.orFalse(),
                                        dateOfBirth = dateOfBirth.orDefault()
                                    )
                                )
                                Toast.makeText(context, "Changes Saved", Toast.LENGTH_SHORT)
                                    .show()
                                navController.navigate(NavigationBarItemsGraph.Profile.route) {
                                    popUpTo(NavigationBarItemsGraph.Profile.route) {
                                        inclusive = true
                                    }
                                }
                            }
                        }
                    }
                )
            }
            if (showChangePasswordBottomSheet) {
                ModalBottomSheet(
                    sheetState = sheetState,
                    onDismissRequest = {
                        showChangePasswordBottomSheet = false
                        oldPassword = ""
                        newPassword = ""
                        repeatNewPassword = ""
                        oldPasswordHasError=false
                        newPasswordHasError=false
                        repeatNewPasswordHasError=false
                    }
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                end = 16.dp,
                                start = 16.dp,
                                bottom = 16.dp
                            ),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = stringResource(R.string.password_change),
                            style = FontSemiBold18(Black),
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                        //region OldPassword
                        TextField(
                            value = oldPassword,
                            label = {
                                Text(
                                    text = oldPasswordLabel,
                                    style = FontMedium14(Gray),
                                )
                            },
                            onValueChange = { value ->
                                oldPassword = value
                                oldPasswordHasError = oldPassword.isEmpty()
                                oldPasswordLabel =
                                    if (oldPassword.isNotEmpty()) "Old Password" else "Old Password Can Not Be Empty"
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .shadow(4.dp)
                                .border(
                                    width = 1.dp,
                                    color = if (oldPasswordHasError) Error else Color.Transparent,
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
                        //endregion
                        Spacer(modifier = Modifier.height(20.dp))
                        //region New Password
                        TextField(
                            value = newPassword,
                            label = {
                                Text(
                                    text = newPasswordLabel,
                                    style = FontMedium14(Gray),
                                )
                            },
                            onValueChange = { value ->
                                newPassword = value
                                newPasswordHasError = newPassword.isEmpty()
                                newPasswordLabel =
                                    if (newPassword.isNotEmpty()) "New Password" else "New Password Can Not Be Empty"
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .shadow(4.dp)
                                .border(
                                    width = 1.dp,
                                    color = if (newPasswordHasError) Error else Color.Transparent,
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
                        //endregion
                        Spacer(modifier = Modifier.height(20.dp))
                        //region CardExpireDate
                        TextField(
                            value = repeatNewPassword,
                            label = {
                                Text(
                                    text = repeatNewPasswordLabel,
                                    style = FontMedium14(Gray),
                                )
                            },
                            onValueChange = { value ->
                                repeatNewPassword = value
                                repeatNewPasswordHasError =
                                    repeatNewPassword.isEmpty()
                                repeatNewPasswordLabel =
                                    if (repeatNewPassword.isNotEmpty()) "Repeat New Password" else "Repeat New Password Can Not Be Empty"
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .shadow(4.dp)
                                .border(
                                    width = 1.dp,
                                    color = if (repeatNewPasswordHasError) Error else Color.Transparent,
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
                        //endregion
                        Spacer(modifier = Modifier.height(20.dp))
                        CustomButton(
                            modifier = Modifier
                                .fillMaxWidth(),
                            text = "SAVE PASSWORD",
                            onClick = {
                                when {
                                    oldPassword.isEmpty() -> {
                                        oldPasswordHasError = true
                                        oldPasswordLabel =
                                            "Old Password Can Not Be Empty"
                                    }

                                    newPassword.isEmpty() -> {
                                        newPasswordHasError = true
                                        newPasswordLabel =
                                            "New Password Can Not Be Empty"
                                    }

                                    repeatNewPassword.isEmpty() -> {
                                        repeatNewPasswordHasError = true
                                        repeatNewPasswordLabel =
                                            "Repeat New Password Can Not Be Empty"
                                    }

                                    newPassword != repeatNewPassword -> {
                                        repeatNewPasswordHasError = true
                                        repeatNewPasswordLabel =
                                            "Password not matched to repeat password"
                                    }

                                    !oldPasswordHasError
                                            && !newPasswordHasError
                                            && !repeatNewPasswordHasError -> {
                                        viewModel.updateUserPassword(
                                            token = userLoggedInInfo?.token.orDefault(),
                                            updatePasswordReq = UpdatePasswordReq(
                                                id = userLoggedInInfo?.id.orDefault(),
                                                oldPassword = oldPassword,
                                                password = newPassword,
                                                repeatPassword = repeatNewPassword
                                            )
                                        )
                                    }
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}