package com.technolearn.rasoulonlineshop.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.technolearn.rasoulonlineshop.R

// Set of Material typography styles to start with
val Typography = Typography(
    body1 = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    )
    /* Other default text styles to override
    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
    */
)
val MetroPoliceFontFamily = FontFamily(
    Font(R.font.metropolis_black, FontWeight.Black),
    Font(R.font.metropolis_black_italic, FontWeight.Black, FontStyle.Italic),
    Font(R.font.metropolis_bold, FontWeight.Bold),
    Font(R.font.metropolis_bold_italic, FontWeight.Bold, FontStyle.Italic),
    Font(R.font.metropolis_extra_bold, FontWeight.ExtraBold),
    Font(R.font.metropolis_extra_bold_italic, FontWeight.ExtraBold, FontStyle.Italic),
    Font(R.font.metropolis_extra_light, FontWeight.ExtraLight),
    Font(R.font.metropolis_extra_light_italic, FontWeight.ExtraLight, FontStyle.Italic),
    Font(R.font.metropolis_light, FontWeight.Light),
    Font(R.font.metropolis_light_italic, FontWeight.Light, FontStyle.Italic),
    Font(R.font.metropolis_medium, FontWeight.Medium),
    Font(R.font.metropolis_medium_italic, FontWeight.Medium, FontStyle.Italic),
    Font(R.font.metropolis_regular, FontWeight.Normal),
    Font(R.font.metropolis_regular_italic, FontWeight.Normal, FontStyle.Italic),
    Font(R.font.metropolis_semi_bold, FontWeight.SemiBold),
    Font(R.font.metropolis_semi_bold_italic, FontWeight.SemiBold, FontStyle.Italic),
    Font(R.font.metropolis_thin, FontWeight.Thin),
    Font(R.font.metropolis_thin_italic, FontWeight.Thin, FontStyle.Italic),
)