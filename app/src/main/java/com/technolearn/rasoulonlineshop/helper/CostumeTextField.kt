package com.technolearn.rasoulonlineshop.helper

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.technolearn.rasoulonlineshop.ui.theme.Black
import com.technolearn.rasoulonlineshop.ui.theme.Gray
import com.technolearn.rasoulonlineshop.ui.theme.MetroPoliceFontFamily
import com.technolearn.rasoulonlineshop.ui.theme.White

@Composable
fun CostumeTextField(
    labelText: String,
    paddingEnd: Int,
    paddingStart: Int,
    paddingTop: Int,
    paddingBottom: Int
) {
    val inputValue = remember { mutableStateOf(TextFieldValue()) }
    TextField(
        value = inputValue.value,
        label = {
            Text(
                text = labelText,
                color = Gray,
                fontFamily = MetroPoliceFontFamily,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
                modifier = Modifier.padding(top = 8.dp)
            )
        },
        onValueChange = { ovc -> inputValue.value = ovc },

        modifier = Modifier
            .fillMaxWidth()
            .padding(
                end = paddingEnd.dp,
                start = paddingStart.dp,
                top = paddingTop.dp,
                bottom = paddingBottom.dp
            )
            .shadow(8.dp)
            .height(64.dp),
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = White,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            cursorColor = Black
        ),
        shape = RoundedCornerShape(4.dp)
    )
}
