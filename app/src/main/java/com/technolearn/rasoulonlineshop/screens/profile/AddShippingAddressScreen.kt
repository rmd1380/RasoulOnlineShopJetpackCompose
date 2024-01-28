package com.technolearn.rasoulonlineshop.screens.profile

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.technolearn.rasoulonlineshop.R
import com.technolearn.rasoulonlineshop.helper.CustomButton
import com.technolearn.rasoulonlineshop.helper.CustomTopAppBar
import com.technolearn.rasoulonlineshop.mapper.toUpdateUserReq
import com.technolearn.rasoulonlineshop.navigation.NavigationBarItemsGraph
import com.technolearn.rasoulonlineshop.navigation.Screen
import com.technolearn.rasoulonlineshop.ui.theme.Background
import com.technolearn.rasoulonlineshop.ui.theme.Black
import com.technolearn.rasoulonlineshop.ui.theme.Error
import com.technolearn.rasoulonlineshop.ui.theme.FontMedium14
import com.technolearn.rasoulonlineshop.ui.theme.FontSemiBold18
import com.technolearn.rasoulonlineshop.ui.theme.Gray
import com.technolearn.rasoulonlineshop.ui.theme.White
import com.technolearn.rasoulonlineshop.util.Extensions.orDefault
import com.technolearn.rasoulonlineshop.util.Extensions.orFalse
import com.technolearn.rasoulonlineshop.util.Extensions.orTrue
import com.technolearn.rasoulonlineshop.vm.ShopViewModel
import com.technolearn.rasoulonlineshop.vo.entity.UserAddressEntity
import timber.log.Timber

