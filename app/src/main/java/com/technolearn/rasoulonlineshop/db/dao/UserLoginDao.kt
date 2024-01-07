package com.technolearn.rasoulonlineshop.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.technolearn.rasoulonlineshop.vo.entity.UserCartEntity
import com.technolearn.rasoulonlineshop.vo.entity.UserLoginEntity

@Dao
interface UserLoginDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(userCartEntity: UserLoginEntity)
    @Update
    suspend fun updateUser(userCartEntity: UserLoginEntity)
    @Delete
    suspend fun deleteFromUser(userCartEntity: UserLoginEntity)
    @Query("SELECT * FROM user_login WHERE isLogin = 1 LIMIT 1")
    fun getLoggedInUser(): LiveData<UserLoginEntity?>
    @Query("SELECT EXISTS (SELECT 1 FROM user_login WHERE isLogin = 1)")
    fun isUserLogin(): LiveData<Boolean?>

}