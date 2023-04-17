package com.srinivas.expensetracker.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.srinivas.expensetracker.data.entities.Budget
import com.srinivas.expensetracker.data.relations.ExpenseWithBudgetAndCategory
import com.srinivas.expensetracker.ui.composables.BudgetItem

@Composable
fun AllBudgetsScreen(
    modifier: Modifier = Modifier,
    budgets: List<Budget>,
    totalExpenses: List<ExpenseWithBudgetAndCategory>,
    showSnackbar: MutableState<Boolean>,
    deleteBudget: (Budget) -> Unit,
    navController: NavController
) {
    Column(modifier = modifier.fillMaxSize()) {
        Text(
            text = "Your Budgets",
            modifier = modifier
                .fillMaxWidth()
                .padding(8.dp),
            fontSize = 24.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = modifier.height(5.dp))
        LazyColumn(modifier = modifier.fillMaxWidth()) {
            items(items = budgets) { budget ->
                BudgetItem(
                    budget = budget,
                    progressBarWidth = 160.dp,
                    imageWidth = 0.85f,
                    deleteBudget = deleteBudget,
                    showSnackbar = showSnackbar,
                    navController = navController,
                    totalExpenses = totalExpenses.filter {
                        it.budget.budgetId == budget.budgetId
                    }.sumOf { expense -> expense.expense.expenseAmount }
                )
            }
        }

    }
}