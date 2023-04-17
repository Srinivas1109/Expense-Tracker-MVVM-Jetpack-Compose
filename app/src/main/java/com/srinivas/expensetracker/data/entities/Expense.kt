package com.srinivas.expensetracker.data.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.Date

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = Budget::class,
            parentColumns = ["budgetId"],
            childColumns = ["budgetId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = ExpenseCategory::class,
            parentColumns = ["categoryName"],
            childColumns = ["expenseCategory"],
            onDelete = ForeignKey.CASCADE
        ),
    ]
)
data class Expense(
    @PrimaryKey(autoGenerate = true)
    val expenseId: Int = 0,
    val budgetId: Int,
    val expenseCategory: String,
    val expenseAmount: Double,
    val createdOn: Long = System.currentTimeMillis(),
    val lastModifiedOn: Long = System.currentTimeMillis()
)
