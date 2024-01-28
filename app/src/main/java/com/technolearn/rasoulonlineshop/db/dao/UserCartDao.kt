package com.technolearn.rasoulonlineshop.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.technolearn.rasoulonlineshop.vo.entity.UserCartEntity

@Dao
interface UserCartDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserCart(userCartEntity: UserCartEntity)
    @Update
    suspend fun updateUserCart(userCartEntity: UserCartEntity)
    @Delete
    suspend fun deleteFromUserCart(userCartEntity: UserCartEntity)
    @Query("SELECT * FROM user_cart")
    fun getAllUserCart(): LiveData<List<UserCartEntity>>
    @Query("SELECT * FROM user_cart WHERE id = :productId LIMIT 1")
    fun getUserCartById(productId: Int): LiveData<UserCartEntity?>

    @Query("UPDATE user_cart SET quantity = quantity + 1 WHERE id = :productId")
    suspend fun incrementQuantity(productId: Int)

    @Query("UPDATE user_cart SET quantity = quantity - 1 WHERE id = :productId")
    suspend fun decrementQuantity(productId: Int)

    @Query("DELETE FROM user_cart")
    suspend fun deleteAll()

}