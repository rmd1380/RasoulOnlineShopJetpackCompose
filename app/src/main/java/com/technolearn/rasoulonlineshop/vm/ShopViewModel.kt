package com.technolearn.rasoulonlineshop.vm

import android.app.Application
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.technolearn.rasoulonlineshop.api.ApiService
import com.technolearn.rasoulonlineshop.db.dao.FavoritesDao
import com.technolearn.rasoulonlineshop.db.dao.UserAddressDao
import com.technolearn.rasoulonlineshop.db.dao.UserCartDao
import com.technolearn.rasoulonlineshop.db.dao.UserCreditCardDao
import com.technolearn.rasoulonlineshop.db.dao.UserLoginDao
import com.technolearn.rasoulonlineshop.mapper.toFavoriteEntity
import com.technolearn.rasoulonlineshop.util.Extensions.orDefault
import com.technolearn.rasoulonlineshop.util.Extensions.orFalse
import com.technolearn.rasoulonlineshop.util.prepareToken
import com.technolearn.rasoulonlineshop.util.safeApiCall
import com.technolearn.rasoulonlineshop.vo.entity.FavoriteEntity
import com.technolearn.rasoulonlineshop.vo.entity.UserAddressEntity
import com.technolearn.rasoulonlineshop.vo.entity.UserCartEntity
import com.technolearn.rasoulonlineshop.vo.entity.UserCreditCardEntity
import com.technolearn.rasoulonlineshop.vo.entity.UserLoginEntity
import com.technolearn.rasoulonlineshop.vo.generics.ApiResponse
import com.technolearn.rasoulonlineshop.vo.generics.Resource
import com.technolearn.rasoulonlineshop.vo.req.LoginReq
import com.technolearn.rasoulonlineshop.vo.req.SignUpReq
import com.technolearn.rasoulonlineshop.vo.req.UpdatePasswordReq
import com.technolearn.rasoulonlineshop.vo.req.UpdateReq
import com.technolearn.rasoulonlineshop.vo.res.CategoryRes
import com.technolearn.rasoulonlineshop.vo.res.LoginRes
import com.technolearn.rasoulonlineshop.vo.res.ProductRes
import com.technolearn.rasoulonlineshop.vo.res.SignUpRes
import com.technolearn.rasoulonlineshop.vo.res.UpdateRes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ShopViewModel @Inject constructor(
    private val application: Application,
    private val apiService: ApiService,
    private val favoritesDao: FavoritesDao,
    private val userCartDao: UserCartDao,
    private val userLoginDao: UserLoginDao,
    private val userAddressDao: UserAddressDao,
    private val userCreditCardDao: UserCreditCardDao
) : ViewModel() {


    //region SignUp
    private val _registerStatus = MutableLiveData<Resource<ApiResponse<SignUpRes>>?>()
    val registerStatus: MutableLiveData<Resource<ApiResponse<SignUpRes>>?> = _registerStatus
    private val _loginStatus = MutableLiveData<Resource<ApiResponse<LoginRes>>?>()
    val loginStatus: MutableLiveData<Resource<ApiResponse<LoginRes>>?> = _loginStatus
    private val _updateUserStatus = MutableLiveData<Resource<ApiResponse<UpdateRes>>?>()
    val updateUserStatus: MutableLiveData<Resource<ApiResponse<UpdateRes>>?> = _updateUserStatus
    private val _updateUserPasswordStatus = MutableLiveData<Resource<ApiResponse<UpdateRes>>?>()
    val updateUserPasswordStatus: MutableLiveData<Resource<ApiResponse<UpdateRes>>?> =
        _updateUserPasswordStatus

    //endregion
    //region Product
    private val _allProduct = MutableLiveData<Resource<ApiResponse<List<ProductRes>>>>()
    val allProduct: LiveData<Resource<ApiResponse<List<ProductRes>>>> = _allProduct
    private val _productByCategoryId = MutableLiveData<Resource<ApiResponse<List<ProductRes>>>>()
    val productByCategoryId: LiveData<Resource<ApiResponse<List<ProductRes>>>> =
        _productByCategoryId
    private val _productById = MutableLiveData<Resource<ApiResponse<ProductRes>>>()
    val productById: LiveData<Resource<ApiResponse<ProductRes>>> = _productById

    //endregion
    private val _allCategory = MutableLiveData<Resource<ApiResponse<List<CategoryRes>>>>()
    val allCategory: LiveData<Resource<ApiResponse<List<CategoryRes>>>> = _allCategory

    val isLoggedIn: LiveData<Boolean?> = userLoginDao.isUserLogin()

    fun getAllFavorite(): LiveData<List<FavoriteEntity>> {
        return favoritesDao.getAllFavoriteProducts()
    }

    fun getAllUserCart(): LiveData<List<UserCartEntity>> {
        return userCartDao.getAllUserCart()
    }


    fun getOneProductInUserCartById(id: Int): LiveData<UserCartEntity?> {
        return userCartDao.getUserCartById(id)
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

    fun updateUser(token: String, updateReq: UpdateReq) {
        viewModelScope.launch {
            _updateUserStatus.value = Resource.loading(null)
            try {
                val response = safeApiCall(Dispatchers.IO) {
                    apiService.updateUser(updateReq, prepareToken(token))
                }
                response.observeForever { resource ->
                    _updateUserStatus.value = resource
                }
            } catch (e: Exception) {
                _updateUserStatus.value = Resource.error(null, e.message, null, null, null)
            }
        }
    }

    fun updateUserPassword(token: String, updatePasswordReq: UpdatePasswordReq) {
        viewModelScope.launch {
            _updateUserPasswordStatus.value = Resource.loading(null)
            try {
                val response = safeApiCall(Dispatchers.IO) {
                    apiService.updateUserPassword(updatePasswordReq, prepareToken(token))
                }
                response.observeForever { resource ->
                    _updateUserPasswordStatus.value = resource
                }
            } catch (e: Exception) {
                _updateUserPasswordStatus.value = Resource.error(null, e.message, null, null, null)
            }
        }
    }

    fun resetRegisterStatus() {
        _registerStatus.value = null
    }

    fun resetLoginStatus() {
        _loginStatus.value = null
    }

    fun resetUpdateUserPasswordStatus() {
        _updateUserPasswordStatus.value = null
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

    fun fetchProductByCategoryId(
        id: Int,
        pageIndex: Int,
        pageSize: Int
    ) {
        viewModelScope.launch {
            _productByCategoryId.value = Resource.loading(null)
            try {
                val result = safeApiCall(Dispatchers.IO) {
                    apiService.getProductByCategoryId(
                        id = id,
                        pageIndex = pageIndex,
                        pageSize = pageSize
                    )
                }
                result.observeForever { resource ->
                    _productByCategoryId.value = resource
                    updateIsAddToFavorites(_productByCategoryId.value?.data?.data)
                }
            } catch (e: Exception) {
                _productByCategoryId.value = Resource.error(null, e.message, null, null, null)
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
                    updateIsAddToCart(_productById.value?.data?.data)
                }
            } catch (e: Exception) {
                _productById.value = Resource.error(null, e.message, null, null, null)
            }
        }
    }
    //endregion

    //region Category
    fun fetchAllCategory() {
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
        favoritesDao.getAllFavoriteProducts().observeForever { favoriteProducts ->
            productList?.let {
                for (product in it) {
                    product.isAddToFavorites =
                        favoriteProducts.any { favoriteProduct -> favoriteProduct.id == product.id }
                }
            }
        }
    }

    private fun updateIsAddToCart(product: ProductRes?) {
        product?.let { productRes ->
            getOneProductInUserCartById(productRes.id.orDefault()).observeForever { userCartProduct ->
                productRes.isAddToCart = userCartProduct?.id == productRes.id
            }
        }
    }

    fun addToCart(userCartEntity: UserCartEntity) {
        viewModelScope.launch {
            userCartDao.insertUserCart(userCartEntity)
            Timber.d("addToCartViewModel:::::$userCartEntity")
        }
    }

    private fun deleteFromCart(userCartEntity: UserCartEntity) {
        viewModelScope.launch {
            userCartDao.deleteFromUserCart(userCartEntity)
        }
    }

    fun incrementQuantity(productId: Int) {
        viewModelScope.launch {
            userCartDao.incrementQuantity(productId)
        }
    }

    fun decrementQuantity(productId: Int) {
        viewModelScope.launch {
            userCartDao.decrementQuantity(productId)
            getAllUserCart().observeForever { resource ->
                val cartList: List<UserCartEntity> = resource
                val cartToUpdate = cartList.find { it.id == productId }
                if (cartToUpdate != null && cartToUpdate.quantity.orDefault() == 0) {
                    deleteFromCart(cartToUpdate)
                }
                Timber.d("cartList::$cartList")
                Timber.d("cartToUpdate::$cartToUpdate")
            }
        }
    }

    fun insertUser(user: UserLoginEntity) {
        viewModelScope.launch {
            userLoginDao.insertUser(user)
            Timber.d("userLoginRes:::::$user")
        }
    }

    fun deleteUser(user: UserLoginEntity) {
        viewModelScope.launch {
            userLoginDao.deleteFromUser(user)
        }
    }

    fun updateUserDB(user: UserLoginEntity) {
        viewModelScope.launch {
            userLoginDao.updateUser(user)
            Timber.d("userLoginRes:::::Updated::$user")
        }
    }

    fun getLoggedInUser(): LiveData<UserLoginEntity?> {
        return userLoginDao.getLoggedInUser()
    }

    fun insertUserAddress(userAddress: UserAddressEntity) {
        viewModelScope.launch {
            userAddressDao.insertUserAddress(userAddress)
        }
    }

    fun deleteUserAddress(userAddress: UserAddressEntity) {
        viewModelScope.launch {
            userAddressDao.deleteFromUserAddress(userAddress)
        }
    }

    fun updateUserAddress(userAddress: UserAddressEntity) {
        viewModelScope.launch {
            userAddressDao.updateUserAddress(userAddress)
        }
    }

    fun getAllUserAddress(): LiveData<List<UserAddressEntity>> {
        return userAddressDao.getAllUserAddress()
    }

    //UserCreditCard
    fun insertUserCreditCard(userCreditCard: UserCreditCardEntity) {
        viewModelScope.launch {
            userCreditCardDao.insertUserCreditCard(userCreditCard)
        }
    }

    fun deleteUserCreditCard(userCreditCard: UserCreditCardEntity) {
        viewModelScope.launch {
            userCreditCardDao.deleteFromUserCreditCard(userCreditCard)
        }
    }

    fun updateUserCreditCard(userCreditCard: UserCreditCardEntity) {
        viewModelScope.launch {
            userCreditCardDao.updateUserCreditCard(userCreditCard)
        }
    }

    fun getAllUserCreditCard(): LiveData<List<UserCreditCardEntity>> {
        return userCreditCardDao.getAllUserCreditCard()
    }
}
