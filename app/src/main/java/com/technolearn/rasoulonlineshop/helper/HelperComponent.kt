package com.technolearn.rasoulonlineshop.helper

import androidx.annotation.RawRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.progressSemantics
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.technolearn.rasoulonlineshop.R
import com.technolearn.rasoulonlineshop.ui.theme.Black
import com.technolearn.rasoulonlineshop.ui.theme.FontMedium14
import com.technolearn.rasoulonlineshop.ui.theme.FontRegular11
import com.technolearn.rasoulonlineshop.ui.theme.FontRegular14
import com.technolearn.rasoulonlineshop.ui.theme.FontSemiBold16
import com.technolearn.rasoulonlineshop.ui.theme.Gray
import com.technolearn.rasoulonlineshop.ui.theme.Primary
import com.technolearn.rasoulonlineshop.ui.theme.White
import com.technolearn.rasoulonlineshop.util.Extensions.orDefault
import com.technolearn.rasoulonlineshop.vo.enums.ButtonSize
import com.technolearn.rasoulonlineshop.vo.enums.ButtonStyle
import com.technolearn.rasoulonlineshop.vo.model.helperComponent.CustomAction
import com.technolearn.rasoulonlineshop.vo.res.ProductRes

@Composable
fun CustomRatingBar(
    rating: Double = 0.0,
    stars: Int = 5,
) {
    val filledStars = rating.toInt()
    val remainder = rating - filledStars
    var hasHalfStar = false
    val unfilledStars = stars - filledStars

    if (remainder > 0) {
        hasHalfStar = true
    }

    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.Bottom) {
        repeat(filledStars) {
            Image(
                painter = painterResource(R.drawable.ic_start_active),
                contentDescription = null,
                modifier = Modifier.size(16.dp)
            )
        }

        if (hasHalfStar) {
            Image(
                painter = painterResource(R.drawable.ic_start_half),
                contentDescription = null,
                modifier = Modifier.size(16.dp)
            )
        }

        repeat(unfilledStars - if (hasHalfStar) 1 else 0) {
            Image(
                painter = painterResource(R.drawable.ic_start_inactive),
                contentDescription = null,
                modifier = Modifier.size(16.dp)
            )
        }
        Text(
            modifier = Modifier.padding(start = 2.dp),
            text = "($rating)",
            style = FontRegular11(Gray),
        )
    }
}


@Composable
fun AddToFavorite(
    modifier: Modifier,
    productRes: ProductRes?,
    onToggleFavorite: (Int) -> Unit,
    isProductLikedState: Boolean
) {
    Card(
        modifier = modifier,
        elevation = 4.dp,
        shape = CircleShape
    ) {
        IconButton(
            modifier = Modifier
                .fillMaxSize(),
            onClick = {
                onToggleFavorite(productRes?.id.orDefault())
            }
        ) {
            Icon(
                painter = painterResource(id = if (isProductLikedState) R.drawable.ic_heart_filled else R.drawable.ic_heart_not_filled),
                contentDescription = if (isProductLikedState) "Liked" else "Not Liked",
                tint = if (isProductLikedState) Primary else Gray
            )
        }
    }
}

@Composable
fun CircleIconCard(
    modifier: Modifier,
    icon: Painter,
    backgroundColor: Color = White,
    iconTint:Color= Gray,
    iconSize:Dp=16.dp,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier,
        elevation = 4.dp,
        shape = CircleShape,
        backgroundColor = backgroundColor
    ) {
        IconButton(
            modifier = Modifier
                .fillMaxSize(),
            onClick = {
                onClick()
            }
        ) {
            Icon(
                modifier = Modifier.size(iconSize),
                painter = icon,
                contentDescription = null,
                tint = iconTint,
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
                painter = painterResource(id = R.drawable.ic_chevron_down),
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
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
            .clip(RoundedCornerShape(roundCorner))
            .clickable {
                onClick.invoke()
            },
        backgroundColor = White,
        border = BorderStroke(
            1.dp, when (tagColor) {
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
            }
        ),
        shape = RoundedCornerShape(roundCorner),
        elevation = 0.dp
    ) {
        Text(
            text = defaultValue,
            style = FontMedium14(Black),
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopAppBar(
    title: String,
    style: TextStyle?,
    modifier: Modifier = Modifier,
    navigationIcon: ImageVector? = null,
    actionIcons: List<CustomAction>? = null,
    navigationOnClick: () -> Unit,
    actionOnclick: (CustomAction) -> Unit,
) {
    TopAppBar(
        title = {
            if (style != null) {
                Text(
                    text = title,
                    style = style,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        navigationIcon = {
            IconButton(onClick = navigationOnClick) {
                navigationIcon?.let {
                    Icon(
                        imageVector = it,
                        contentDescription = "navigation",
                    )
                }
            }
        },
        actions = {
            if (!actionIcons.isNullOrEmpty()) {
                actionIcons.forEach { action ->
                    IconButton(onClick = { actionOnclick(action) }) {
                        Icon(
                            imageVector = action.icon,
                            contentDescription = action.name,
                        )
                    }
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = White
        ),
        scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(),
    )
}

@Composable
fun LoadingInColumn(modifier: Modifier, count: Int = 1) {

    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .progressSemantics()
                .size(24.dp),
            color = Black,
            strokeWidth = 2.dp
        )
    }
}

@Composable
fun QuantityControl(
    quantity: Int,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit,
    onAddToCart: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        CustomButton(
            modifier = Modifier,
            size = ButtonSize.SMALL,
            text = stringResource(R.string.add_to_cart),
            onClick = onAddToCart,
            iconStart = painterResource(R.drawable.ic_tick),
            buttonTextStyle = FontRegular11(White)
        )
        Row(
            modifier = Modifier.wrapContentWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CircleIconCard(
                modifier = Modifier
                    .size(36.dp),
                icon = painterResource(id = R.drawable.ic_subtract)
            ) {
                onDecrease()
            }
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = quantity.toString(),
                style = FontMedium14(Black),
            )
            Spacer(modifier = Modifier.width(16.dp))
            CircleIconCard(
                modifier = Modifier
                    .size(36.dp),
                icon = painterResource(id = R.drawable.ic_add)
            ) {
                onIncrease()
            }
        }

    }
}

@Composable
fun LottieComponent(@RawRes resId: Int) {
    val isPlaying by remember { mutableStateOf(true) }
    val speed by remember { mutableFloatStateOf(1f) }

    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(resId))
    val progress by animateLottieCompositionAsState(
        composition,
        iterations = LottieConstants.IterateForever,
        isPlaying = isPlaying,
        speed = speed,
        restartOnPlay = false
    )

    Column(
        Modifier
            .background(White)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LottieAnimation(
            composition = composition,
            progress = { progress },
            modifier = Modifier.size(300.dp)
        )
    }
}
