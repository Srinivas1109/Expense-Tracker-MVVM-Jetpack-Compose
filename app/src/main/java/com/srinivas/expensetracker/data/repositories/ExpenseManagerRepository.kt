package com.srinivas.expensetracker.data.repositories

import com.srinivas.expensetracker.data.entities.Budget
import com.srinivas.expensetracker.data.entities.Expense
import com.srinivas.expensetracker.data.entities.ExpenseCategory
import com.srinivas.expensetracker.data.relations.ExpenseWithBudgetAndCategory
import kotlinx.coroutines.flow.Flow

interface ExpenseManagerRepository {
    suspend fun addExpense(expense: Expense)

    fun getAllExpenses() : Flow<List<ExpenseWithBudgetAndCategory>>

    suspend fun deleteExpense(expenseId: Int)
    // for category table
    suspend fun addCategory(expenseCategory: ExpenseCategory)

    fun getAllCategories() : Flow<List<ExpenseCategory>>

    // for budget table
    suspend fun addBudget(budget: Budget)

    fun getAllBudgets() : Flow<List<Budget>>

    fun getExpenseByBudgetId(id: Int) : Flow<List<ExpenseWithBudgetAndCategory>>

    suspend fun updateBudget(budget: Budget)

    suspend fun getBudgetById(budgetId: Int) : Budget

    suspend fun deleteBudget(budget: Budget)

}