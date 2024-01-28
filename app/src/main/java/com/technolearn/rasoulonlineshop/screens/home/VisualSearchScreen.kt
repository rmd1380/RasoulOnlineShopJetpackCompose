package com.technolearn.rasoulonlineshop.screens.home

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.technolearn.rasoulonlineshop.R
import com.technolearn.rasoulonlineshop.helper.CustomButton
import com.technolearn.rasoulonlineshop.navigation.Screen
import com.technolearn.rasoulonlineshop.ui.theme.Black
import com.technolearn.rasoulonlineshop.ui.theme.FontSemiBold24
import com.technolearn.rasoulonlineshop.ui.theme.White
import com.technolearn.rasoulonlineshop.vo.enums.ButtonStyle
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import org.tensorflow.lite.task.vision.classifier.ImageClassifier
import java.nio.ByteBuffer
import java.nio.ByteOrder

var imageSize = 224

@Composable
fun VisualSearchScreen(navController: NavController) {
    Scaffold(
        backgroundColor = Black.copy(alpha = 0.5f),
        bottomBar = {},
        topBar = {}
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            PickImageFromGallery(navController)
        }
    }
}

@Composable
fun PickImageFromGallery(navController: NavController) {
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current
    val bitmap = remember { mutableStateOf<Bitmap?>(null) }
    var finalLabel = ""

    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
            imageUri = uri
        }

    Column(
        modifier =
        Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        if(imageUri != null){
            imageUri?.let {
                if (Build.VERSION.SDK_INT < 28) {
                    bitmap.value = MediaStore.Images
                        .Media.getBitmap(context.contentResolver, it)
                } else {
                    val source = ImageDecoder.createSource(context.contentResolver, it)
                    val tempBitmap = ImageDecoder.decodeBitmap(source)

                    // Convert the bitmap to a compatible configuration
                    bitmap.value = tempBitmap.copy(Bitmap.Config.ARGB_8888, true)
                }

                bitmap.value?.let { btm ->
                    Image(
                        bitmap = btm.asImageBitmap(),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(20.dp)
                    )
                    finalLabel=ClassifyImage(btm,context)
                }
            }
        }else{
            Image(
                painter = painterResource(id = R.drawable.ic_placeholder),
                contentDescription = null,
                modifier = Modifier
                    .size(200.dp)
                    .padding(20.dp)
            )
        }


        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = "Search for an outfit by uploading an image",
            style = FontSemiBold24(White),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.height(32.dp))
        CustomButton(
            modifier = Modifier
                .fillMaxWidth(),
            text = stringResource(R.string.upload_an_image),
            onClick = {
                launcher.launch("image/*")
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        if(imageUri != null){
            CustomButton(
                modifier = Modifier
                    .fillMaxWidth(),
                style = ButtonStyle.OUTLINED,
                text = "SEARCH PRODUCT",
                onClick = {
                    navController.navigate(Screen.SearchProductScreen.passWhatIsTitleForSearch(finalLabel))
                }
            )
        }
    }
}

@Composable
fun ClassifyImage(image1: Bitmap, context: Context):String {
    var finalLabel:String=""
    val model = ImageClassifier.createFromFileAndOptions(
        context,
        "mobilenetv1.tflite",
        ImageClassifier.ImageClassifierOptions.builder().build()
    )
    // Convert the input bitmap to a TensorImage
    val ti = TensorImage.fromBitmap(image1)
    val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 224, 224, 3), DataType.FLOAT32)
    val byteBuffer = ByteBuffer.allocateDirect(4 * imageSize * imageSize * 3)
    byteBuffer.order(ByteOrder.nativeOrder())

    val intValues = IntArray(ti.bitmap.width * ti.bitmap.height)
    ti.bitmap.getPixels(intValues, 0, ti.bitmap.width, 0, 0, ti.bitmap.width, ti.bitmap.height)

    var pixel = 0
    for (i in 0 until imageSize) {
        for (j in 0 until imageSize) {
            val `val` = intValues[pixel++] // RGB
            byteBuffer.putFloat((`val` shr 16 and 0xFF) * (1f / 255f))
            byteBuffer.putFloat((`val` shr 8 and 0xFF) * (1f / 255f))
            byteBuffer.putFloat((`val` and 0xFF) * (1f / 255f))
        }
    }
    inputFeature0.loadBuffer(byteBuffer)

    // Preprocess the image and classify it
    val results = model.classify(ti)
    var maxPos = 0
    var maxConfidence = 0f
    for (i in results.indices) {
        if (results[i].categories[i].score > maxConfidence) {
            maxConfidence = results[i].categories[i].score
            maxPos = i
        }
    }
    // Find the index of the class with the highest confidence
    val maxResult = results.maxByOrNull { it.categories[maxPos].score }

    // Display the result
    maxResult?.let { result ->
        finalLabel=result.categories[maxPos].label
        Text(
            text = finalLabel,
            style = FontSemiBold24(Black),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )
    }
    return finalLabel
}


