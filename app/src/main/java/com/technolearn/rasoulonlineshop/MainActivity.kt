package com.technolearn.rasoulonlineshop

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.technolearn.rasoulonlineshop.helper.CostumeTextField
import com.technolearn.rasoulonlineshop.ui.theme.*
import com.technolearn.rasoulonlineshop.vm.ShopViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val shopViewModel: ShopViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RasoulOnlineShopTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MainView()
                }
            }
        }
    }
}

@Composable
fun MainView() {
    val contextForToast = LocalContext.current.applicationContext
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
                        Toast.makeText(contextForToast, "Click Arrow Back", Toast.LENGTH_SHORT)
                            .show()
                    }) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.ic_back),
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
                ///////SignUpText
                Text(
                    text = "SignUp",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 14.dp),
                    fontFamily = MetroPoliceFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 34.sp
                )
                ///////TextField
                CostumeTextField(labelText = "Name", 16, 16, 72, 0)
                CostumeTextField(labelText = "Email", 16, 16, 8, 0)
                CostumeTextField(labelText = "Password", 16, 16, 8, 0)
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    RasoulOnlineShopTheme {
        MainView()
    }
}