package com.technolearn.rasoulonlineshop.vm

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.technolearn.rasoulonlineshop.api.ApiService
import com.technolearn.rasoulonlineshop.util.safeApiCall
import com.technolearn.rasoulonlineshop.vo.enums.Status
import com.technolearn.rasoulonlineshop.vo.generics.ApiResponse
import com.technolearn.rasoulonlineshop.vo.generics.Resource
import com.technolearn.rasoulonlineshop.vo.res.SliderRes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShopViewModel @Inject constructor(
    private val application: Application,
    private val apiService: ApiService
) : ViewModel() {

    private var sliderRes: MutableLiveData<Resource<ApiResponse<List<SliderRes>>>> =
        MutableLiveData()

    fun getSlider(): MutableLiveData<Resource<ApiResponse<List<SliderRes>>>> {
        viewModelScope.launch {
            sliderRes = safeApiCall(Dispatchers.IO) {
                apiService.getAllSlider()
            } as MutableLiveData<Resource<ApiResponse<List<SliderRes>>>>
        }
        return sliderRes
    }
}