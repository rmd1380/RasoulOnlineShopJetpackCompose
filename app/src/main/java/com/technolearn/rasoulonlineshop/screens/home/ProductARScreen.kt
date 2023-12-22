package com.technolearn.rasoulonlineshop.screens.home

import android.widget.Toast
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
import androidx.compose.foundation.layout.systemBarsPadding
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.android.filament.Engine
import com.google.ar.core.Anchor
import com.google.ar.core.Config
import com.google.ar.core.Frame
import com.google.ar.core.Plane
import com.google.ar.core.TrackingFailureReason
import com.skydoves.landscapist.glide.GlideImage
import com.technolearn.rasoulonlineshop.R
import com.technolearn.rasoulonlineshop.helper.AddToFavorite
import com.technolearn.rasoulonlineshop.helper.CircleIconCard
import com.technolearn.rasoulonlineshop.helper.CustomButton
import com.technolearn.rasoulonlineshop.helper.CustomRatingBar
import com.technolearn.rasoulonlineshop.helper.CustomTopAppBar
import com.technolearn.rasoulonlineshop.helper.DropDown
import com.technolearn.rasoulonlineshop.helper.LoadingInColumn
import com.technolearn.rasoulonlineshop.helper.LottieComponent
import com.technolearn.rasoulonlineshop.helper.ProductItem
import com.technolearn.rasoulonlineshop.helper.QuantityControl
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
import com.technolearn.rasoulonlineshop.util.Extensions.orFalse
import com.technolearn.rasoulonlineshop.vm.ShopViewModel
import com.technolearn.rasoulonlineshop.vo.entity.UserCartEntity
import com.technolearn.rasoulonlineshop.vo.enums.Status
import com.technolearn.rasoulonlineshop.vo.model.helperComponent.CustomAction
import com.technolearn.rasoulonlineshop.vo.res.ProductRes
import io.github.sceneview.ar.ARScene
import io.github.sceneview.ar.arcore.createAnchorOrNull
import io.github.sceneview.ar.arcore.getUpdatedPlanes
import io.github.sceneview.ar.arcore.isValid
import io.github.sceneview.ar.getDescription
import io.github.sceneview.ar.node.AnchorNode
import io.github.sceneview.ar.rememberARCameraNode
import io.github.sceneview.loaders.MaterialLoader
import io.github.sceneview.loaders.ModelLoader
import io.github.sceneview.model.ModelInstance
import io.github.sceneview.node.CubeNode
import io.github.sceneview.node.ModelNode
import io.github.sceneview.rememberCollisionSystem
import io.github.sceneview.rememberEngine
import io.github.sceneview.rememberMaterialLoader
import io.github.sceneview.rememberModelLoader
import io.github.sceneview.rememberNodes
import io.github.sceneview.rememberOnGestureListener
import io.github.sceneview.rememberView
import timber.log.Timber

