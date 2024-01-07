package com.technolearn.rasoulonlineshop.vo.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_credit_card")
data class UserCreditCardEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val cardName: String,
    val cardNumber: String,
    val cardExpirationDate: String,
    val cvv2: String,
    var isCardSelected:Boolean=false
)

