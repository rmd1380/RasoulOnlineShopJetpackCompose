package com.technolearn.rasoulonlineshop.helper

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.progressSemantics
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.technolearn.rasoulonlineshop.R
import com.technolearn.rasoulonlineshop.ui.theme.Black
import com.technolearn.rasoulonlineshop.ui.theme.FontMedium14
import com.technolearn.rasoulonlineshop.ui.theme.FontRegular11
import com.technolearn.rasoulonlineshop.ui.theme.FontRegular14
import com.technolearn.rasoulonlineshop.ui.theme.FontSemiBold16
import com.technolearn.rasoulonlineshop.ui.theme.Gray
import com.technolearn.rasoulonlineshop.ui.theme.Primary
import com.technolearn.rasoulonlineshop.ui.theme.White
import com.technolearn.rasoulonlineshop.vo.enums.ButtonSize
import com.technolearn.rasoulonlineshop.vo.enums.ButtonStyle
import kotlin.math.ceil
import kotlin.math.floor

@Composable
fun CustomRatingBar(
    rating: Double = 0.0,
    stars: Int = 5,
) {
    val filledStars = floor(rating).toInt()
    val unfilledStars = (stars - ceil(rating)).toInt()
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.Bottom) {
        repeat(filledStars) {
            Image(
                painter = painterResource(R.drawable.ic_start_active),
                contentDescription = null,
                modifier = Modifier.size(16.dp)
            )
        }
        repeat(unfilledStars) {
            Image(
                painter = painterResource(R.drawable.ic_start_inactive),
                contentDescription = null,
                modifier = Modifier.size(16.dp)
            )
        }
        Text(
            modifier = Modifier.padding(start = 2.dp),
            text = "(${rating})",
            style = FontRegular11(Gray),
        )
    }
}

@Composable
fun AddToFavorite(
    modifier: Modifier,
    isAddToFavorite: Boolean
) {
    var isLiked by remember { mutableStateOf(isAddToFavorite) }
    Card(
        modifier = modifier,
        elevation = 4.dp,
        shape = CircleShape
    ) {
        IconButton(
            modifier = Modifier
                .fillMaxSize(),
            onClick = {
                isLiked = !isLiked
            }
        ) {
            Icon(
                painter = painterResource(id = if (isLiked) R.drawable.ic_heart_filled else R.drawable.ic_heart_not_filled),
                contentDescription = if (isLiked) "Liked" else "Not Liked",
                tint = if (isLiked) Primary else Gray
            )
        }
    }
}

@Composable
fun DropDown(
    value: String,
    modifier: Modifier = Modifier,
    isFocused: Boolean,
    onClick: () -> Unit,
) {

    Box(
        modifier = modifier
            .border(
                width = 1.dp,
                if (isFocused) Primary else Gray,
                shape = RoundedCornerShape(8.dp)
            )
            .background(White)
            .fillMaxWidth()
            .clickable(
                interactionSource = MutableInteractionSource(),
                indication = null
            ) {
                onClick()
            }
            .padding(horizontal = 16.dp, vertical = 12.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxSize()
        ) {
            Text(
                text = value,
                style = FontMedium14(Black),
                modifier = Modifier
                    .wrapContentSize()
                    .padding()
                    .clickable(
                        interactionSource = MutableInteractionSource(),
                        indication = null
                    ) {
                        onClick()
                    },
                textAlign = TextAlign.Start
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_down),
                contentDescription = "ic_down",
                tint = Black,
                modifier = Modifier
                    .size(12.dp)
                    .clickable(
                        interactionSource = MutableInteractionSource(),
                        indication = null
                    ) {
                        onClick()
                    }
            )
        }
    }
}

@Composable
fun Tag(
    defaultValue: String,
    roundCorner: Dp = 12.dp,
    tagColor: String = "",
    onClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .border(
                width = 1.dp, when (tagColor) {
                    "Black" -> {
                        Black
                    }

                    "Yellow" -> {
                        Color.Yellow
                    }

                    "Red" -> {
                        Color.Red
                    }

                    "Blue" -> {
                        Color.Blue
                    }

                    "Green" -> {
                        Color.Green
                    }

                    else -> {
                        Gray
                    }
                }, shape = RoundedCornerShape(roundCorner)
            )
            .clip(RoundedCornerShape(roundCorner))
            .background(
                when (tagColor) {
                    "Black" -> {
                        Black
                    }

                    "Yellow" -> {
                        Color.Yellow
                    }

                    "Red" -> {
                        Color.Red
                    }

                    "Blue" -> {
                        Color.Blue
                    }

                    "Green" -> {
                        Color.Green
                    }

                    else -> {
                        White
                    }
                }
            )
            .clickable {
                onClick.invoke()
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = defaultValue,
            style = FontMedium14(
                when (tagColor) {
                    "Black" -> {
                        White
                    }

                    "Yellow" -> {
                        White
                    }

                    "Red" -> {
                        White
                    }

                    "Blue" -> {
                        White
                    }

                    "Green" -> {
                        White
                    }

                    else -> {
                        Black
                    }
                }
            ),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(horizontal = 20.dp, vertical = 10.dp)
                .clickable(
                    interactionSource = MutableInteractionSource(),
                    indication = null
                ) {
                    onClick.invoke()
                }
        )
    }
}


