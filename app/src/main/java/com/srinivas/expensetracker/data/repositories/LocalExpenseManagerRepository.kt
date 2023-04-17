package com.srinivas.expensetracker.data.repositories

import com.srinivas.expensetracker.data.daos.ExpenseManagerDao
import com.srinivas.expensetracker.data.entities.Budget
import com.srinivas.expensetracker.data.entities.Expense
import com.srinivas.expensetracker.data.entities.ExpenseCategory
import com.srinivas.expensetracker.data.relations.ExpenseWithBudgetAndCategory
import kotlinx.coroutines.flow.Flow

class LocalExpenseManagerRepository(private val expenseManagerDao: ExpenseManagerDao): ExpenseManagerRepository {
    override suspend fun addExpense(expense: Expense) {
        expenseManagerDao.addExpense(expense)
    }

    override fun getAllExpenses(): Flow<List<ExpenseWithBudgetAndCategory>> {
        return expenseManagerDao.getAllExpenses()
    }

    override suspend fun addCategory(expenseCategory: ExpenseCategory) {
        expenseManagerDao.addCategory(expenseCategory)
    }

    override fun getAllCategories(): Flow<List<ExpenseCategory>> {
        return expenseManagerDao.getAllCategories()
    }

    override suspend fun addBudget(budget: Budget) {
        expenseManagerDao.addBudget(budget)
    }

    override fun getAllBudgets(): Flow<List<Budget>> {
        return expenseManagerDao.getAllBudgets()
    }

    override fun getExpenseByBudgetId(id: Int): Flow<List<ExpenseWithBudgetAndCategory>> {
        return expenseManagerDao.getExpenseByBudgetId(id)
    }

    override suspend fun deleteExpense(expenseId: Int) {
        expenseManagerDao.deleteExpense(expenseId)
    }

    override suspend fun updateBudget(budget: Budget) {
        expenseManagerDao.updateBudget(budget)
    }

    override suspend fun deleteBudget(budget: Budget) {
        expenseManagerDao.deleteBudget(budget)
    }

    override suspend fun getBudgetById(budgetId: Int): Budget {
        return expenseManagerDao.getBudgetById(budgetId)
    }
}