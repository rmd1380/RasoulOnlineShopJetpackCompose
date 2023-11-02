package com.technolearn.rasoulonlineshop.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.technolearn.rasoulonlineshop.db.converters.Converters
import com.technolearn.rasoulonlineshop.db.dao.FavoritesDao
import com.technolearn.rasoulonlineshop.vo.res.ProductRes

@Database(entities = [ProductRes::class], version = 1 , exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoritesDao(): FavoritesDao
}
