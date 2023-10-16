package com.technolearn.rasoulonlineshop.screens.home

import androidx.annotation.DrawableRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.technolearn.rasoulonlineshop.MainActivity
import com.technolearn.rasoulonlineshop.R
import com.technolearn.rasoulonlineshop.helper.AddToFavorite
import com.technolearn.rasoulonlineshop.helper.CustomButton
import com.technolearn.rasoulonlineshop.helper.CustomRatingBar
import com.technolearn.rasoulonlineshop.helper.CustomTopAppBar
import com.technolearn.rasoulonlineshop.helper.DropDown
import com.technolearn.rasoulonlineshop.helper.Tag
import com.technolearn.rasoulonlineshop.navigation.BottomNavigationBar
import com.technolearn.rasoulonlineshop.navigation.NavigationBarItemsGraph
import com.technolearn.rasoulonlineshop.navigation.Screen
import com.technolearn.rasoulonlineshop.ui.theme.Background
import com.technolearn.rasoulonlineshop.ui.theme.Black
import com.technolearn.rasoulonlineshop.ui.theme.FontRegular11
import com.technolearn.rasoulonlineshop.ui.theme.FontRegular14
import com.technolearn.rasoulonlineshop.ui.theme.FontSemiBold16
import com.technolearn.rasoulonlineshop.ui.theme.FontSemiBold18
import com.technolearn.rasoulonlineshop.ui.theme.FontSemiBold24
import com.technolearn.rasoulonlineshop.ui.theme.Gray
import com.technolearn.rasoulonlineshop.ui.theme.Primary
import com.technolearn.rasoulonlineshop.util.Extensions.orDefault
import com.technolearn.rasoulonlineshop.vo.model.helperComponent.CustomAction
import com.technolearn.rasoulonlineshop.vo.res.ProductRes

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProductDetailScreen(navController: NavController, productId: Int?) {
    val testProductList: ArrayList<ProductRes> = arrayListOf()
    val testProduct = ProductRes()
    testProductList.add(
        ProductRes(
            11,
            "Adidas",
            "Shirt",
            arrayListOf(R.drawable.test_image_slider1, R.drawable.test_image_slider2),
            "",
            19.9,
            3.0,
            "NEW",
            "Short dress in soft cotton jersey with decorative buttons down the front and a wide, " +
                    "frill-trimmed square neckline with concealed elasticated." +
                    " Elasticated seam under the bust and short puff sleeves with a small frill trim.",
            0f
        )
    )
    testProductList.add(
        ProductRes(
            12,
            "Nike",
            "Shoes",
            arrayListOf(R.drawable.test_image_slider2, R.drawable.test_image_slider3),
            "",
            25.0,
            3.5,
            "5%",
            "Short dress in soft cotton jersey with decorative buttons down the front and a wide, " +
                    "frill-trimmed square neckline with concealed elasticated." +
                    " Elasticated seam under the bust and short puff sleeves with a small frill trim.",
            5f
        )
    )
    testProductList.add(
        ProductRes(
            13,
            "D&G",
            "Shirt",
            arrayListOf(R.drawable.test_image_slider3, R.drawable.test_image_slider4),
            "",
            12.0,
            2.0,
            "NEW",
            "Short dress in soft cotton jersey with decorative buttons down the front and a wide, " +
                    "frill-trimmed square neckline with concealed elasticated." +
                    " Elasticated seam under the bust and short puff sleeves with a small frill trim.",
            25f
        )
    )
    testProductList.add(
        ProductRes(
            14,
            "Jack&Jones",
            "pant",
            arrayListOf(R.drawable.test_image_slider4, R.drawable.test_image_slider1),
            "",
            16.3,
            4.0,
            "30%",
            "Short dress in soft cotton jersey with decorative buttons down the front and a wide, " +
                    "frill-trimmed square neckline with concealed elasticated." +
                    " Elasticated seam under the bust and short puff sleeves with a small frill trim.",
            30f
        )
    )
    testProductList.forEach {
        if (it.id == productId) {
            testProduct.id = it.id
            testProduct.brand = it.brand
            testProduct.title = it.title
            testProduct.image = it.image
            testProduct.addDate = it.addDate
            testProduct.price = it.price
            testProduct.rate = it.rate
            testProduct.description = it.description
            testProduct.hasDiscount = it.hasDiscount
        }
    }
    Scaffold(
        backgroundColor = Background,
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(0.5.dp)
            ) {
                CustomButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    text = stringResource(R.string.add_to_cart),
                    onClick = {

                    }
                )
            }
        },
        topBar = {
            CustomTopAppBar(
                title = testProduct.title.orDefault(),
                style = FontSemiBold18(Black),
                navigationIcon = ImageVector.vectorResource(id = R.drawable.ic_back),
                actionIcons = arrayListOf(
                    CustomAction("Share", ImageVector.vectorResource(id = R.drawable.ic_share))
                ),
                navigationOnClick = {
                    navController.navigate(NavigationBarItemsGraph.Home.route) {
                        popUpTo(NavigationBarItemsGraph.Home.route) {
                            inclusive = true
                        }
                    }
                },
                actionOnclick = { customAction ->
                    when (customAction.name) {
                        "Share" -> {
                        }
                    }
                }
            )


        }

    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth(),
                contentPadding = PaddingValues(bottom = 32.dp)
            ) {
//                SliderItem(sliderRes = testProduct[index].image[0])
                //Slider
                item {
                    val pagerState = rememberPagerState(pageCount = {
                        testProduct.image.size
                    })
                    HorizontalPager(
                        state = pagerState,
                        key = { index: Int -> testProduct.image[index] }
                    ) { page ->
                        SliderItem(sliderRes = testProduct.image[page])
                    }

                    Spacer(modifier = Modifier.height(12.dp))
                }
                //DropDowns::Size,Color
                item {
                    DropdownsWithAddFavorite()
                    Spacer(modifier = Modifier.height(20.dp))
                }
                //Brand-Rate-Price-Desc
                item {
                    BrandRatePriceDesc(
                        testProduct
                    )
                }
            }
        }
    }
}

