package com.technolearn.rasoulonlineshop.di.module

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.google.gson.GsonBuilder
import com.technolearn.rasoulonlineshop.BuildConfig
import com.technolearn.rasoulonlineshop.api.ApiService
import com.technolearn.rasoulonlineshop.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideSharedPreferences(
        application: Application
    ): SharedPreferences {
        return application.getSharedPreferences(
            Constants.Prefs.SHARED_PREFS_NAME, Context.MODE_PRIVATE
        )
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        sharedPreferences: SharedPreferences, @ApplicationContext appContext: Context
    ): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        val builder = OkHttpClient().newBuilder()
            .addInterceptor(logging)
            .callTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)

        if (BuildConfig.DEBUG) {
            builder.addInterceptor(ChuckerInterceptor(appContext))
        }

        return builder.build()
    }

    @Provides
    @Singleton
    fun provideApiService(
        okHttpClient: OkHttpClient, sharedPreferences: SharedPreferences
    ): ApiService {

        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.Base.MAIN_URL)
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder().setLenient().create()
                )
            ).client(okHttpClient).build()

        return retrofit.create(ApiService::class.java)
    }

}