@Composable
fun AddShippingAddressScreen(
    navController: NavController,
    viewModel: ShopViewModel,
    isEdit: Boolean?,
    addressId: Int?
) {
    val context = LocalContext.current
    val userLoggedInInfo by remember { viewModel.getLoggedInUser() }.observeAsState()
    val userAddressById by remember { viewModel.getUserAddressById(addressId.orDefault()) }.observeAsState()
    var userAddressLoaded by remember { mutableStateOf(false) }
    LaunchedEffect(userAddressById) {
        if (userAddressById != null) {
            userAddressLoaded = true
        }
    }
    Timber.d("isEdit:::$isEdit::::addressIdADD::$addressId::::userAddressById:::$userAddressById")
    Scaffold(
        backgroundColor = Background,
        bottomBar = {

        },
        topBar = {
            CustomTopAppBar(
                title = stringResource(R.string.add_shipping_address),
                style = FontSemiBold18(Black),
                navigationIcon = ImageVector.vectorResource(R.drawable.ic_chevron_back),
                navigationOnClick = {
                    navController.navigate(Screen.ShippingAddressScreen.route) {
                        popUpTo(Screen.ShippingAddressScreen.route) {
                            inclusive = true
                        }
                    }
                },
                actionOnclick = {}
            )
        },
    ) {
        if (userAddressLoaded || !isEdit.orFalse()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    val maxCharFirstName = 30
                    var firstName by remember {
                        mutableStateOf(
                            if (isEdit.orFalse()) {
                                userAddressById?.firstName
                            } else {
                                ""
                            }
                        )
                    }
                    var firstNameHasError by remember { mutableStateOf(false) }
                    var firstNameLabel by remember { mutableStateOf("First name") }

                    val maxCharLastName = 30
                    var lastName by remember {
                        mutableStateOf(
                            if (isEdit.orFalse()) {
                                userAddressById?.lastName
                            } else {
                                ""
                            }
                        )
                    }
                    var lastNameHasError by remember { mutableStateOf(false) }
                    var lastNameLabel by remember { mutableStateOf("Last name") }

                    val maxCharPhone = 11
                    var phone by remember {
                        mutableStateOf(
                            if (isEdit.orFalse()) {
                                userAddressById?.phone
                            } else {
                                ""
                            }
                        )
                    }
                    var phoneHasError by remember { mutableStateOf(false) }
                    var phoneLabel by remember { mutableStateOf("Phone") }

                    val maxCharAddress = 80
                    var address by remember {
                        mutableStateOf(
                            if (isEdit.orFalse()) {
                                userAddressById?.address
                            } else {
                                ""
                            }
                        )
                    }
                    var addressHasError by remember { mutableStateOf(false) }
                    var addressLabel by remember { mutableStateOf("Address") }

                    val maxCharCity = 30
                    var city by remember {
                        mutableStateOf(
                            if (isEdit.orFalse()) {
                                userAddressById?.city
                            } else {
                                ""
                            }
                        )
                    }
                    var cityHasError by remember { mutableStateOf(false) }
                    var cityLabel by remember { mutableStateOf("City") }

                    val maxCharProvince = 30
                    var province by remember {
                        mutableStateOf(
                            if (isEdit.orFalse()) {
                                userAddressById?.province
                            } else {
                                ""
                            }
                        )
                    }
                    var provinceHasError by remember { mutableStateOf(false) }
                    var provinceLabel by remember { mutableStateOf("State/Province/Region") }

                    val maxCharPostalCode = 10
                    var postalCode by remember {
                        mutableStateOf(
                            if (isEdit.orFalse()) {
                                userAddressById?.postalCode
                            } else {
                                ""
                            }
                        )
                    }
                    var postalCodeHasError by remember { mutableStateOf(false) }
                    var postalCodeLabel by remember { mutableStateOf("Zip Code (Postal Code)") }

                    val maxCharCountry = 30
                    var country by remember {
                        mutableStateOf(
                            if (isEdit.orFalse()) {
                                userAddressById?.country
                            } else {
                                ""
                            }
                        )
                    }
                    var countryHasError by remember { mutableStateOf(false) }
                    var countryLabel by remember { mutableStateOf("Country") }

                    val maxCharAddressName = 30
                    var addressName by remember {
                        mutableStateOf(
                            if (isEdit.orFalse()) {
                                userAddressById?.addressName
                            } else {
                                ""
                            }
                        )
                    }
                    var addressNameHasError by remember { mutableStateOf(false) }
                    var addressNameLabel by remember { mutableStateOf("AddressName") }
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth(),
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 24.dp)
                    ) {
                        item {
                            //region FirstName
                            TextField(
                                value = firstName.orDefault(),
                                label = {
                                    Text(
                                        text = firstNameLabel,
                                        style = FontMedium14(Gray),
                                    )
                                },
                                onValueChange = { value ->
                                    if (value.length <= maxCharFirstName) firstName = value
                                    firstNameHasError = firstName?.isEmpty().orFalse()
                                    firstNameLabel =
                                        if (firstName?.isNotEmpty()
                                                .orFalse()
                                        ) "First name" else "First Name Can Not Be Empty"
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .shadow(4.dp)
                                    .border(
                                        width = 1.dp,
                                        color = if (firstNameHasError) Error else Color.Transparent,
                                        shape = RoundedCornerShape(4.dp)
                                    ),
                                colors = TextFieldDefaults.textFieldColors(
                                    backgroundColor = White,
                                    focusedIndicatorColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent,
                                    cursorColor = Black
                                ),
                                shape = RoundedCornerShape(4.dp),
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Text,
                                    imeAction = ImeAction.Next
                                ),
                                singleLine = true
                            )
                            //endregion
                        }
                        item {
                            //region LastName
                            TextField(
                                value = lastName.orDefault(),
                                label = {
                                    Text(
                                        text = lastNameLabel,
                                        style = FontMedium14(Gray),
                                    )
                                },
                                onValueChange = { value ->
                                    if (value.length <= maxCharLastName) lastName = value
                                    lastNameHasError = lastName?.isEmpty().orFalse()
                                    lastNameLabel =
                                        if (firstName?.isNotEmpty()
                                                .orFalse()
                                        ) "Last Name" else "Last Name Can Not Be Empty"
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 24.dp)
                                    .shadow(4.dp)
                                    .border(
                                        width = 1.dp,
                                        color = if (lastNameHasError) Error else Color.Transparent,
                                        shape = RoundedCornerShape(4.dp)
                                    ),
                                colors = TextFieldDefaults.textFieldColors(
                                    backgroundColor = White,
                                    focusedIndicatorColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent,
                                    cursorColor = Black
                                ),
                                shape = RoundedCornerShape(4.dp),
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Text,
                                    imeAction = ImeAction.Next
                                ),
                                singleLine = true
                            )
                            //endregion
                        }
                        item {
                            //region Phone
                            TextField(
                                value = phone.orDefault(),
                                label = {
                                    Text(
                                        text = phoneLabel,
                                        style = FontMedium14(Gray),
                                    )
                                },
                                onValueChange = { value ->
                                    if (value.length <= maxCharPhone) phone = value
                                    phoneHasError = phone?.isEmpty().orFalse()
                                    phoneLabel =
                                        if (phone?.isNotEmpty()
                                                .orFalse()
                                        ) "Phone" else "Phone Can Not Be Empty"
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 24.dp)
                                    .shadow(4.dp)
                                    .border(
                                        width = 1.dp,
                                        color = if (phoneHasError) Error else Color.Transparent,
                                        shape = RoundedCornerShape(4.dp)
                                    ),
                                colors = TextFieldDefaults.textFieldColors(
                                    backgroundColor = White,
                                    focusedIndicatorColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent,
                                    cursorColor = Black
                                ),
                                shape = RoundedCornerShape(4.dp),
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Phone,
                                    imeAction = ImeAction.Next
                                ),
                                singleLine = true
                            )
                            //endregion
                        }
                        item {
                            //region Address
                            TextField(
                                value = address.orDefault(),
                                label = {
                                    Text(
                                        text = addressLabel,
                                        style = FontMedium14(Gray),
                                    )
                                },
                                onValueChange = { value ->
                                    if (value.length <= maxCharAddress) address = value
                                    addressHasError = address?.isEmpty().orFalse()
                                    addressLabel =
                                        if (address?.isNotEmpty()
                                                .orFalse()
                                        ) "Address" else "Address Can Not Be Empty"
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 24.dp)
                                    .shadow(4.dp)
                                    .border(
                                        width = 1.dp,
                                        color = if (addressHasError) Error else Color.Transparent,
                                        shape = RoundedCornerShape(4.dp)
                                    ),
                                colors = TextFieldDefaults.textFieldColors(
                                    backgroundColor = White,
                                    focusedIndicatorColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent,
                                    cursorColor = Black
                                ),
                                shape = RoundedCornerShape(4.dp),
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Text,
                                    imeAction = ImeAction.Next
                                ),
                                singleLine = true
                            )
                            //endregion
                        }
                        item {
                            //region City
                            TextField(
                                value = city.orDefault(),
                                label = {
                                    Text(
                                        text = cityLabel,
                                        style = FontMedium14(Gray),
                                    )
                                },
                                onValueChange = { value ->
                                    if (value.length <= maxCharCity) city = value
                                    cityHasError = city?.isEmpty().orFalse()
                                    cityLabel =
                                        if (city?.isNotEmpty()
                                                .orFalse()
                                        ) "City" else "City Can Not Be Empty"
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 24.dp)
                                    .shadow(4.dp)
                                    .border(
                                        width = 1.dp,
                                        color = if (cityHasError) Error else Color.Transparent,
                                        shape = RoundedCornerShape(4.dp)
                                    ),
                                colors = TextFieldDefaults.textFieldColors(
                                    backgroundColor = White,
                                    focusedIndicatorColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent,
                                    cursorColor = Black
                                ),
                                shape = RoundedCornerShape(4.dp),
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Text,
                                    imeAction = ImeAction.Next
                                ),
                                singleLine = true
                            )
                            //endregion
                        }
                        item {
                            //region Province
                            TextField(
                                value = province.orDefault(),
                                label = {
                                    Text(
                                        text = provinceLabel,
                                        style = FontMedium14(Gray),
                                    )
                                },
                                onValueChange = { value ->
                                    if (value.length <= maxCharProvince) province = value
                                    provinceHasError = province?.isEmpty().orFalse()
                                    provinceLabel =
                                        if (province?.isNotEmpty()
                                                .orFalse()
                                        ) "State/Province/Region" else "State/Province/Region Can Not Be Empty"
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 24.dp)
                                    .shadow(4.dp)
                                    .border(
                                        width = 1.dp,
                                        color = if (provinceHasError) Error else Color.Transparent,
                                        shape = RoundedCornerShape(4.dp)
                                    ),
                                colors = TextFieldDefaults.textFieldColors(
                                    backgroundColor = White,
                                    focusedIndicatorColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent,
                                    cursorColor = Black
                                ),
                                shape = RoundedCornerShape(4.dp),
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Text,
                                    imeAction = ImeAction.Next
                                ),
                                singleLine = true
                            )
                            //endregion
                        }
                        item {
                            //region PostalCode
                            TextField(
                                value = postalCode.orDefault(),
                                label = {
                                    Text(
                                        text = postalCodeLabel,
                                        style = FontMedium14(Gray),
                                    )
                                },
                                onValueChange = { value ->
                                    if (value.length <= maxCharPostalCode) postalCode = value
                                    postalCodeHasError = postalCode?.isEmpty().orFalse()
                                    postalCodeLabel =
                                        if (postalCode?.isNotEmpty()
                                                .orFalse()
                                        ) "Zip Code (Postal Code)" else "Zip Code (Postal Code) Can Not Be Empty"
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 24.dp)
                                    .shadow(4.dp)
                                    .border(
                                        width = 1.dp,
                                        color = if (postalCodeHasError) Error else Color.Transparent,
                                        shape = RoundedCornerShape(4.dp)
                                    ),
                                colors = TextFieldDefaults.textFieldColors(
                                    backgroundColor = White,
                                    focusedIndicatorColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent,
                                    cursorColor = Black
                                ),
                                shape = RoundedCornerShape(4.dp),
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Number,
                                    imeAction = ImeAction.Next
                                ),
                                singleLine = true
                            )
                            //endregion
                        }
                        item {
                            //region Country
                            TextField(
                                value = country.orDefault(),
                                label = {
                                    Text(
                                        text = countryLabel,
                                        style = FontMedium14(Gray),
                                    )
                                },
                                onValueChange = { value ->
                                    if (value.length <= maxCharCountry) country = value
                                    countryHasError = country?.isEmpty().orFalse()
                                    countryLabel =
                                        if (country?.isNotEmpty()
                                                .orFalse()
                                        ) "Country" else "Country Can Not Be Empty"
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 24.dp)
                                    .shadow(4.dp)
                                    .border(
                                        width = 1.dp,
                                        color = if (countryHasError) Error else Color.Transparent,
                                        shape = RoundedCornerShape(4.dp)
                                    ),
                                colors = TextFieldDefaults.textFieldColors(
                                    backgroundColor = White,
                                    focusedIndicatorColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent,
                                    cursorColor = Black
                                ),
                                shape = RoundedCornerShape(4.dp),
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Text,
                                    imeAction = ImeAction.Next
                                ),
                                singleLine = true
                            )
                            //endregion
                        }
                        item {
                            //region AddressName
                            TextField(
                                value = addressName.orDefault(),
                                label = {
                                    Text(
                                        text = addressNameLabel,
                                        style = FontMedium14(Gray),
                                    )
                                },
                                onValueChange = { value ->
                                    if (value.length <= maxCharAddressName) addressName = value
                                    addressNameHasError = addressName?.isEmpty().orFalse()
                                    addressNameLabel =
                                        if (addressName?.isNotEmpty()
                                                .orFalse()
                                        ) "AddressName" else "AddressName Can Not Be Empty"
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 24.dp)
                                    .shadow(4.dp)
                                    .border(
                                        width = 1.dp,
                                        color = if (addressNameHasError) Error else Color.Transparent,
                                        shape = RoundedCornerShape(4.dp)
                                    ),
                                colors = TextFieldDefaults.textFieldColors(
                                    backgroundColor = White,
                                    focusedIndicatorColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent,
                                    cursorColor = Black
                                ),
                                shape = RoundedCornerShape(4.dp),
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Text,
                                    imeAction = ImeAction.Done
                                ),
                                singleLine = true
                            )
                            //endregion
                        }
                        item {
                            CustomButton(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 48.dp),
                                text = if (isEdit.orFalse()) {
                                    stringResource(R.string.update_address)
                                } else {
                                    stringResource(R.string.save_address)
                                },
                                onClick = {
                                    if (isEdit.orFalse()) {
                                        when {
                                            firstName?.isEmpty().orFalse() -> {
                                                firstNameHasError = true
                                                firstNameLabel = "First Name Can Not Be Empty"
                                            }

                                            lastName?.isEmpty().orFalse() -> {
                                                lastNameHasError = true
                                                lastNameLabel = "Last Name Can Not Be Empty"
                                            }

                                            address?.isEmpty().orFalse() -> {
                                                addressHasError = true
                                                addressLabel = "Address Can Not Be Empty"
                                            }

                                            city?.isEmpty().orFalse() -> {
                                                cityHasError = true
                                                cityLabel = "City Can Not Be Empty"
                                            }

                                            province?.isEmpty().orFalse() -> {
                                                provinceHasError = true
                                                provinceLabel = "Province Can Not Be Empty"
                                            }

                                            postalCode?.isEmpty().orFalse() -> {
                                                postalCodeHasError = true
                                                postalCodeLabel = "PostalCode Can Not Be Empty"
                                            }

                                            country?.isEmpty().orFalse() -> {
                                                countryHasError = true
                                                countryLabel = "Country Can Not Be Empty"
                                            }

                                            !firstNameHasError
                                                    && !lastNameHasError
                                                    && !addressHasError
                                                    && !cityHasError
                                                    && !provinceHasError
                                                    && !postalCodeHasError
                                                    && !countryHasError -> {
                                                if(userAddressById!=null){
                                                    val updatedUserAddress = userAddressById!!.copy(
                                                        firstName = firstName.orDefault(),
                                                        lastName = lastName.orDefault(),
                                                        phone = phone.orDefault(),
                                                        addressName = addressName.orDefault(),
                                                        address = address.orDefault(),
                                                        city = city.orDefault(),
                                                        province = province.orDefault(),
                                                        postalCode = postalCode.orDefault(),
                                                        country = country.orDefault()
                                                    )
                                                    viewModel.updateUserAddress(updatedUserAddress)
                                                }
                                                if (userAddressById?.isAddressSelected.orFalse()) {
                                                    viewModel.updateUser(
                                                        userLoggedInInfo?.token.orDefault(),
                                                        updateReq = toUpdateUserReq(
                                                            UserAddressEntity(
                                                                userId = userLoggedInInfo?.id.orDefault(),
                                                                firstName = firstName.orDefault(),
                                                                lastName = lastName.orDefault(),
                                                                phone = phone.orDefault(),
                                                                addressName = addressName.orDefault(),
                                                                address = address.orDefault(),
                                                                city = city.orDefault(),
                                                                province = province.orDefault(),
                                                                postalCode = postalCode.orDefault(),
                                                                country = country.orDefault(),
                                                            )
                                                        )
                                                    )
                                                }
                                                navController.navigate(Screen.ShippingAddressScreen.route) {
                                                    popUpTo(Screen.ShippingAddressScreen.route) {
                                                        inclusive = true
                                                    }
                                                }
                                            }
                                        }
                                    } else {
                                        when {
                                            firstName?.isEmpty().orFalse() -> {
                                                firstNameHasError = true
                                                firstNameLabel = "First Name Can Not Be Empty"
                                            }

                                            lastName?.isEmpty().orFalse() -> {
                                                lastNameHasError = true
                                                lastNameLabel = "Last Name Can Not Be Empty"
                                            }

                                            address?.isEmpty().orFalse() -> {
                                                addressHasError = true
                                                addressLabel = "Address Can Not Be Empty"
                                            }

                                            city?.isEmpty().orFalse() -> {
                                                cityHasError = true
                                                cityLabel = "City Can Not Be Empty"
                                            }

                                            province?.isEmpty().orFalse() -> {
                                                provinceHasError = true
                                                provinceLabel = "Province Can Not Be Empty"
                                            }

                                            postalCode?.isEmpty().orFalse() -> {
                                                postalCodeHasError = true
                                                postalCodeLabel = "PostalCode Can Not Be Empty"
                                            }

                                            country?.isEmpty().orFalse() -> {
                                                countryHasError = true
                                                countryLabel = "Country Can Not Be Empty"
                                            }

                                            !firstNameHasError
                                                    && !lastNameHasError
                                                    && !addressHasError
                                                    && !cityHasError
                                                    && !provinceHasError
                                                    && !postalCodeHasError
                                                    && !countryHasError -> {
                                                viewModel.insertUserAddress(
                                                    UserAddressEntity(
                                                        userId = userLoggedInInfo?.id.orDefault(),
                                                        firstName = firstName.orDefault(),
                                                        lastName = lastName.orDefault(),
                                                        phone = phone.orDefault(),
                                                        addressName = addressName.orDefault(),
                                                        address = address.orDefault(),
                                                        city = city.orDefault(),
                                                        province = province.orDefault(),
                                                        postalCode = postalCode.orDefault(),
                                                        country = country.orDefault(),
                                                    )
                                                )
                                                navController.navigate(Screen.ShippingAddressScreen.route) {
                                                    popUpTo(Screen.ShippingAddressScreen.route) {
                                                        inclusive = true
                                                    }
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
        } else {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun AddShippingAddressScreen() {
    AddShippingAddressScreen(rememberNavController(), viewModel(), false, 1)
}