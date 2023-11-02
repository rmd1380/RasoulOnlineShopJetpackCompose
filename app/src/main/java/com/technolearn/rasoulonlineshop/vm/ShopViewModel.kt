package com.technolearn.rasoulonlineshop.vm

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.technolearn.rasoulonlineshop.api.ApiService
import com.technolearn.rasoulonlineshop.db.dao.FavoritesDao
import com.technolearn.rasoulonlineshop.util.safeApiCall
import com.technolearn.rasoulonlineshop.vo.generics.ApiResponse
import com.technolearn.rasoulonlineshop.vo.generics.Resource
import com.technolearn.rasoulonlineshop.vo.req.LoginReq
import com.technolearn.rasoulonlineshop.vo.req.SignUpReq
import com.technolearn.rasoulonlineshop.vo.res.CategoryRes
import com.technolearn.rasoulonlineshop.vo.res.LoginRes
import com.technolearn.rasoulonlineshop.vo.res.ProductRes
import com.technolearn.rasoulonlineshop.vo.res.SignUpRes
import com.technolearn.rasoulonlineshop.vo.res.SliderRes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ShopViewModel @Inject constructor(
    private val application: Application,
    private val apiService: ApiService,
    private val favoritesDao: FavoritesDao
) : ViewModel() {

    //    private var register: MutableLiveData<Resource<ApiResponse<SignUpRes>>> =
//        MutableLiveData()
    private val _registerStatus = MutableLiveData<Resource<ApiResponse<SignUpRes>>>()
    val registerStatus: LiveData<Resource<ApiResponse<SignUpRes>>> = _registerStatus
    private val _loginStatus = MutableLiveData<Resource<ApiResponse<LoginRes>>>()
    val loginStatus: LiveData<Resource<ApiResponse<LoginRes>>> = _loginStatus
    private var allSlider: MutableLiveData<Resource<ApiResponse<List<SliderRes>>>> =
        MutableLiveData()
    private var sliderById: MutableLiveData<Resource<ApiResponse<SliderRes>>> =
        MutableLiveData()
    private var allProduct: MutableLiveData<Resource<ApiResponse<List<ProductRes>>>> =
        MutableLiveData()
    private var productById: MutableLiveData<Resource<ApiResponse<ProductRes>>> =
        MutableLiveData()
    private var allCategory: MutableLiveData<Resource<ApiResponse<List<CategoryRes>>>> =
        MutableLiveData()


    private val allFavoriteProducts = favoritesDao.getAllFavoriteProducts()

    fun getAllFavorite(): LiveData<List<ProductRes>> {
        return allFavoriteProducts
    }

    //region SignUp
    fun register(signUpReq: SignUpReq) {
        viewModelScope.launch {
            _registerStatus.value = Resource.loading(null)
            try {
                val response = safeApiCall(Dispatchers.IO) {
                    apiService.register(signUpReq)
                }
                response.observeForever { resource ->
                    _registerStatus.value = resource
                }
            } catch (e: Exception) {
                _registerStatus.value = Resource.error(null, e.message, null, null, null)
            }
        }
    }
    fun login(loginReq: LoginReq) {
        viewModelScope.launch {
            _loginStatus.value = Resource.loading(null)
            try {
                val response = safeApiCall(Dispatchers.IO) {
                    apiService.login(loginReq)
                }
                response.observeForever { resource ->
                    _loginStatus.value = resource
                }
            } catch (e: Exception) {
                _loginStatus.value = Resource.error(null, e.message, null, null, null)
            }
        }
    }
//    fun register(signUpReq: SignUpReq): MutableLiveData<Resource<ApiResponse<SignUpRes>>> {
//        viewModelScope.launch {
//            register = safeApiCall(Dispatchers.IO) {
//                apiService.register(signUpReq)
//            } as MutableLiveData<Resource<ApiResponse<SignUpRes>>>
//        }
//        return register
//    }

    //endregion

    //region Slider
    fun getAllSlider(): MutableLiveData<Resource<ApiResponse<List<SliderRes>>>> {
        viewModelScope.launch {
            allSlider = safeApiCall(Dispatchers.IO) {
                apiService.getAllSlider()
            } as MutableLiveData<Resource<ApiResponse<List<SliderRes>>>>
        }
        return allSlider
    }

    fun getSliderById(id: Long): MutableLiveData<Resource<ApiResponse<SliderRes>>> {
        viewModelScope.launch {
            sliderById = safeApiCall(Dispatchers.IO) {
                apiService.getSliderById(id)
            } as MutableLiveData<Resource<ApiResponse<SliderRes>>>
        }
        return sliderById
    }
    //endregion

    //region Product
    fun getAllProduct(
        pageIndex: Int,
        pageSize: Int
    ): MutableLiveData<Resource<ApiResponse<List<ProductRes>>>> {
        viewModelScope.launch {
            allProduct = safeApiCall(Dispatchers.IO) {
                apiService.getAllProduct(pageIndex = pageIndex, pageSize = pageSize)
            } as MutableLiveData<Resource<ApiResponse<List<ProductRes>>>>
        }
        return allProduct
    }

    fun getProductById(id: Int): MutableLiveData<Resource<ApiResponse<ProductRes>>> {
        viewModelScope.launch {
            productById = safeApiCall(Dispatchers.IO) {
                apiService.getProductById(id)
            } as MutableLiveData<Resource<ApiResponse<ProductRes>>>
        }
        return productById
    }
    //endregion

    //region Category

    fun getAllCategory(): MutableLiveData<Resource<ApiResponse<List<CategoryRes>>>> {
        viewModelScope.launch {
            allCategory = safeApiCall(Dispatchers.IO) {
                apiService.getAllCategory()
            } as MutableLiveData<Resource<ApiResponse<List<CategoryRes>>>>
        }
        return allCategory
    }


    //endregion
    fun toggleAddToFavorites(productId: Int) {
        Timber.d("toggleAddToFavorites called for productId: $productId")
        viewModelScope.launch {
            allProduct.value?.data?.data?.map { product ->
                if (product.id == productId) {
                    allProduct.value?.data?.data?.map {
                        it.isAddToFavorites = !product.isAddToFavorites
                        allProduct.postValue(allProduct.value)
                    }
                    Timber.d("toggleAddToFavorites called for productId::::updatedProduct ${product.isAddToFavorites}")
                }
            }
            val product = allProduct.value?.data?.data?.find { it.id == productId }
            product?.let {
                if (product.isAddToFavorites) {
                    Timber.d("insertToFavorite:$product")
                    favoritesDao.insertToFavorite(product)
                } else {
                    Timber.d("deleteFromFavorite:$product")
                    favoritesDao.deleteFromFavorite(product)
                }
            }
        }

//    val products: MutableStateFlow<List<ProductRes>> = MutableStateFlow(emptyList())
//    val allFavoriteProducts = favoritesDao.getAllFavoriteProducts()
//
//    init {
//        products.value = testList
//        viewModelScope.launch {
//            allFavoriteProducts.collect {pro->
//                pro.forEach {pro1->
//                    products.value.forEach{pro2->
//                        if(pro1.id==pro2.id){
//                            pro2.isAddToFavorites=true
//                        }
//                    }
//                }
//            }
//        }
//    }
//
//    fun toggleAddToFavorites(productId: Int) {
//        Timber.d("toggleAddToFavorites called for productId: $productId")
//        viewModelScope.launch {
//            val updatedList = products.value.map { product ->
//                if (product.id == productId) {
//                    val updatedProduct = product.copy(isAddToFavorites = !product.isAddToFavorites)
//                    Timber.d("toggleAddToFavorites called for productId::::updatedProduct $updatedProduct")
//                    updatedProduct
//                } else {
//                    product
//                }
//            }
//
//            updatedList.let { products.value = it }
//
//            val product = products.value.find { it.id == productId }
//
//            product?.let {
//                if (product.isAddToFavorites) {
//                    Timber.d("deleteFromFavorite:$product")
//                    favoritesDao.insertToFavorite(product)
//                } else {
//                    Timber.d("insertToFavorite:$product")
//                    favoritesDao.deleteFromFavorite(product)
//
//                }
//            }
//
//
//        }
//    }
    }


}