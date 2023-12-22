package com.technolearn.rasoulonlineshop.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.technolearn.rasoulonlineshop.db.converters.Converters
import com.technolearn.rasoulonlineshop.db.dao.FavoritesDao
import com.technolearn.rasoulonlineshop.db.dao.UserCartDao
import com.technolearn.rasoulonlineshop.vo.entity.FavoriteEntity
import com.technolearn.rasoulonlineshop.vo.entity.UserCartEntity

@Database(
    entities = [FavoriteEntity::class, UserCartEntity::class],
    version = 4,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoritesDao(): FavoritesDao
    abstract fun userCartDao(): UserCartDao
}