private const val kModelFile = "models/caterpillar_work_boot.glb"
private const val kMaxModelInstances = 2


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProductDetailScreen(navController: NavController, productId: Int?, viewModel: ShopViewModel) {

    val productList by remember { viewModel.allProduct }.observeAsState()
    val productRes by remember { viewModel.productById }.observeAsState()
    var isAddedToCart by remember { mutableStateOf(false) }
    var userCartEntity by remember { mutableStateOf(viewModel.getOneProductInUserCartById(productId.orDefault()).value) }
    var price by remember { mutableDoubleStateOf(0.0) }
    var quantity by remember { mutableStateOf(0) }
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        viewModel.fetchAllProduct(0, 10)
        viewModel.fetchProductById(productId.orDefault())
    }
    Timber.d("isAddedToCart:::${isAddedToCart}::$productId::$userCartEntity")
    Scaffold(
        backgroundColor = Background,
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(0.5.dp)
            ) {
                when (productRes?.status) {
                    Status.LOADING -> {}
                    Status.SUCCESS -> {
                        if (isAddedToCart.orFalse() || quantity > 0) {
                            QuantityControl(
                                quantity = quantity,
                                onIncrease = { quantity++ },
                                onDecrease = {
                                    quantity = maxOf(0, quantity - 1)
                                    if (quantity == 0) {
                                        isAddedToCart = false
                                    }
                                },
                                onAddToCart = {
                                    viewModel.addToCart(
                                        UserCartEntity(
                                            id = productId,
                                            name = productRes?.data?.data?.title.orDefault(),
                                            image = productRes?.data?.data?.image?.get(0),
                                            color = userCartEntity?.color,
                                            size = userCartEntity?.size,
                                            price = price,
                                            quantity = quantity
                                        )
                                    )
                                }
                            )
                        } else {
                            CustomButton(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                text = stringResource(R.string.wanna_buy),
                                onClick = {
                                    if (userCartEntity?.color != "Color" && userCartEntity?.size != "Size") {
                                        if (productRes?.data?.data?.id == productId) {
                                            isAddedToCart =
                                                productRes?.data?.data?.isAddToCart.orFalse()
                                        }
                                        quantity++
                                    } else {
                                        Toast.makeText(
                                            context,
                                            "Please Select Size And Color",
                                            Toast.LENGTH_SHORT
                                        ).show()

                                    }
                                }
                            )
                        }
                    }

                    Status.ERROR -> {}
                    else -> {}
                }
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
            when (productRes?.status) {
                Status.LOADING -> {
                    LottieComponent(R.raw.main_progress)
                }

                Status.SUCCESS -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth(),
                        contentPadding = PaddingValues(bottom = 32.dp)
                    ) {
                        //Slider
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
                                    sliderRes = productRes?.data?.data?.image?.get(page)
                                        .orDefault(),
                                )
                            }
                            Spacer(modifier = Modifier.height(12.dp))
                        }
                        Timber.d("productResDetail::SUCCESS${productRes?.data}")
                        //DropDowns::Size,Color
                        item {
                            userCartEntity = DropdownsWithAddFavorite(
                                productRes?.data?.data ?: ProductRes(),
                                viewModel,
                            )
                            Spacer(modifier = Modifier.height(20.dp))
                        }
                        //Brand-Rate-Price-Desc
                        item {
                            price = BrandRatePriceDesc(
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
                                                viewModel = viewModel,
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

                Status.ERROR -> {}
                else -> {}
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
fun DropdownsWithAddFavorite(
    productRes: ProductRes,
    viewModel: ShopViewModel,
): UserCartEntity {
    var open3DView by remember { mutableStateOf(false) }
    val isProductLiked by remember { viewModel.getAllFavorite() }.observeAsState(emptyList())

    val sheetState = rememberModalBottomSheetState()

    var showSizeBottomSheet by rememberSaveable { mutableStateOf(false) }
    var showColorBottomSheet by rememberSaveable { mutableStateOf(false) }

    var isDropDownSizeFocused by rememberSaveable { mutableStateOf(false) }
    var isDropDownColorFocused by rememberSaveable { mutableStateOf(false) }

    var selectedColorText by rememberSaveable {
        mutableStateOf(productRes.colors.firstOrNull()?.title.takeIf { it?.isNotEmpty().orFalse() }
            ?: "Color")
    }

    var selectedSizeText by rememberSaveable {
        mutableStateOf(productRes.sizes.firstOrNull()?.title.takeIf { it?.isNotEmpty().orFalse() }
            ?: "Size")
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
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
            if (productRes.threeDModel?.isNotEmpty().orFalse()) {
                Spacer(modifier = Modifier.width(10.dp))
                CircleIconCard(
                    modifier = Modifier
                        .size(36.dp),
                    icon = painterResource(id = R.drawable.ic_three_d_view),
                    iconSize = 24.dp
                ) {
                    open3DView=true
                }
            }
            if(open3DView){
                ARScreen()
            }
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

                        items(productRes.sizes.size.orDefault()) { index ->
                            Tag(
                                defaultValue = productRes.sizes[index].title.orDefault(),
                                roundCorner = 8.dp,
                            ) {
                                showSizeBottomSheet = !showSizeBottomSheet
                                selectedSizeText = productRes.sizes[index].title.orDefault()
                            }
                        }
                        Timber.d("productSize::SUCCESS::${productRes.sizes}")
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
                        items(productRes.colors.size.orDefault()) { index ->
                            Tag(
                                defaultValue = productRes.colors[index].title.orDefault(),
                                roundCorner = 8.dp,
                                tagColor = productRes.colors[index].title.orDefault()
                            ) {
                                showColorBottomSheet = !showColorBottomSheet
                                selectedColorText = productRes.colors[index].title.orDefault()
                            }
                        }
//                        Timber.d("colorList000::SUCCESS::${productRes.colors[0].title}")
                    }
                }
            }
        }
    }
    return UserCartEntity(
        id = null,
        name = null,
        image = null,
        color = selectedColorText,
        size = selectedSizeText,
        price = null,
        quantity = null
    )
}

@Composable
fun BrandRatePriceDesc(
    productRes: ProductRes
): Double {
    var finalPrice = 0.0
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
                    finalPrice = productRes.price?.minus(discountAmount.orDefault()).orDefault()
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
    return if (finalPrice > 0) finalPrice else productRes.price.orDefault()
}

