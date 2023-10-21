package com.technolearn.rasoulonlineshop.vm

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.technolearn.rasoulonlineshop.R
import com.technolearn.rasoulonlineshop.api.ApiService
import com.technolearn.rasoulonlineshop.util.safeApiCall
import com.technolearn.rasoulonlineshop.vo.generics.ApiResponse
import com.technolearn.rasoulonlineshop.vo.generics.Resource
import com.technolearn.rasoulonlineshop.vo.res.ProductRes
import com.technolearn.rasoulonlineshop.vo.res.SliderRes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ShopViewModel @Inject constructor(
    private val application: Application,
    private val apiService: ApiService
) : ViewModel() {

    private var sliderRes: MutableLiveData<Resource<ApiResponse<List<SliderRes>>>> =
        MutableLiveData()
    val products: MutableLiveData<List<ProductRes>> = MutableLiveData()

    init {
        products.value = listOf(
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
            ),
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
            ),
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
            ),
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
    }

    fun toggleAddToFavorites(productId: Int) {
        Timber.d("toggleAddToFavorites called for productId: $productId")
        val updatedList = products.value?.map { product ->
            if (product.id == productId) {
                val updatedProduct = product.copy(isAddToFavorites = !product.isAddToFavorites)
                Timber.d("toggleAddToFavorites called for productId::::updatedProduct $updatedProduct")
                updatedProduct
            } else {
                product
            }
        }
        updatedList.let { products.value = it }
    }


    fun getSlider(): MutableLiveData<Resource<ApiResponse<List<SliderRes>>>> {
        viewModelScope.launch {
            sliderRes = safeApiCall(Dispatchers.IO) {
                apiService.getAllSlider()
            } as MutableLiveData<Resource<ApiResponse<List<SliderRes>>>>
        }
        return sliderRes
    }
}