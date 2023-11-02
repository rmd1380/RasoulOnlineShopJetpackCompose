package com.technolearn.rasoulonlineshop.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.technolearn.rasoulonlineshop.vo.res.ProductRes
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

@Dao
interface FavoritesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertToFavorite(favoritesEntity: ProductRes)
    @Update
    suspend fun updateFavorite(favoritesEntity: ProductRes)
    @Delete
    suspend fun deleteFromFavorite(favoritesEntity: ProductRes)
    @Query("SELECT * FROM favorites")
    fun getAllProducts(): Flow<List<ProductRes>>

    @Query("SELECT * FROM favorites WHERE isAddToFavorites = 1 ")
    fun getAllFavoriteProducts(): LiveData<List<ProductRes>>

}