package com.technolearn.rasoulonlineshop.helper

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Build
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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
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
import com.technolearn.rasoulonlineshop.ui.theme.FontSemiBold11
import com.technolearn.rasoulonlineshop.ui.theme.FontSemiBold16
import com.technolearn.rasoulonlineshop.ui.theme.FontSemiBold24
import com.technolearn.rasoulonlineshop.ui.theme.Gray
import com.technolearn.rasoulonlineshop.ui.theme.Primary
import com.technolearn.rasoulonlineshop.ui.theme.White
import com.technolearn.rasoulonlineshop.util.CardNumberParser
import com.technolearn.rasoulonlineshop.util.Extensions.orDefault
import com.technolearn.rasoulonlineshop.vo.entity.UserCreditCardEntity
import com.technolearn.rasoulonlineshop.vo.enums.ButtonSize
import com.technolearn.rasoulonlineshop.vo.enums.ButtonStyle
import com.technolearn.rasoulonlineshop.vo.enums.SearchWidgetState
import com.technolearn.rasoulonlineshop.vo.model.helperComponent.CreditCardModel
import com.technolearn.rasoulonlineshop.vo.model.helperComponent.CustomAction
import com.technolearn.rasoulonlineshop.vo.res.ProductRes
import java.time.LocalDate
import java.time.format.DateTimeFormatter

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
    iconTint: Color = Gray,
    iconSize: Dp = 16.dp,
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

@SuppressLint("UnrememberedMutableInteractionSource")
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

@SuppressLint("UnrememberedMutableInteractionSource")
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
    buttonTextStyle: TextStyle = FontRegular14(White),
    buttonColor: Color = Primary
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
            left = 120.dp
            right = 120.dp
        }

        ButtonSize.SMALL -> {
            top = 12.dp
            bottom = 12.dp
            left = 60.dp
            right = 60.dp
        }

        ButtonSize.X_SMALL -> {
            top = 8.dp
            bottom = 8.dp
            left = 32.dp
            right = 32.dp
        }

    }
    when (style) {
        ButtonStyle.CONTAINED -> {
            background = if (enable) buttonColor else buttonColor.copy(alpha = 0.6f)
            borderColor = Color.Transparent
            textColor = if (enable) White else White.copy(alpha = 0.8f)
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

@SuppressLint("UnrememberedMutableInteractionSource")
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
    searchWidgetState: SearchWidgetState=SearchWidgetState.CLOSED,
    searchTextState: String?=null,
    onTextChange: ((String) -> Unit)? =null,
    onCloseClicked: (() -> Unit)?=null,
    onSearchClicked: ((String) -> Unit)?=null,
    title: String,
    style: TextStyle?,
    modifier: Modifier = Modifier,
    navigationIcon: ImageVector? = null,
    actionIcons: List<CustomAction>? = null,
    navigationOnClick: () -> Unit,
    actionOnclick: (CustomAction) -> Unit,
    tint: Color = Black,
) {
    when (searchWidgetState) {
        SearchWidgetState.CLOSED -> {
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
                                    painter = action.icon,
                                    contentDescription = action.name,
                                    modifier = Modifier.size(24.dp),
                                    tint = tint
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

        SearchWidgetState.OPENED -> {
            onTextChange?.let {textChange->
                onCloseClicked?.let { closeClicked ->
                    onSearchClicked?.let { searchClicked ->
                        SearchAppBar(
                            text = searchTextState.orDefault(),
                            onTextChange = textChange,
                            onCloseClicked = closeClicked,
                            onSearchClicked = searchClicked
                        )
                    }
                }
            }
        }
    }
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


@Composable
private fun CreditCardContainer(
    backgroundColor: Color = Black,
    model: CreditCardModel,
    emptyChar: Char = 'x',
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(215.dp),
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.credit_card_round_corner)),
        backgroundColor = backgroundColor,
        elevation = 5.dp
    ) {
        val cardNumber = CardNumberParser(
            number = model.number,
            emptyChar = emptyChar
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(215.dp),
            contentAlignment = Alignment.CenterEnd
        ) {
            Column(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(top = 34.dp, start = 24.dp, end = 24.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.chip_credit_card),
                    contentDescription = null,
                    modifier = Modifier.size(36.dp)
                )
                Spacer(modifier = Modifier.height(18.dp))
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    style = FontSemiBold24(White),
                    text = "${cardNumber.first} ${cardNumber.second} ${cardNumber.third} ${cardNumber.fourth}"
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .background(Gray),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = stringResource(R.string.card_holder_name),
                            style = FontSemiBold11(Black)
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(text = model.holderName, style = FontSemiBold16(Black))

                    }
                    Column {
                        Text(
                            text = stringResource(R.string.expiry_date),
                            style = FontSemiBold11(Black)
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = model.formattedExpiration.ifEmpty { "00/00" },
                            style = FontSemiBold16(Black)
                        )
                    }
                    model.logoCardIssuer?.let { safeLogoIssuer ->
                        Image(
                            painter = painterResource(safeLogoIssuer),
                            contentDescription = null,
                            modifier = Modifier.size(36.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CreditCard(
    userCreditCardEntity: UserCreditCardEntity,
    emptyChar: Char = 'x',
    backgroundColor: Color = Black,
    selected: Boolean,
    onClick: () -> Unit,
    deleteCard: () -> Unit
) {
    val model = CreditCardModel(
        number = userCreditCardEntity.cardNumber,
        expiration = userCreditCardEntity.cardExpirationDate,
        holderName = userCreditCardEntity.cardName,
        cvv2 = userCreditCardEntity.cvv2
    )
    Column(Modifier.fillMaxWidth()) {
        CreditCardContainer(
            model = model,
            emptyChar = emptyChar,
            backgroundColor = backgroundColor
        )
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier
                    .wrapContentWidth()
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) {
                        onClick()
                    },
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Checkbox(
                    checked = selected,
                    onCheckedChange = {
                        onClick()
                    },
                    colors = CheckboxDefaults.colors(
                        uncheckedColor = Gray,
                        checkedColor = Black,
                        checkmarkColor = White
                    )
                )
                Text(
                    text = stringResource(R.string.use_as_default_payment_method),
                    style = FontRegular14(Black),
                )
            }

            Icon(
                painter = painterResource(id = R.drawable.ic_trash),
                contentDescription = "ic_trash",
                tint = Black,
                modifier = Modifier
                    .size(24.dp)
                    .clickable {
                        deleteCard()
                    }
            )
        }

    }

}

@Composable
fun DatePicker(
    label: String,
    value: String,
    onValueChange: (String) -> Unit = {},
    pattern: String = "yyyy-MM-dd",
) {
    val formatter = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        DateTimeFormatter.ofPattern(pattern)
    } else {
        TODO("VERSION.SDK_INT < O")
    }
    val date = if (value.isNotBlank()) LocalDate.parse(value, formatter) else LocalDate.now()
    val dialog = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        DatePickerDialog(
            LocalContext.current,
            { _, year, month, dayOfMonth ->
                onValueChange(LocalDate.of(year, month + 1, dayOfMonth).toString())
            },
            date.year,
            date.monthValue - 1,
            date.dayOfMonth,
        )
    } else {
        TODO("VERSION.SDK_INT < O")
    }
    TextField(
        value = value,
        label = {
            Text(
                text = label,
                style = FontMedium14(Gray),
            )
        },
        onValueChange = {
        },
        modifier = Modifier
            .fillMaxWidth()
            .shadow(4.dp)
            .border(
                width = 1.dp,
                color = Color.Transparent,
                shape = RoundedCornerShape(4.dp)
            )
            .clickable { dialog.show() },
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = White,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            cursorColor = Black
        ),
        shape = RoundedCornerShape(4.dp),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        singleLine = true,
        enabled = false,
    )
}