//@Composable
//fun ClassifyImage1(image1: Bitmap,context: Context) {
////    val model = Mobilenetv1.newInstance(LocalContext.current)
//    val assetManager = context.assets
//    val modelFilename = "your_model_file.tflite" // Replace with your actual model filename
//    val modelInputStream = assetManager.open(modelFilename)
//
//    val model = ImageClassifier.createFromFileAndOptions(
//        context,
//        modelFilename,
//        ImageClassifier.ImageClassifierOptions.builder().build()
//    )
//    // Creates inputs for reference.
//    val ti = TensorImage.fromBitmap(image1)
//    val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 224, 224, 3), DataType.FLOAT32)
//    val byteBuffer = ByteBuffer.allocateDirect(4 * imageSize * imageSize * 3)
//    byteBuffer.order(ByteOrder.nativeOrder())
//
//    // get 1D array of 224 * 224 pixels in image
//    val intValues = IntArray(ti.bitmap.width * ti.bitmap.height)
//    ti.bitmap.getPixels(intValues, 0, ti.bitmap.width, 0, 0, ti.bitmap.width, ti.bitmap.height)
//
//    // iterate over pixels and extract R, G, and B values. Add to bytebuffer.
//    var pixel = 0
//    for (i in 0 until imageSize) {
//        for (j in 0 until imageSize) {
//            val `val` = intValues[pixel++] // RGB
//            byteBuffer.putFloat((`val` shr 16 and 0xFF) * (1f / 255f))
//            byteBuffer.putFloat((`val` shr 8 and 0xFF) * (1f / 255f))
//            byteBuffer.putFloat((`val` and 0xFF) * (1f / 255f))
//        }
//    }
//    inputFeature0.loadBuffer(byteBuffer)
//
//    // Runs model inference and gets result.
//    val outputs = model.process(ti)
//    val outputFeature0 = outputs.probabilityAsCategoryList
////    val outputs = model.process(inputFeature0)
////    val outputFeature0 = outputs.probabilityAsCategoryList
////    val confidences = outputFeature0.floatArray
//    // find the index of the class with the biggest confidence.
//    var maxPos = 0
//    var maxConfidence = 0f
//    for (i in outputFeature0.indices) {
//        if (outputFeature0[i].score > maxConfidence) {
//            maxConfidence = outputFeature0[i].score
//            maxPos = i
//        }
//    }
//
//    Text(
//        text = outputFeature0[maxPos].label,
//        style = FontSemiBold24(Black),
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(horizontal = 16.dp)
//    )
//    // Releases model resources if no longer used.
//    model.close()
//
//}

//val classes = arrayOf(
//    "Bag",
//    "cap",
//    "hairpin",
//    "christmas_hat",
//    "ring",
//    "cowboy_hat",
//    "glasses",
//    "earring",
//    "hat",
//    "wizard_hat",
//    "suits",
//    "pants",
//    "jacket",
//    "dress",
//    "hoodie",
//    "shirt",
//    "head_phones",
//    "phone",
//    "controller",
//    "watch",
//    "monitor",
//    "camera",
//    "keyboard",
//    "laptop",
//    "chair",
//    "sofa",
//    "tea_pot",
//    "vase",
//    "fan",
//    "lamp",
//    "handmixer",
//    "mirror",
//    "shoes",
//    "boot",
//    "high_heel"
//)