@Composable
fun CustomButton(
    iconStart: Painter? = null,
    iconEnd: Painter? = null,
    size: ButtonSize = ButtonSize.BIG,
    style: ButtonStyle = ButtonStyle.CONTAINED,
    enable: Boolean = true,
    loading: Boolean = false,
    onClick: () -> Unit = {},
    modifier: Modifier,
    text: String,
    roundCorner: Dp = 25.dp,
    paddingLeft: Dp = 0.dp,
    paddingRight: Dp = 0.dp,
    paddingTop: Dp = 0.dp,
    paddingBottom: Dp = 0.dp,
    buttonTextStyle: TextStyle = FontRegular14(White)
) {
    var top = paddingTop
    var bottom = paddingBottom
    var left = paddingLeft
    var right = paddingRight
    var textColor = Color.White
    var background = Primary
    var borderColor = Black
    val btnTextStyle: TextStyle = buttonTextStyle

    when (size) {
        ButtonSize.BIG -> {
            top = 18.dp
            bottom = 18.dp
            left = 140.dp
            right = 140.dp
        }

        ButtonSize.SMALL -> {
            top = 12.dp
            bottom = 12.dp
            left = 60.dp
            right = 60.dp
        }

    }
    when (style) {
        ButtonStyle.CONTAINED -> {
            background = if (enable) Primary else Primary.copy(alpha = 0.9f)
            borderColor = Color.Transparent
            textColor = if (enable) White else White.copy(alpha = 0.9f)
        }

        ButtonStyle.OUTLINED -> {
            background = Color.Transparent
            borderColor = if (enable) Black else Gray
            textColor = if (enable) Black else Gray
        }
    }
    Box(
        modifier = modifier
            .wrapContentSize()
            .clip(RoundedCornerShape(roundCorner))
            .border(width = 2.dp, color = borderColor, shape = RoundedCornerShape(roundCorner))
            .background(background)
            .clickable(enabled = enable && !loading) {
                onClick()
            },
        contentAlignment = Alignment.CenterEnd
    )
    {
        Row(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(top = top, bottom = bottom, start = left, end = right),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if (!loading) {
                if (iconStart != null) {
                    Icon(
                        painter = iconStart,
                        contentDescription = "icon_start",
                        tint = textColor,
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .size(12.dp)
                    )
                    Spacer(modifier = Modifier.padding(start = 8.dp))
                }
                Text(
                    modifier = Modifier.align(Alignment.CenterVertically),
                    text = text,
                    color = textColor,
                    style = btnTextStyle
                )
                Spacer(modifier = Modifier.padding(start = 8.dp))
                if (iconEnd != null) {
                    Icon(
                        painter = iconEnd,
                        contentDescription = "icon_End",
                        tint = textColor,
                        modifier = Modifier.size(12.dp)
                    )
                    Spacer(modifier = Modifier.padding(start = 8.dp))
                }
            } else {
                CircularProgressIndicator(
                    modifier = Modifier
                        .progressSemantics()
                        .size(24.dp),
                    color = if (style == ButtonStyle.CONTAINED) White else Black,
                    strokeWidth = 2.dp
                )
            }
        }
    }
}

@Composable
fun Label(
    text: String,
    roundCorner: Dp = 30.dp,
    modifier: Modifier
) {
    Button(
        colors = ButtonDefaults.buttonColors(
            backgroundColor = if (text == "NEW") Black else Primary,
        ),
        shape = RoundedCornerShape(roundCorner),
        onClick = {},
        modifier = modifier
            .clickable(
                interactionSource = MutableInteractionSource(),
                indication = null,
                enabled = false
            ) {}
    ) {
        Text(
            modifier = Modifier.clickable(
                interactionSource = MutableInteractionSource(),
                indication = null,
                enabled = false
            ) { },
            text = text,
            style = FontSemiBold16(White)
        )
    }

}