@Composable
fun SearchAppBar(
    text: String,
    onTextChange: (String) -> Unit,
    onCloseClicked: () -> Unit,
    onSearchClicked: (String) -> Unit,
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(White),
    )
    {
        TextField(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
            value = text,
            onValueChange = {
                onTextChange(it)
            },
            placeholder = {
                Text(
                    modifier = Modifier.alpha(ContentAlpha.medium),
                    text = "Search here...",
                    style = FontMedium14(Gray)
                )
            },
            singleLine = true,
            leadingIcon = {
                IconButton(
                    modifier = Modifier,
                    onClick = {
                        onSearchClicked(text)
                        keyboardController?.hide()
                    }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_search),
                        contentDescription = "Search Icon",
                        tint = Black
                    )
                }
            },
            trailingIcon = {
                IconButton(
                    onClick = {
                        if (text.isNotEmpty()) {
                            onTextChange("")
                        } else {
                            onCloseClicked()
                        }
                    }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_cross),
                        contentDescription = "Close Icon",
                        tint = Black
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearchClicked(text)
                    keyboardController?.hide()
                }
            ),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                cursorColor = Black
            )
        )
    }
}

@Preview(name = "Credit card front side")
@Composable
private fun CreditCardPreview() {
    Column(
        modifier = Modifier.width(500.dp)
    ) {
        CreditCard(
            userCreditCardEntity = UserCreditCardEntity(
                cardName = "String",
                cardNumber = "String",
                cardExpirationDate = "String",
                cvv2 = "String",
            ),
            selected = false,
            onClick = {},
            deleteCard = {}
        )
    }
}
