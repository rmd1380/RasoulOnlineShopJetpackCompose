package com.technolearn.rasoulonlineshop.ui.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.technolearn.rasoulonlineshop.R

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

fun FontRegular11(color: Color = Gray) = TextStyle(
    fontFamily = MetroPoliceFontFamily,
    fontWeight = FontWeight.Normal,
    fontSize = 11.sp,
    color = color
)
fun FontRegular14(color: Color = Gray) = TextStyle(
    fontFamily = MetroPoliceFontFamily,
    fontWeight = FontWeight.Normal,
    fontSize = 14.sp,
    color = color
)
fun FontRegular16(color: Color = Gray) = TextStyle(
    fontFamily = MetroPoliceFontFamily,
    fontWeight = FontWeight.Normal,
    fontSize = 16.sp,
    color = color
)
fun FontMedium14(color: Color = Gray) = TextStyle(
    fontFamily = MetroPoliceFontFamily,
    fontWeight = FontWeight.Medium,
    fontSize = 14.sp,
    color = color
)
fun FontSemiBold11(color: Color = Gray) = TextStyle(
    fontFamily = MetroPoliceFontFamily,
    fontWeight = FontWeight.SemiBold,
    fontSize = 11.sp,
    color = color
)
fun FontSemiBold16(color: Color = Gray) = TextStyle(
    fontFamily = MetroPoliceFontFamily,
    fontWeight = FontWeight.SemiBold,
    fontSize = 16.sp,
    color = color
)

fun FontSemiBold18(color: Color = Gray) = TextStyle(
    fontFamily = MetroPoliceFontFamily,
    fontWeight = FontWeight.SemiBold,
    fontSize = 18.sp,
    color = color
)

fun FontSemiBold24(color: Color = Gray) = TextStyle(
    fontFamily = MetroPoliceFontFamily,
    fontWeight = FontWeight.SemiBold,
    fontSize = 24.sp,
    color = color
)
fun FontBold34(color: Color = Gray) = TextStyle(
    fontFamily = MetroPoliceFontFamily,
    fontWeight = FontWeight.Bold,
    fontSize = 34.sp,
    color = color
)