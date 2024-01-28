package com.technolearn.rasoulonlineshop.vm

import android.app.Application
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
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
import com.technolearn.rasoulonlineshop.vo.enums.SearchWidgetState
import com.technolearn.rasoulonlineshop.vo.generics.ApiResponse
import com.technolearn.rasoulonlineshop.vo.generics.Resource
import com.technolearn.rasoulonlineshop.vo.req.InvoiceReq
import com.technolearn.rasoulonlineshop.vo.req.LoginReq
import com.technolearn.rasoulonlineshop.vo.req.SignUpReq
import com.technolearn.rasoulonlineshop.vo.req.UpdatePasswordReq
import com.technolearn.rasoulonlineshop.vo.req.UpdateReq
import com.technolearn.rasoulonlineshop.vo.res.CategoryRes
import com.technolearn.rasoulonlineshop.vo.res.InvoiceRes
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
    val registerStatus: LiveData<Resource<ApiResponse<SignUpRes>>?> = _registerStatus
    private val _loginStatus = MutableLiveData<Resource<ApiResponse<LoginRes>>?>()
    val loginStatus: LiveData<Resource<ApiResponse<LoginRes>>?> = _loginStatus
    private val _updateUserStatus = MutableLiveData<Resource<ApiResponse<UpdateRes>>?>()
    val updateUserStatus: LiveData<Resource<ApiResponse<UpdateRes>>?> = _updateUserStatus
    private val _updateUserPasswordStatus = MutableLiveData<Resource<ApiResponse<UpdateRes>>?>()
    val updateUserPasswordStatus: LiveData<Resource<ApiResponse<UpdateRes>>?> =
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

    //region Invoice
    private val _invoiceStatus = MutableLiveData<Resource<ApiResponse<InvoiceRes>>?>()
    val invoiceStatus: LiveData<Resource<ApiResponse<InvoiceRes>>?> = _invoiceStatus
    private val _invoiceByUserId = MutableLiveData<Resource<ApiResponse<List<InvoiceRes>>>>()
    val invoiceByUserId: LiveData<Resource<ApiResponse<List<InvoiceRes>>>> = _invoiceByUserId
    //endregion
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

    fun resetInvoiceStatus() {
        _invoiceStatus.value = null
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

    //region Invoice
    fun addInvoice(token: String, status: Int, invoiceReq: InvoiceReq) {
        viewModelScope.launch {
            _invoiceStatus.value = Resource.loading(null)
            try {
                val response = safeApiCall(Dispatchers.IO) {
                    apiService.addInvoice(
                        invoiceReq = invoiceReq,
                        status = status,
                        token = prepareToken(token)
                    )
                }
                response.observeForever { resource ->
                    _invoiceStatus.value = resource
                }
            } catch (e: Exception) {
                _invoiceStatus.value = Resource.error(null, e.message, null, null, null)
            }
        }
    }

    fun fetchInvoiceByUserId(
        userId: Long,
        token: String
    ) {
        viewModelScope.launch {
            _invoiceByUserId.value = Resource.loading(null)
            try {
                val result = safeApiCall(Dispatchers.IO) {
                    apiService.getAllInvoiceByUserId(
                        id = userId,
                        token = prepareToken(token)
                    )
                }
                result.observeForever { resource ->
                    _invoiceByUserId.value = resource
                }
            } catch (e: Exception) {
                _invoiceByUserId.value = Resource.error(null, e.message, null, null, null)
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

    // region Cart
    fun addToCart(userCartEntity: UserCartEntity) {
        viewModelScope.launch {
            userCartDao.insertUserCart(userCartEntity)
            Timber.d("addToCartViewModel:::::$userCartEntity")
        }
    }

    fun deleteFromCart(userCartEntity: UserCartEntity) {
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

    fun deleteAllUserCart() {
        viewModelScope.launch {
            userCartDao.deleteAll()
        }
    }
    // endregion

    // region User
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

    //endregion

    //region UserAddress
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

    fun clearSelectedAddressesExcept(id: Long) {
        viewModelScope.launch {
            userAddressDao.clearSelectedAddressesExcept(id)
        }
    }

    fun getUserAddressById(id:Int): LiveData<UserAddressEntity> {
        return userAddressDao.getUserAddressById(id)
    }
    //endregion

    //region UserCreditCard
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

    fun clearSelectedCreditCardExcept(id: Long) {
        viewModelScope.launch {
            userCreditCardDao.clearSelectedCreditCardExcept(id)
        }
    }

    fun getSelectedUserCreditCard(): LiveData<UserCreditCardEntity> {
        return userCreditCardDao.getSelectedUserCreditCard()
    }
    //endregion


    //region Search

    private val _searchWidgetState: MutableState<SearchWidgetState> =
        mutableStateOf(value = SearchWidgetState.CLOSED)
    val searchWidgetState: State<SearchWidgetState> = _searchWidgetState

    private val _searchTextState: MutableState<String> =
        mutableStateOf(value = "")
    val searchTextState: State<String> = _searchTextState

    fun updateSearchWidgetState(newValue: SearchWidgetState) {
        _searchWidgetState.value = newValue
    }

    fun updateSearchTextState(newValue: String) {
        _searchTextState.value = newValue
    }

    fun resetTextAndState(){
        _searchWidgetState.value = SearchWidgetState.CLOSED
        _searchTextState.value = ""
    }
    //endregion
}