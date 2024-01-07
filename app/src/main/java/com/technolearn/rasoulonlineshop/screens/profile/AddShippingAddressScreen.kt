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
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.technolearn.rasoulonlineshop.R
import com.technolearn.rasoulonlineshop.helper.CustomButton
import com.technolearn.rasoulonlineshop.helper.CustomTopAppBar
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
import com.technolearn.rasoulonlineshop.vm.ShopViewModel
import com.technolearn.rasoulonlineshop.vo.entity.UserAddressEntity

@Composable
fun AddShippingAddressScreen(navController: NavController, viewModel: ShopViewModel) {
    val context = LocalContext.current
    val userLoggedInInfo by remember { viewModel.getLoggedInUser() }.observeAsState()
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
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                var firstName by remember { mutableStateOf("") }
                var firstNameHasError by remember { mutableStateOf(false) }
                var firstNameLabel by remember { mutableStateOf("First name") }

                var lastName by remember { mutableStateOf("") }
                var lastNameHasError by remember { mutableStateOf(false) }
                var lastNameLabel by remember { mutableStateOf("Last name") }

                var phone by remember { mutableStateOf("") }
                var phoneHasError by remember { mutableStateOf(false) }
                var phoneLabel by remember { mutableStateOf("Phone") }

                var address by remember { mutableStateOf("") }
                var addressHasError by remember { mutableStateOf(false) }
                var addressLabel by remember { mutableStateOf("Address") }

                var city by remember { mutableStateOf("") }
                var cityHasError by remember { mutableStateOf(false) }
                var cityLabel by remember { mutableStateOf("City") }

                var province by remember { mutableStateOf("") }
                var provinceHasError by remember { mutableStateOf(false) }
                var provinceLabel by remember { mutableStateOf("State/Province/Region") }

                var postalCode by remember { mutableStateOf("") }
                var postalCodeHasError by remember { mutableStateOf(false) }
                var postalCodeLabel by remember { mutableStateOf("Zip Code (Postal Code)") }

                var country by remember { mutableStateOf("") }
                var countryHasError by remember { mutableStateOf(false) }
                var countryLabel by remember { mutableStateOf("Country") }

                var addressName by remember { mutableStateOf("") }
                var addressNameHasError by remember { mutableStateOf(false) }
                var addressNameLabel by remember { mutableStateOf("AddressName") }
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentPadding = PaddingValues(horizontal = 16.dp,vertical = 24.dp)
                ) {
                    item {
                        //region FirstName
                        TextField(
                            value = firstName,
                            label = {
                                Text(
                                    text = firstNameLabel,
                                    style = FontMedium14(Gray),
                                )
                            },
                            onValueChange = { value ->
                                firstName = value
                                firstNameHasError = firstName.isEmpty()
                                firstNameLabel =
                                    if (firstName.isNotEmpty()) "First name" else "First Name Can Not Be Empty"
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
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                            singleLine = true
                        )
                        //endregion
                    }
                    item {
                        //region LastName
                        TextField(
                            value = lastName,
                            label = {
                                Text(
                                    text = lastNameLabel,
                                    style = FontMedium14(Gray),
                                )
                            },
                            onValueChange = { value ->
                                lastName = value
                                lastNameHasError = lastName.isEmpty()
                                lastNameLabel =
                                    if (firstName.isNotEmpty()) "Last Name" else "Last Name Can Not Be Empty"
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
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                            singleLine = true
                        )
                        //endregion
                    }
                    item {
                        //region Phone
                        TextField(
                            value = phone,
                            label = {
                                Text(
                                    text = phoneLabel,
                                    style = FontMedium14(Gray),
                                )
                            },
                            onValueChange = { value ->
                                phone = value
                                phoneHasError = phone.isEmpty()
                                phoneLabel =
                                    if (phone.isNotEmpty()) "Phone" else "Phone Can Not Be Empty"
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
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                            singleLine = true
                        )
                        //endregion
                    }
                    item {
                        //region Address
                        TextField(
                            value = address,
                            label = {
                                Text(
                                    text = addressLabel,
                                    style = FontMedium14(Gray),
                                )
                            },
                            onValueChange = { value ->
                                address = value
                                addressHasError = address.isEmpty()
                                addressLabel =
                                    if (address.isNotEmpty()) "Address" else "Address Can Not Be Empty"
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
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                            singleLine = true
                        )
                        //endregion
                    }
                    item {
                        //region City
                        TextField(
                            value = city,
                            label = {
                                Text(
                                    text = cityLabel,
                                    style = FontMedium14(Gray),
                                )
                            },
                            onValueChange = { value ->
                                city = value
                                cityHasError = city.isEmpty()
                                cityLabel =
                                    if (city.isNotEmpty()) "City" else "City Can Not Be Empty"
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
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                            singleLine = true
                        )
                        //endregion
                    }
                    item {
                        //region Province
                        TextField(
                            value = province,
                            label = {
                                Text(
                                    text = provinceLabel,
                                    style = FontMedium14(Gray),
                                )
                            },
                            onValueChange = { value ->
                                province = value
                                provinceHasError = province.isEmpty()
                                provinceLabel =
                                    if (province.isNotEmpty()) "State/Province/Region" else "State/Province/Region Can Not Be Empty"
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
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                            singleLine = true
                        )
                        //endregion
                    }
                    item {
                        //region PostalCode
                        TextField(
                            value = postalCode,
                            label = {
                                Text(
                                    text = postalCodeLabel,
                                    style = FontMedium14(Gray),
                                )
                            },
                            onValueChange = { value ->
                                postalCode = value
                                postalCodeHasError = postalCode.isEmpty()
                                postalCodeLabel =
                                    if (postalCode.isNotEmpty()) "Zip Code (Postal Code)" else "Zip Code (Postal Code) Can Not Be Empty"
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
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                            singleLine = true
                        )
                        //endregion
                    }
                    item {
                        //region Country
                        TextField(
                            value = country,
                            label = {
                                Text(
                                    text = countryLabel,
                                    style = FontMedium14(Gray),
                                )
                            },
                            onValueChange = { value ->
                                country = value
                                countryHasError = country.isEmpty()
                                countryLabel =
                                    if (country.isNotEmpty()) "Country" else "Country Can Not Be Empty"
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
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                            singleLine = true
                        )
                        //endregion
                    }
                    item {
                        //region AddressName
                        TextField(
                            value = addressName,
                            label = {
                                Text(
                                    text = addressNameLabel,
                                    style = FontMedium14(Gray),
                                )
                            },
                            onValueChange = { value ->
                                addressName = value
                                addressNameHasError = addressName.isEmpty()
                                addressNameLabel =
                                    if (addressName.isNotEmpty()) "AddressName" else "AddressName Can Not Be Empty"
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
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                            singleLine = true
                        )
                        //endregion
                    }
                    item {
                        CustomButton(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 48.dp),
                            text = stringResource(R.string.save_address),
                            onClick = {
                                when {
                                    firstName.isEmpty() -> {
                                        firstNameHasError = true
                                        firstNameLabel = "First Name Can Not Be Empty"
                                    }

                                    lastName.isEmpty() -> {
                                        lastNameHasError = true
                                        lastNameLabel = "Last Name Can Not Be Empty"
                                    }

                                    address.isEmpty() -> {
                                        addressHasError = true
                                        addressLabel = "Address Can Not Be Empty"
                                    }

                                    city.isEmpty() -> {
                                        cityHasError = true
                                        cityLabel = "City Can Not Be Empty"
                                    }

                                    province.isEmpty() -> {
                                        provinceHasError = true
                                        provinceLabel = "Province Can Not Be Empty"
                                    }

                                    postalCode.isEmpty() -> {
                                        postalCodeHasError = true
                                        postalCodeLabel = "PostalCode Can Not Be Empty"
                                    }

                                    country.isEmpty() -> {
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
                                                userId=userLoggedInInfo?.id.orDefault(),
                                                firstName = firstName,
                                                lastName = lastName,
                                                phone = phone,
                                                addressName = addressName,
                                                address = address,
                                                city = city,
                                                province = province,
                                                postalCode = postalCode,
                                                country = country,
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
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddShippingAddressScreen() {
    AddShippingAddressScreen(rememberNavController(), viewModel())
}