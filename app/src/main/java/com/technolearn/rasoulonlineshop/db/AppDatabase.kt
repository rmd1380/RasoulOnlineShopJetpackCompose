package com.technolearn.rasoulonlineshop.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.technolearn.rasoulonlineshop.db.converters.Converters
import com.technolearn.rasoulonlineshop.db.dao.FavoritesDao
import com.technolearn.rasoulonlineshop.db.dao.UserAddressDao
import com.technolearn.rasoulonlineshop.db.dao.UserCartDao
import com.technolearn.rasoulonlineshop.db.dao.UserCreditCardDao
import com.technolearn.rasoulonlineshop.db.dao.UserLoginDao
import com.technolearn.rasoulonlineshop.vo.entity.FavoriteEntity
import com.technolearn.rasoulonlineshop.vo.entity.UserAddressEntity
import com.technolearn.rasoulonlineshop.vo.entity.UserCartEntity
import com.technolearn.rasoulonlineshop.vo.entity.UserCreditCardEntity
import com.technolearn.rasoulonlineshop.vo.entity.UserLoginEntity

@Database(
    entities = [FavoriteEntity::class, UserCartEntity::class, UserLoginEntity::class, UserAddressEntity::class, UserCreditCardEntity::class],
    version = 13,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoritesDao(): FavoritesDao
    abstract fun userCartDao(): UserCartDao
    abstract fun userLoginDao(): UserLoginDao
    abstract fun userAddressDao(): UserAddressDao
    abstract fun userCreditCardDao(): UserCreditCardDao
}
