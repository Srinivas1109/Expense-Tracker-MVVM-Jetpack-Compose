package com.srinivas.expensetracker.data.entities

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity
data class Budget(
    @PrimaryKey(autoGenerate = true)
    val budgetId: Int = 0,
    val budgetName: String,
    val amount: Double,
    val createdOn: Long = System.currentTimeMillis(),
    val lastModifiedOn: Long = System.currentTimeMillis()
)
