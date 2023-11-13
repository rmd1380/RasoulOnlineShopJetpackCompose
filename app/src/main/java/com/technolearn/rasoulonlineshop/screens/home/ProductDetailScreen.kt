package com.technolearn.rasoulonlineshop.screens.home

import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.progressSemantics
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.skydoves.landscapist.glide.GlideImage
import com.technolearn.rasoulonlineshop.R
import com.technolearn.rasoulonlineshop.helper.AddToFavorite
import com.technolearn.rasoulonlineshop.helper.CustomButton
import com.technolearn.rasoulonlineshop.helper.CustomRatingBar
import com.technolearn.rasoulonlineshop.helper.CustomTopAppBar
import com.technolearn.rasoulonlineshop.helper.DropDown
import com.technolearn.rasoulonlineshop.helper.LoadingInColumn
import com.technolearn.rasoulonlineshop.helper.ProductItem
import com.technolearn.rasoulonlineshop.helper.Tag
import com.technolearn.rasoulonlineshop.navigation.NavigationBarItemsGraph
import com.technolearn.rasoulonlineshop.ui.theme.Background
import com.technolearn.rasoulonlineshop.ui.theme.Black
import com.technolearn.rasoulonlineshop.ui.theme.FontRegular11
import com.technolearn.rasoulonlineshop.ui.theme.FontRegular14
import com.technolearn.rasoulonlineshop.ui.theme.FontRegular16
import com.technolearn.rasoulonlineshop.ui.theme.FontSemiBold16
import com.technolearn.rasoulonlineshop.ui.theme.FontSemiBold18
import com.technolearn.rasoulonlineshop.ui.theme.FontSemiBold24
import com.technolearn.rasoulonlineshop.ui.theme.Gray
import com.technolearn.rasoulonlineshop.ui.theme.Primary
import com.technolearn.rasoulonlineshop.util.Extensions.orDefault
import com.technolearn.rasoulonlineshop.vm.ShopViewModel
import com.technolearn.rasoulonlineshop.vo.enums.Status
import com.technolearn.rasoulonlineshop.vo.model.helperComponent.CustomAction
import com.technolearn.rasoulonlineshop.vo.res.ProductRes
import timber.log.Timber

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProductDetailScreen(navController: NavController, productId: Int?, viewModel: ShopViewModel) {

    val productList by remember { viewModel.allProduct }.observeAsState()
    val productRes by remember { viewModel.productById }.observeAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchAllProduct(0, 10)
        viewModel.fetchProductById(productId.orDefault())
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
                title = productRes?.data?.data?.title.orDefault(),
                style = FontSemiBold18(Black),
                navigationIcon = ImageVector.vectorResource(id = R.drawable.ic_chevron_back),
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
                //Slider
                when (productRes?.status) {
                    Status.LOADING -> {
                        item {
                            LoadingInColumn(
                                Modifier
                                    .fillMaxSize()
                                    .height(536.dp)
                            )
                            Spacer(modifier = Modifier.height(18.dp))
                        }
                        Timber.d("productResDetail::LOADING${productRes}")
                    }

                    Status.SUCCESS -> {
                        item {
                            val pagerState = rememberPagerState(pageCount = {
                                productRes?.data?.data?.image?.size.orDefault()
                            })
                            HorizontalPager(
                                state = pagerState,
                                key = { index: Int ->
                                    productRes?.data?.data?.image?.get(index).orDefault()
                                }
                            ) { page ->
                                SliderItem(
                                    sliderRes = productRes?.data?.data?.image?.get(page).orDefault()
                                )
                            }
                            Spacer(modifier = Modifier.height(12.dp))
                        }
                        Timber.d("productResDetail::SUCCESS${productRes?.data}")
                    }

                    Status.ERROR -> {
                        Timber.d("productResDetail::ERROR${productRes?.message}")
                    }

                    else -> {}
                }

                //DropDowns::Size,Color
                item {
                    DropdownsWithAddFavorite(productRes?.data?.data ?: ProductRes(), viewModel)
                    Spacer(modifier = Modifier.height(20.dp))
                }
                //Brand-Rate-Price-Desc
                item {
                    BrandRatePriceDesc(
                        productRes?.data?.data ?: ProductRes()
                    )
                    Divider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 20.dp), thickness = 0.4.dp, color = Gray
                    )
                }
                //Shipping info
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = stringResource(R.string.shipping_info),
                            style = FontRegular16(Black)
                        )
                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(
                                imageVector = ImageVector.vectorResource(R.drawable.ic_chevron_right),
                                contentDescription = "ic_chevron_right",
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                    Divider(
                        modifier = Modifier
                            .fillMaxWidth(),
                        thickness = 0.4.dp, color = Gray
                    )
                }
                //Support
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = stringResource(R.string.support),
                            style = FontRegular16(Black)
                        )
                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(
                                imageVector = ImageVector.vectorResource(R.drawable.ic_chevron_right),
                                contentDescription = "ic_chevron_right",
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                    Divider(
                        modifier = Modifier
                            .fillMaxWidth(),
                        thickness = 0.4.dp, color = Gray
                    )
                    Spacer(modifier = Modifier.height(18.dp))
                }
                //youCanAlsoLikeThisText
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = stringResource(R.string.you_can_also_like_this),
                            style = FontSemiBold18(Black),
                        )
                        Text(
                            text = "${productList?.data?.data?.size} items",
                            style = FontRegular11(Gray),
                            modifier = Modifier.clickable {
                            }
                        )
                    }
                    Spacer(modifier = Modifier.height(18.dp))
                }
                //Product
                item {
                    LazyRow(contentPadding = PaddingValues(horizontal = 8.dp)) {
                        when (productList?.status) {
                            Status.LOADING -> {
                                item {
                                    LoadingInColumn(
                                        Modifier
                                            .fillMaxSize()
                                            .height(536.dp)
                                    )
                                    Spacer(modifier = Modifier.height(18.dp))
                                }
                                Timber.d("ProductOnDetail::LOADING${productList?.message}")
                            }

                            Status.SUCCESS -> {
                                items(productList?.data?.data?.size.orDefault()) { productRes ->
                                    ProductItem(
                                        productRes = productList?.data?.data?.get(productRes)
                                            ?: ProductRes(),
                                        navController = navController,
                                        viewModel =viewModel,
                                    )
                                }
                                Timber.d("ProductOnDetail::SUCCESS${productList?.data}")
                            }

                            Status.ERROR -> {
                                Timber.d("ProductOnDetail::ERROR${productList?.message}")
                            }

                            else -> {}
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SliderItem(sliderRes: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        GlideImage(
            imageModel = sliderRes,
            modifier = Modifier
                .fillMaxWidth()
                .height(536.dp),
            contentScale = ContentScale.Fit,
            loading = {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(15.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = CenterHorizontally
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .progressSemantics()
                            .size(24.dp),
                        color = Black,
                        strokeWidth = 2.dp
                    )
                }
            },
            failure = {
                Text(text = "image request failed.")
            })
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownsWithAddFavorite(productRes: ProductRes, viewModel: ShopViewModel) {
    val isProductLiked by remember { viewModel.getAllFavorite() }.observeAsState(emptyList())

    val sheetState = rememberModalBottomSheetState()

    var showSizeBottomSheet by rememberSaveable { mutableStateOf(false) }
    var showColorBottomSheet by rememberSaveable { mutableStateOf(false) }

    var isDropDownSizeFocused by rememberSaveable { mutableStateOf(false) }
    var isDropDownColorFocused by rememberSaveable { mutableStateOf(false) }

    val colorList by remember { viewModel.allColor }.observeAsState()
    val product by remember { viewModel.productById }.observeAsState()

    var selectedColorText by rememberSaveable { mutableStateOf("Color") }
    var selectedSizeText by rememberSaveable { mutableStateOf("Size") }

    LaunchedEffect(Unit) {
        viewModel.fetchAllColor()
    }
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
                value = selectedSizeText.orDefault(),
                modifier = Modifier.weight(1f),
                isFocused = isDropDownSizeFocused
            ) {
                isDropDownSizeFocused = true
                isDropDownColorFocused = false
                showSizeBottomSheet = true
            }
            Spacer(modifier = Modifier.width(12.dp))
            DropDown(
                value = selectedColorText.orDefault(),
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
                    .size(36.dp)
                    .weight(0.25f),
                productRes = productRes,
                onToggleFavorite = {
                    viewModel.toggleAddToFavorites(it)
                },
                isProductLikedState = isProductLiked.any { it.id == productRes.id }
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
                        when (product?.status) {
                            Status.LOADING -> {
                                Timber.d("productSize::LOADING")
                            }

                            Status.SUCCESS -> {
                                items(product?.data?.data?.sizes?.size.orDefault()) { index ->
                                    Tag(
                                        defaultValue = product?.data?.data?.sizes?.get(index)?.title.orDefault(),
                                        roundCorner = 8.dp,
                                    ) {
                                        showSizeBottomSheet = !showSizeBottomSheet
                                        selectedSizeText = product?.data?.data?.sizes?.get(index)?.title.orDefault()
                                    }
                                }
                                Timber.d("productSize::SUCCESS::${product?.data?.data?.sizes}")
                            }

                            Status.ERROR -> {
                                Timber.d("productSize::ERROR::${product?.message}")
                            }

                            else -> {}
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
                        when (colorList?.status) {
                            Status.LOADING -> {
                                Timber.d("colorList::LOADING")
                            }

                            Status.SUCCESS -> {
                                items(colorList?.data?.data?.size.orDefault()) { index ->
                                    Tag(
                                        defaultValue = colorList?.data?.data?.get(index)?.title.orDefault(),
                                        roundCorner = 8.dp,
                                        tagColor = colorList?.data?.data?.get(index)?.title.orDefault()
                                    ) {
                                        showColorBottomSheet = !showColorBottomSheet
                                        selectedColorText = colorList?.data?.data?.get(index)?.title.orDefault()
                                    }
                                }
                                Timber.d("colorList::SUCCESS::${colorList?.data}")
                                Timber.d("colorList000::SUCCESS::${colorList?.data?.data?.get(0)?.title}")
                            }

                            Status.ERROR -> {
                                Timber.d("colorList::ERROR::${colorList?.message}")
                            }

                            else -> {}
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
                if (productRes.hasDiscount.orDefault() > 0f) {
                    val discountAmount =
                        (productRes.price?.times(productRes.hasDiscount.orDefault()))?.div(100.0)
                    val finalPrice = productRes.price?.minus(discountAmount.orDefault())
                    Text(
                        modifier = Modifier.padding(start = 4.dp),
                        text = "${finalPrice}$",
                        style = FontSemiBold24(Primary),
                        textAlign = TextAlign.End
                    )
                }
                Text(
                    text = "${productRes.price}$",
                    style = if (productRes.hasDiscount.orDefault() > 0f) FontSemiBold16(Gray) else FontSemiBold24(
                        Black
                    ),
                    textDecoration = if (productRes.hasDiscount.orDefault() > 0f) TextDecoration.LineThrough else TextDecoration.None,
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