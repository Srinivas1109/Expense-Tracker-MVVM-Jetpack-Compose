package com.srinivas.expensetracker.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity
data class ExpenseCategory(
    @PrimaryKey(autoGenerate = false)
    val categoryName: String,
    val createdOn: Long = System.currentTimeMillis()
)
