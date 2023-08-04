package com.technolearn.rasoulonlineshop.api

import com.technolearn.rasoulonlineshop.vo.generics.ApiResponse
import com.technolearn.rasoulonlineshop.vo.res.SliderRes
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("api/slider")
    suspend fun getAllSlider(
    ): Response<ApiResponse<List<SliderRes>>>

}