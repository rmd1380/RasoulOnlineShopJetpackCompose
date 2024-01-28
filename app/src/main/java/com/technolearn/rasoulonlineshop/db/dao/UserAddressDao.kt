package com.technolearn.rasoulonlineshop.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.technolearn.rasoulonlineshop.vo.entity.UserAddressEntity
import com.technolearn.rasoulonlineshop.vo.entity.UserCartEntity

@Dao
interface UserAddressDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserAddress(userAddressEntity: UserAddressEntity)
    @Update
    suspend fun updateUserAddress(userAddressEntity: UserAddressEntity)
    @Delete
    suspend fun deleteFromUserAddress(userAddressEntity: UserAddressEntity)
    @Query("SELECT * FROM user_Address")
    fun getAllUserAddress():LiveData<List<UserAddressEntity>>

    @Query("UPDATE user_Address SET isAddressSelected = 0 WHERE id != :addressId")
    suspend fun clearSelectedAddressesExcept(addressId:Long)

    @Query("SELECT * FROM user_Address WHERE id = :addressId LIMIT 1")
    fun getUserAddressById(addressId: Int): LiveData<UserAddressEntity>


}