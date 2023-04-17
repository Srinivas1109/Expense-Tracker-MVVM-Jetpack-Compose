package com.srinivas.expensetracker.ui.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.srinivas.expensetracker.data.relations.ExpenseWithBudgetAndCategory

@Composable
fun ExpensesList(
    modifier: Modifier = Modifier,
    deleteExpenseItem: (Int, ExpenseWithBudgetAndCategory) -> Unit,
    showSnackbar: MutableState<Boolean>,
    expensesList: List<ExpenseWithBudgetAndCategory>,
    getCategoryIcon: (String) -> Int
){
    Column() {
        Text(
            text = "Your expenses",
            fontWeight = FontWeight.Bold,
            fontSize = 28.sp,
            modifier = modifier.padding(16.dp)
        )
        LazyColumn(){
            items(items = expensesList){
                ExpenseItem(
                    expenseItem = it,
                    categoryIcon = getCategoryIcon(it.expense.expenseCategory),
                    showSnackbar = showSnackbar,
                    deleteExpenseItem = deleteExpenseItem
                )
            }

        }
    }

}