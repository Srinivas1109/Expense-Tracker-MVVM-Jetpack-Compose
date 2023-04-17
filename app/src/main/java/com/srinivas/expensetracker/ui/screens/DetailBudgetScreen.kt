package com.srinivas.expensetracker.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.srinivas.expensetracker.NavDestinations
import com.srinivas.expensetracker.data.entities.Budget
import com.srinivas.expensetracker.data.relations.ExpenseWithBudgetAndCategory
import com.srinivas.expensetracker.ui.composables.ExpenseItem
import com.srinivas.expensetracker.ui.composables.PieChart
import kotlinx.coroutines.flow.Flow

data class PieChartData(val category: String, val expense: Float)

@Composable
fun DetailBudgetScreen(
    modifier: Modifier = Modifier,
    budgetId: Int?,
    getCategoryIcon: (String) -> Int,
    deleteExpenseItem: (Int, ExpenseWithBudgetAndCategory) -> Unit,
    updateBudget: (Budget) -> Unit,
    showSnackbar: MutableState<Boolean>,
    navController: NavController,
    getBudgetDetails: (Int) -> Flow<List<ExpenseWithBudgetAndCategory>>
) {
    val pieChartData = mutableListOf<PieChartData>()
    var amount by remember {
        mutableStateOf("")
    }
    if (budgetId != null) {
        val budgetDetailsList = getBudgetDetails(budgetId).collectAsState(initial = emptyList())
        val categoriesGroup = budgetDetailsList.value.groupBy {
            it.expenseCategory
        }
        var showAlertDialog by remember {
            mutableStateOf(false)
        }

        val context = LocalContext.current
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            if (showAlertDialog) {
                AlertDialog(
                    onDismissRequest = { showAlertDialog = false },
                    modifier = Modifier.fillMaxWidth(),
                    title = { Text(text = "Edit Budget") },
                    text = {
                        Column() {
                            OutlinedTextField(
                                modifier = modifier
                                    .fillMaxWidth()
                                    .padding(10.dp),
                                value = amount,
                                onValueChange = { amount = it },
                                placeholder = { Text(text = "Amount to be Modified") },
                                label = { Text(text = "Amount") },
                                keyboardOptions = KeyboardOptions(
                                    imeAction = ImeAction.Next,
                                    keyboardType = KeyboardType.Number
                                ),
                                singleLine = true
                            )
                            Text(
                                text = "Note: For example if you to increment previous amount by Rs 500 enter 500 or else if you want to decrease by Rs 500 enter -500.",
                                fontSize = 12.sp,
                                color = Color.Gray,
                                fontWeight = FontWeight.ExtraLight
                            )
                            Spacer(modifier = modifier.height(5.dp))
                        }
                    },
                    buttons = {
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = modifier
                                .fillMaxWidth()
                                .padding(10.dp)
                        ) {

                            Button(onClick = { showAlertDialog = false }) {
                                Text(text = "Cancel")
                            }
                            Button(onClick = {
                                if (budgetDetailsList.value.isNotEmpty()) {
                                    val budget = budgetDetailsList.value[0].budget
                                    updateBudget(budget.copy(amount = budget.amount + amount.toDouble()))
                                } else {
                                    Toast.makeText(
                                        context,
                                        "Delete Budget and add New budget",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }

                                showAlertDialog = false
                                amount = ""
                            }) {
                                Text(text = "Update")
                            }
                        }

                    }
                )
            }
            if (budgetDetailsList.value.isNotEmpty()) {
                categoriesGroup.forEach {
                    pieChartData.add(
                        PieChartData(
                            it.key.categoryName,
                            -(it.value.sumOf { x -> x.expense.expenseAmount }.toFloat())
                        )
                    )
                }
                Column(
                    modifier = modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    PieChart(
                        data = pieChartData,
                        budgetName = budgetDetailsList.value[0].budget.budgetName
                    )
                    Button(
                        modifier = modifier.fillMaxWidth(),
                        onClick = { navController.navigate("${NavDestinations.AddExpenseScreen.name}/${budgetId}") }) {
                        Text(
                            text = "Add Expense",
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                    }
                    Spacer(modifier = modifier.height(5.dp))
                    Button(
                        modifier = modifier.fillMaxWidth(),
                        onClick = { showAlertDialog = true }) {
                        Text(
                            text = "Edit Budget",
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                    }
                    Spacer(modifier = modifier.height(5.dp))

                    LazyColumn() {
                        items(items = budgetDetailsList.value) { budgetItem ->
                            ExpenseItem(
                                expenseItem = budgetItem,
                                deleteExpenseItem = deleteExpenseItem,
                                showSnackbar = showSnackbar,
                                categoryIcon = getCategoryIcon(budgetItem.expenseCategory.categoryName)
                            )
                        }
                    }
                }

            } else {
                Column(
                    modifier = modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {

                    Text(text = "No expenses found related to this budget")
                    Spacer(modifier = modifier.height(5.dp))
                    Button(
                        modifier = modifier.fillMaxWidth(),
                        onClick = { navController.navigate("${NavDestinations.AddExpenseScreen.name}/${budgetId}") }) {
                        Text(
                            text = "Add Expense",
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                    }
                    Spacer(modifier = modifier.height(5.dp))
                    Button(
                        modifier = modifier.fillMaxWidth(),
                        onClick = { showAlertDialog = true }) {
                        Text(
                            text = "Edit Budget",
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                    }
                }
            }
        }
    } else {
        Text(text = "Budget id not found")
    }
}