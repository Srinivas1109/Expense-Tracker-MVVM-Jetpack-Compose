package com.srinivas.expensetracker.data.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.srinivas.expensetracker.R
import com.srinivas.expensetracker.data.entities.Budget
import com.srinivas.expensetracker.data.entities.Expense
import com.srinivas.expensetracker.data.relations.ExpenseWithBudgetAndCategory
import com.srinivas.expensetracker.data.repositories.LocalExpenseManagerRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class ExpenseManagerViewModel(private val repository: LocalExpenseManagerRepository) :
    ViewModel() {

    val expensesWithBudgetAndCategory = repository.getAllExpenses()
    val availableBudgets = repository.getAllBudgets()
    val availableCategories = repository.getAllCategories()
    var cachedExpenseItem: ExpenseWithBudgetAndCategory? = null
    var cachedBudgetItem: Budget? = null

    fun getCategorySymbol(categoryName: String): Int {
        return when (categoryName) {
            "Shopping" -> R.drawable.shopping_cart_48px
            "Food" -> R.drawable.restaurant_menu_48px
            "Groceries" -> R.drawable.shopping_basket_48px
            "Entertainment" -> R.drawable.sports_esports_48px
            "Bills" -> R.drawable.receipt_long_48px
            "Travel" -> R.drawable.flight_takeoff_48px
            "Personal" -> R.drawable.settings_accessibility_48px
            "Education" -> R.drawable.school_48px
            "Utilities" -> R.drawable.build_48px
            else -> R.drawable.settings_accessibility_48px
        }
    }

    fun addNewBudget(budget: Budget) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addBudget(budget)
        }
    }

    fun getExpenseByBudgetId(id: Int): Flow<List<ExpenseWithBudgetAndCategory>> {
        return repository.getExpenseByBudgetId(id)
    }

    fun addNewExpense(expense: Expense) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addExpense(expense)
        }
    }

    fun deleteExpense(expenseId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteExpense(expenseId)
        }
    }

    fun updateBudgetAmount(budget: Budget) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateBudget(budget)
        }
    }

    fun deleteBudget(budget: Budget){
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteBudget(budget)
        }
    }


}