@Composable
private fun ARScreen() {
    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background){
        Box(
            modifier = Modifier.fillMaxSize(),
        ) {
            val childNodes = rememberNodes()
            val engine = rememberEngine()
            val modelLoader = rememberModelLoader(engine)
            val materialLoader = rememberMaterialLoader(engine)
            val cameraNode = rememberARCameraNode(engine)
            val view = rememberView(engine)
            val collisionSystem = rememberCollisionSystem(view)

            var planeRenderer by remember { mutableStateOf(true) }

            val modelInstances = remember { mutableListOf<ModelInstance>() }
            var trackingFailureReason by remember {
                mutableStateOf<TrackingFailureReason?>(null)
            }
            var frame by remember { mutableStateOf<Frame?>(null) }
            ARScene(
                modifier = Modifier.fillMaxSize(),
                childNodes = childNodes,
                engine = engine,
                view = view,
                modelLoader = modelLoader,
                collisionSystem = collisionSystem,
                sessionConfiguration = { session, config ->
                    config.depthMode =
                        when (session.isDepthModeSupported(Config.DepthMode.AUTOMATIC)) {
                            true -> Config.DepthMode.AUTOMATIC
                            else -> Config.DepthMode.DISABLED
                        }
                    config.instantPlacementMode = Config.InstantPlacementMode.LOCAL_Y_UP
                    config.lightEstimationMode =
                        Config.LightEstimationMode.ENVIRONMENTAL_HDR
                },
                cameraNode = cameraNode,
                planeRenderer = planeRenderer,
                onTrackingFailureChanged = {
                    trackingFailureReason = it
                },
                onSessionUpdated = { session, updatedFrame ->
                    frame = updatedFrame

                    if (childNodes.isEmpty()) {
                        updatedFrame.getUpdatedPlanes()
                            .firstOrNull { it.type == Plane.Type.HORIZONTAL_UPWARD_FACING }
                            ?.let { it.createAnchorOrNull(it.centerPose) }?.let { anchor ->
                                childNodes += createAnchorNode(
                                    engine = engine,
                                    modelLoader = modelLoader,
                                    materialLoader = materialLoader,
                                    modelInstances = modelInstances,
                                    anchor = anchor
                                )
                            }
                    }
                },
                onGestureListener = rememberOnGestureListener(
                    onSingleTapConfirmed = { motionEvent, node ->
                        if (node == null) {
                            val hitResults = frame?.hitTest(motionEvent.x, motionEvent.y)
                            hitResults?.firstOrNull {
                                it.isValid(
                                    depthPoint = false,
                                    point = false
                                )
                            }?.createAnchorOrNull()
                                ?.let { anchor ->
                                    planeRenderer = false
                                    childNodes += createAnchorNode(
                                        engine = engine,
                                        modelLoader = modelLoader,
                                        materialLoader = materialLoader,
                                        modelInstances = modelInstances,
                                        anchor = anchor
                                    )
                                }
                        }
                    })
            )
            androidx.compose.material3.Text(
                modifier = Modifier
                    .systemBarsPadding()
                    .fillMaxWidth()
                    .align(Alignment.TopCenter)
                    .padding(top = 16.dp, start = 32.dp, end = 32.dp),
                textAlign = TextAlign.Center,
                fontSize = 28.sp,
                color = Color.White,
                text = trackingFailureReason?.let {
                    it.getDescription(LocalContext.current)
                } ?: if (childNodes.isEmpty()) {
                    stringResource(R.string.point_your_phone_down)
                } else {
                    stringResource(R.string.tap_anywhere_to_add_model)
                }
            )
        }
    }

}

private fun createAnchorNode(
    engine: Engine,
    modelLoader: ModelLoader,
    materialLoader: MaterialLoader,
    modelInstances: MutableList<ModelInstance>,
    anchor: Anchor
): AnchorNode {
    val anchorNode = AnchorNode(engine = engine, anchor = anchor)
    val modelNode = ModelNode(
        modelInstance = modelInstances.apply {
            if (isEmpty()) {
                this += modelLoader.createInstancedModel(kModelFile, kMaxModelInstances)
            }
        }.removeLast(),
        // Scale to fit in a 0.5 meters cube
        scaleToUnits = 0.5f
    ).apply {
        // Model Node needs to be editable for independent rotation from the anchor rotation
        isEditable = true
    }
    val boundingBoxNode = CubeNode(
        engine,
        size = modelNode.extents,
        center = modelNode.center,
        materialInstance = materialLoader.createColorInstance(Color.White.copy(alpha = 0.5f))
    ).apply {
        isVisible = false
    }
    modelNode.addChildNode(boundingBoxNode)
    anchorNode.addChildNode(modelNode)

    listOf(modelNode, anchorNode).forEach {
        it.onEditingChanged = { editingTransforms ->
            boundingBoxNode.isVisible = editingTransforms.isNotEmpty()
        }
    }
    return anchorNode
}