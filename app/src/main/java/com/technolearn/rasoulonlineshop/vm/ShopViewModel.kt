package com.technolearn.rasoulonlineshop.vm

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.technolearn.rasoulonlineshop.api.ApiService
import com.technolearn.rasoulonlineshop.db.dao.FavoritesDao
import com.technolearn.rasoulonlineshop.mapper.toFavoriteEntity
import com.technolearn.rasoulonlineshop.util.Extensions.orFalse
import com.technolearn.rasoulonlineshop.util.safeApiCall
import com.technolearn.rasoulonlineshop.vo.entity.FavoriteEntity
import com.technolearn.rasoulonlineshop.vo.generics.ApiResponse
import com.technolearn.rasoulonlineshop.vo.generics.Resource
import com.technolearn.rasoulonlineshop.vo.req.LoginReq
import com.technolearn.rasoulonlineshop.vo.req.SignUpReq
import com.technolearn.rasoulonlineshop.vo.res.CategoryRes
import com.technolearn.rasoulonlineshop.vo.res.ColorRes
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


    private val _registerStatus = MutableLiveData<Resource<ApiResponse<SignUpRes>>>()
    val registerStatus: LiveData<Resource<ApiResponse<SignUpRes>>> = _registerStatus
    private val _loginStatus = MutableLiveData<Resource<ApiResponse<LoginRes>>>()
    val loginStatus: LiveData<Resource<ApiResponse<LoginRes>>> = _loginStatus

    private val _allSlider = MutableLiveData<Resource<ApiResponse<List<SliderRes>>>>()
    val allSlider: LiveData<Resource<ApiResponse<List<SliderRes>>>> = _allSlider
    private val _sliderById = MutableLiveData<Resource<ApiResponse<SliderRes>>>()
    val sliderById: LiveData<Resource<ApiResponse<SliderRes>>> = _sliderById

    private val _allProduct = MutableLiveData<Resource<ApiResponse<List<ProductRes>>>>()
    val allProduct: LiveData<Resource<ApiResponse<List<ProductRes>>>> = _allProduct
    private val _productById = MutableLiveData<Resource<ApiResponse<ProductRes>>>()
    val productById: LiveData<Resource<ApiResponse<ProductRes>>> = _productById


    private val _allCategory = MutableLiveData<Resource<ApiResponse<List<CategoryRes>>>>()
    val allCategory: LiveData<Resource<ApiResponse<List<CategoryRes>>>> = _allCategory

    private val _allColor = MutableLiveData<Resource<ApiResponse<List<ColorRes>>>>()
    val allColor: LiveData<Resource<ApiResponse<List<ColorRes>>>> = _allColor
    private val _colorById = MutableLiveData<Resource<ApiResponse<ColorRes>>>()
    val colorById: LiveData<Resource<ApiResponse<ColorRes>>> = _colorById

    private val allFavoriteProducts = favoritesDao.getAllFavoriteProducts()

    fun getAllFavorite(): LiveData<List<FavoriteEntity>> {
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
    //endregion

    //region Slider
    fun fetchAllSlider() {
        viewModelScope.launch {
            _allSlider.value = Resource.loading(null)
            try {
                val result = safeApiCall(Dispatchers.IO) {
                    apiService.getAllSlider()
                }
                result.observeForever { resource ->
                    _allSlider.value = resource
                }
            } catch (e: Exception) {
                _allSlider.value = Resource.error(null, e.message, null, null, null)
            }
        }
    }

    fun fetchSliderById(id: Long) {
        viewModelScope.launch {
            _sliderById.value = Resource.loading(null)
            try {
                val result = safeApiCall(Dispatchers.IO) {
                    apiService.getSliderById(id)
                }
                result.observeForever { resource ->
                    _sliderById.value = resource
                }
            } catch (e: Exception) {
                _sliderById.value = Resource.error(null, e.message, null, null, null)
            }
        }
    }
    //endregion

    //region Product
    fun fetchAllProduct(
        pageIndex: Int,
        pageSize: Int
    ) {
        viewModelScope.launch {
            _allProduct.value = Resource.loading(null)
            try {
                val result = safeApiCall(Dispatchers.IO) {
                    apiService.getAllProduct(pageIndex = pageIndex, pageSize = pageSize)
                }
                result.observeForever { resource ->
                    _allProduct.value = resource
                    updateIsAddToFavorites(_allProduct.value?.data?.data)
                }
            } catch (e: Exception) {
                _allProduct.value = Resource.error(null, e.message, null, null, null)
            }
        }
    }

    fun fetchProductById(id: Int) {
        viewModelScope.launch {
            _productById.value = Resource.loading(null)
            try {
                val result = safeApiCall(Dispatchers.IO) {
                    apiService.getProductById(id)
                }
                result.observeForever { resource ->
                    _productById.value = resource
                }
            } catch (e: Exception) {
                _productById.value = Resource.error(null, e.message, null, null, null)
            }
        }
    }
    //endregion

    //region Category
    fun fetchAllCategory(
    ) {
        viewModelScope.launch {
            _allCategory.value = Resource.loading(null)
            try {
                val result = safeApiCall(Dispatchers.IO) {
                    apiService.getAllCategory()
                }
                result.observeForever { resource ->
                    _allCategory.value = resource
                }
            } catch (e: Exception) {
                _allCategory.value = Resource.error(null, e.message, null, null, null)
            }
        }
    }

    //endregion

    //region Color

    fun fetchAllColor() {
        viewModelScope.launch {
            _allColor.value = Resource.loading(null)
            try {
                val result = safeApiCall(Dispatchers.IO) {
                    apiService.getAllColor()
                }
                result.observeForever { resource ->
                    _allColor.value = resource
                }
            } catch (e: Exception) {
                _allColor.value = Resource.error(null, e.message, null, null, null)
            }
        }
    }

    fun fetchColorById(id: Long) {
        viewModelScope.launch {
            _colorById.value = Resource.loading(null)
            try {
                val result = safeApiCall(Dispatchers.IO) {
                    apiService.getColorById(id)
                }
                result.observeForever { resource ->
                    _colorById.value = resource
                }
            } catch (e: Exception) {
                _colorById.value = Resource.error(null, e.message, null, null, null)
            }
        }
    }

    //endregion
    fun toggleAddToFavorites(productId: Int) {
        val product = _allProduct.value?.data?.data?.find { it.id == productId }
        viewModelScope.launch {
            if (product?.isAddToFavorites.orFalse()) {
                Timber.d("deleteFromFavorite:$product")
                favoritesDao.deleteFromFavorite(toFavoriteEntity(product ?: ProductRes()))
            } else {
                Timber.d("insertToFavorite:$product")
                favoritesDao.insertToFavorite(toFavoriteEntity(product ?: ProductRes()))
            }
        }
    }
    private fun updateIsAddToFavorites(productList: List<ProductRes>?) {
        allFavoriteProducts.observeForever { favoriteProducts ->
            productList?.let {
                for (product in it) {
                    product.isAddToFavorites =
                        favoriteProducts.any { favoriteProduct -> favoriteProduct.id == product.id }
                }
            }
        }
    }
}