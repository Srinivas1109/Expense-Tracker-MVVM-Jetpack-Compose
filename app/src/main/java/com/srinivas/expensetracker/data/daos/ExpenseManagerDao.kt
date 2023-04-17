package com.srinivas.expensetracker.data.daos

import androidx.room.*
import com.srinivas.expensetracker.data.entities.Budget
import com.srinivas.expensetracker.data.entities.Expense
import com.srinivas.expensetracker.data.entities.ExpenseCategory
import com.srinivas.expensetracker.data.relations.ExpenseWithBudgetAndCategory
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpenseManagerDao {
    // for expense table
    @Insert
    suspend fun addExpense(expense: Expense)

    @Query("delete from expense where expenseId = :expenseId")
    suspend fun deleteExpense(expenseId: Int)

    @Transaction
    @Query("select * from expense order by createdOn desc")
    fun getAllExpenses() : Flow<List<ExpenseWithBudgetAndCategory>>

    @Transaction
    @Query("select * from expense where budgetId = :id order by createdOn desc")
    fun getExpenseByBudgetId(id: Int) : Flow<List<ExpenseWithBudgetAndCategory>>

    // for category table
    @Insert
    suspend fun addCategory(expenseCategory: ExpenseCategory)

    @Query("SELECT * FROM ExpenseCategory order by createdOn desc")
    fun getAllCategories() : Flow<List<ExpenseCategory>>

    // for budget table
    @Insert
    suspend fun addBudget(budget: Budget)

    @Query("select * from budget order by createdOn desc")
    fun getAllBudgets() : Flow<List<Budget>>

    @Update
    suspend fun updateBudget(budget: Budget)

    @Query("select * from budget where budgetId = :budgetId")
    suspend fun getBudgetById(budgetId: Int) : Budget

    @Delete
    suspend fun deleteBudget(budget: Budget)
}