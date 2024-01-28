package com.technolearn.rasoulonlineshop.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.technolearn.rasoulonlineshop.vo.entity.UserCreditCardEntity

@Dao
interface UserCreditCardDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserCreditCard(userCreditCardEntity: UserCreditCardEntity)
    @Update
    suspend fun updateUserCreditCard(userCreditCardEntity: UserCreditCardEntity)
    @Delete
    suspend fun deleteFromUserCreditCard(userCreditCardEntity: UserCreditCardEntity)
    @Query("SELECT * FROM user_credit_card")
    fun getAllUserCreditCard():LiveData<List<UserCreditCardEntity>>

    @Query("UPDATE user_credit_card SET isCardSelected = 0 WHERE id != :creditCardId")
    suspend fun clearSelectedCreditCardExcept(creditCardId:Long)

    @Query("SELECT * FROM user_credit_card WHERE isCardSelected==1 LIMIT 1")
    fun getSelectedUserCreditCard(): LiveData<UserCreditCardEntity>
}