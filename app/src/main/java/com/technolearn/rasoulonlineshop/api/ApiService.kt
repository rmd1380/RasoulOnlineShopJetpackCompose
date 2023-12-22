package com.technolearn.rasoulonlineshop.api

import com.technolearn.rasoulonlineshop.vo.generics.ApiResponse
import com.technolearn.rasoulonlineshop.vo.req.LoginReq
import com.technolearn.rasoulonlineshop.vo.req.SignUpReq
import com.technolearn.rasoulonlineshop.vo.res.CategoryRes
import com.technolearn.rasoulonlineshop.vo.res.ColorRes
import com.technolearn.rasoulonlineshop.vo.res.LoginRes
import com.technolearn.rasoulonlineshop.vo.res.ProductRes
import com.technolearn.rasoulonlineshop.vo.res.SignUpRes
import com.technolearn.rasoulonlineshop.vo.res.SliderRes
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    //region SignUp
    @POST("api/user/register")
    suspend fun register(
        @Body signUpReq: SignUpReq
    ): Response<ApiResponse<SignUpRes>>

    @POST("api/user/login")
    suspend fun login(
        @Body loginReq: LoginReq
    ): Response<ApiResponse<LoginRes>>

    //endregion

    //region Sliders
    @GET("api/slider")
    suspend fun getAllSlider(
    ): Response<ApiResponse<List<SliderRes>>>


    @GET("api/slider/{id}")
    suspend fun getSliderById(@Path("id") id: Long): Response<ApiResponse<SliderRes>>

    //endregion

    //region Products
    @GET("api/product")
    suspend fun getAllProduct(
        @Query("pageIndex") pageIndex: Int,
        @Query("pageSize") pageSize: Int
    ): Response<ApiResponse<List<ProductRes>>>

    @GET("api/product/cat/{id}")
    suspend fun getProductByCategoryId(
        @Path("id") id: Int,
        @Query("pageIndex") pageIndex: Int,
        @Query("pageSize") pageSize: Int
    ): Response<ApiResponse<List<ProductRes>>>

    @GET("api/product/{id}")
    suspend fun getProductById(
        @Path("id") id: Int,
    ): Response<ApiResponse<ProductRes>>

    //endregion

    //region Category
    @GET("api/category")
    suspend fun getAllCategory(
    ): Response<ApiResponse<List<CategoryRes>>>

    //endregion

    //region Color
    @GET("api/color")
    suspend fun getAllColor(
    ): Response<ApiResponse<List<ColorRes>>>

    @GET("api/color/{id}")
    suspend fun getColorById(
        @Path("id") id: Long
    ): Response<ApiResponse<ColorRes>>

    //endregion

}