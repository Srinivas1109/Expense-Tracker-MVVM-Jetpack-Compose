package com.srinivas.expensetracker.ui.composables

import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.srinivas.expensetracker.data.entities.Budget
import com.srinivas.expensetracker.data.relations.ExpenseWithBudgetAndCategory

@Composable
fun BudgetsList(
    modifier: Modifier = Modifier,
    navController: NavController,
    totalExpenses: List<ExpenseWithBudgetAndCategory>,
    showSnackbar: MutableState<Boolean>,
    deleteBudget: (Budget) -> Unit,
    budgets: List<Budget>
) {
    LazyRow() {
        items(items = budgets) { budget ->
            BudgetItem(
                budget = budget,
                showSnackbar = showSnackbar,
                deleteBudget = deleteBudget,
                navController = navController,
                totalExpenses = totalExpenses.filter {
                    it.budget.budgetId == budget.budgetId
                }.sumOf { expense -> expense.expense.expenseAmount })
        }
    }
}