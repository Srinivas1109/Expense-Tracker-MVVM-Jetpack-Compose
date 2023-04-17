package com.srinivas.expensetracker.data.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.srinivas.expensetracker.data.entities.Budget
import com.srinivas.expensetracker.data.entities.Expense
import com.srinivas.expensetracker.data.entities.ExpenseCategory

data class ExpenseWithBudgetAndCategory(
    @Embedded val expense: Expense,
    @Relation(parentColumn = "budgetId", entityColumn = "budgetId")
    val budget: Budget,
    @Relation(parentColumn = "expenseCategory", entityColumn = "categoryName")
    val expenseCategory: ExpenseCategory
)
