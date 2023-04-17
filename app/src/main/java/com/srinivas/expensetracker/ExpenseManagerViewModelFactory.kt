package com.srinivas.expensetracker

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.srinivas.expensetracker.data.repositories.LocalExpenseManagerRepository
import com.srinivas.expensetracker.data.viewmodels.ExpenseManagerViewModel

class ExpenseManagerViewModelFactory(private val repository: LocalExpenseManagerRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ExpenseManagerViewModel(repository) as T
    }
}