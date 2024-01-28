package com.technolearn.rasoulonlineshop.api

import com.technolearn.rasoulonlineshop.vo.generics.ApiResponse
import com.technolearn.rasoulonlineshop.vo.req.InvoiceReq
import com.technolearn.rasoulonlineshop.vo.req.LoginReq
import com.technolearn.rasoulonlineshop.vo.req.SignUpReq
import com.technolearn.rasoulonlineshop.vo.req.UpdatePasswordReq
import com.technolearn.rasoulonlineshop.vo.req.UpdateReq
import com.technolearn.rasoulonlineshop.vo.res.CategoryRes
import com.technolearn.rasoulonlineshop.vo.res.ColorRes
import com.technolearn.rasoulonlineshop.vo.res.InvoiceRes
import com.technolearn.rasoulonlineshop.vo.res.LoginRes
import com.technolearn.rasoulonlineshop.vo.res.ProductRes
import com.technolearn.rasoulonlineshop.vo.res.SignUpRes
import com.technolearn.rasoulonlineshop.vo.res.SliderRes
import com.technolearn.rasoulonlineshop.vo.res.UpdateRes
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
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

    @PUT("api/user/update")
    suspend fun updateUser(
        @Body updateReq: UpdateReq,
        @Header("Authorization") token: String
    ): Response<ApiResponse<UpdateRes>>

    @PUT("api/user/changePassword")
    suspend fun updateUserPassword(
        @Body updatePasswordReq: UpdatePasswordReq,
        @Header("Authorization") token: String
    ): Response<ApiResponse<UpdateRes>>

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

    //region Invoice
    @POST("api/invoice/{status}")
    suspend fun addInvoice(
        @Path("status") status:Int,
        @Body invoiceReq: InvoiceReq,
        @Header("Authorization") token: String
    ): Response<ApiResponse<InvoiceRes>>

    @GET("api/invoice/user/{userId}")
    suspend fun getAllInvoiceByUserId(
        @Path("userId") id: Long,
        @Header("Authorization") token: String
    ): Response<ApiResponse<List<InvoiceRes>>>

    //endregion

}