@Composable
fun SliderItem(@DrawableRes sliderRes: Int) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Image(
            painter = painterResource(id = sliderRes),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(536.dp),
            contentScale = ContentScale.Crop
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownsWithAddFavorite() {
    val sheetState = rememberModalBottomSheetState()

    var showSizeBottomSheet by remember { mutableStateOf(false) }
    var showColorBottomSheet by remember { mutableStateOf(false) }
    var isAddToFavorite by remember { mutableStateOf(false) }

    var isDropDownSizeFocused by remember { mutableStateOf(false) }
    var isDropDownColorFocused by remember { mutableStateOf(false) }

    val buttonColorLabels = listOf("Black", "Yellow", "Red", "Blue", "Green")
    var selectedColorText by remember { mutableStateOf("Black") }

    val buttonSizeLabels = listOf("XL", "L", "M", "S", "XS")
    var selectedSizeText by remember { mutableStateOf("Size") }


    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            DropDown(
                value = selectedSizeText,
                modifier = Modifier.weight(1f),
                isFocused = isDropDownSizeFocused
            ) {
                isDropDownSizeFocused = true
                isDropDownColorFocused = false
                showSizeBottomSheet = true
            }
            Spacer(modifier = Modifier.width(12.dp))
            DropDown(
                value = selectedColorText,
                modifier = Modifier.weight(1f),
                isFocused = isDropDownColorFocused
            ) {
                isDropDownSizeFocused = false
                isDropDownColorFocused = true
                showColorBottomSheet = true
            }
            Spacer(modifier = Modifier.width(20.dp))
            AddToFavorite(
                modifier = Modifier
                    .clickable {
                        isAddToFavorite = !isAddToFavorite
                    }
                    .size(36.dp)
                    .weight(0.25f),
                isAddToFavorite = isAddToFavorite
            )
        }
        if (showSizeBottomSheet) {
            ModalBottomSheet(
                sheetState = sheetState,
                onDismissRequest = {
                    showSizeBottomSheet = false
                }
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 72.dp),
                    horizontalAlignment = CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = stringResource(R.string.select_size),
                        style = FontSemiBold18(Black),
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(3),
                    ) {
                        items(buttonSizeLabels.size) { index ->
                            Tag(
                                defaultValue = buttonSizeLabels[index],
                                roundCorner = 8.dp,
                            ) {
                                showSizeBottomSheet = !showSizeBottomSheet
                                selectedSizeText = buttonSizeLabels[index]
                            }
                        }
                    }
                }
            }
        }
        if (showColorBottomSheet) {
            ModalBottomSheet(
                sheetState = sheetState,
                onDismissRequest = {
                    showColorBottomSheet = false
                }
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 72.dp),
                    horizontalAlignment = CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = stringResource(R.string.select_color),
                        style = FontSemiBold18(Black),
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                    LazyVerticalGrid(columns = GridCells.Fixed(3)) {
                        items(buttonColorLabels.size) { index ->
                            Tag(
                                defaultValue = buttonColorLabels[index],
                                roundCorner = 8.dp,
                                tagColor = buttonColorLabels[index]
                            ) {
                                showColorBottomSheet = !showColorBottomSheet
                                selectedColorText = buttonColorLabels[index]
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BrandRatePriceDesc(
    productRes: ProductRes
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
    ) {
        Row(
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = productRes.brand.orDefault(),
                    style = FontSemiBold24(Black),
                )
                Text(
                    text = productRes.title.orDefault(),
                    style = FontRegular11(Gray),
                )
                Spacer(modifier = Modifier.height(8.dp))
                CustomRatingBar(
                    rating = productRes.rate.orDefault(),
                    stars = 5
                )
            }
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.Center
            ) {
                if (productRes.hasDiscount!! > 0f) {
                    val discountAmount =
                        (productRes.price?.times(productRes.hasDiscount!!))?.div(100.0)
                    val finalPrice = productRes.price?.minus(discountAmount!!)
                    Text(
                        modifier = Modifier.padding(start = 4.dp),
                        text = "${finalPrice}$",
                        style = FontSemiBold24(Primary),
                        textAlign = TextAlign.End
                    )
                }
                Text(
                    text = "${productRes.price}$",
                    style = if (productRes.hasDiscount!! > 0f) FontSemiBold16(Gray) else FontSemiBold24(
                        Black
                    ),
                    textDecoration = if (productRes.hasDiscount!! > 0f) TextDecoration.LineThrough else TextDecoration.None,
                    textAlign = TextAlign.End
                )

            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = productRes.description.orDefault(),
            style = FontRegular14(Black),
            textAlign = TextAlign.Justify
        )
    }

}