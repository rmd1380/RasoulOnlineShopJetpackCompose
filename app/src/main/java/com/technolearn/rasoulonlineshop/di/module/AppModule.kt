package com.technolearn.rasoulonlineshop.di.module

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.compose.runtime.currentComposer
import androidx.room.Room
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.google.gson.GsonBuilder
import com.technolearn.rasoulonlineshop.api.ApiService
import com.technolearn.rasoulonlineshop.db.AppDatabase
import com.technolearn.rasoulonlineshop.db.dao.FavoritesDao
import com.technolearn.rasoulonlineshop.db.dao.UserAddressDao
import com.technolearn.rasoulonlineshop.db.dao.UserCartDao
import com.technolearn.rasoulonlineshop.db.dao.UserCreditCardDao
import com.technolearn.rasoulonlineshop.db.dao.UserLoginDao
import com.technolearn.rasoulonlineshop.di.UnsafeSSLConfig
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
//    @Provides
//    @Singleton
//    fun providesOkHttpClient(@ApplicationContext context: Context): OkHttpClient =
//        OkHttpClient.Builder()
//            .addInterceptor(ChuckerInterceptor(context))
//            .build()
    @Provides
    @Singleton
    fun provideOkHttpClient(
        sharedPreferences: SharedPreferences, @ApplicationContext appContext: Context
    ): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        val chuckerInterceptor = ChuckerInterceptor(appContext)
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        val builder = OkHttpClient().newBuilder()
            .addInterceptor(logging)
            .addInterceptor(chuckerInterceptor)
            .callTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)

        return builder.build()
    }

    @Provides
    @Singleton
    fun provideApiService(okHttpClient: OkHttpClient): ApiService {

        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.Base.MAIN_URL)
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder().setLenient().create()
                )
            )
            .client(UnsafeSSLConfig.unsafeOkHttpClient.build())
            .build()
        return retrofit.create(ApiService::class.java)
    }
//    @Provides
//    @Singleton
//    fun provideApiService(okHttpClient: OkHttpClient): ApiService = Retrofit.Builder()
//        .baseUrl(Constants.Base.MAIN_URL)
//        .addConverterFactory( GsonConverterFactory.create(
//            GsonBuilder().setLenient().create()
//        ))
//        .client(okHttpClient)
//        .client(UnsafeSSLConfig.unsafeOkHttpClient.build())
//        .build()
//        .create(ApiService::class.java)
    @Provides
    @Singleton
    fun provideAppDB(application: Application): AppDatabase {
        return Room.databaseBuilder(
            application, AppDatabase::class.java, "rasoul_online_shop_db"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideFavoriteDao(appDatabase: AppDatabase): FavoritesDao {
        return appDatabase.favoritesDao()
    }

    @Provides
    @Singleton
    fun provideUserCartDao(appDatabase: AppDatabase): UserCartDao {
        return appDatabase.userCartDao()
    }
    @Provides
    @Singleton
    fun provideUserLoginDao(appDatabase: AppDatabase): UserLoginDao {
        return appDatabase.userLoginDao()
    }

    @Provides
    @Singleton
    fun provideUserAddressDao(appDatabase: AppDatabase): UserAddressDao {
        return appDatabase.userAddressDao()
    }
    @Provides
    @Singleton
    fun provideUserCreditCardDao(appDatabase: AppDatabase): UserCreditCardDao {
        return appDatabase.userCreditCardDao()
    }

}