package com.technolearn.rasoulonlineshop.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.technolearn.rasoulonlineshop.vo.entity.FavoriteEntity

@Dao
interface FavoritesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertToFavorite(favoritesEntity: FavoriteEntity)
    @Update
    suspend fun updateFavorite(favoritesEntity: FavoriteEntity)
    @Delete
    suspend fun deleteFromFavorite(favoritesEntity: FavoriteEntity)
    @Query("SELECT * FROM favorites")
    fun getAllFavoriteProducts(): LiveData<List<FavoriteEntity>>
}