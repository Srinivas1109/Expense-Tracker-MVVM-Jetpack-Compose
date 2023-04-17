package com.srinivas.expensetracker.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.srinivas.expensetracker.NavDestinations
import com.srinivas.expensetracker.data.entities.Budget
import com.srinivas.expensetracker.data.relations.ExpenseWithBudgetAndCategory
import com.srinivas.expensetracker.ui.composables.BudgetsList
import com.srinivas.expensetracker.ui.composables.ExpensesList
import com.srinivas.expensetracker.ui.composables.UserInformation

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    expenses: List<ExpenseWithBudgetAndCategory>,
    availableBudgets: List<Budget>,
    showSnackbar: MutableState<Boolean>,
    addNewBudget: (Budget) -> Unit,
    deleteExpenseItem: (Int, ExpenseWithBudgetAndCategory) -> Unit,
    deleteBudget: (Budget) -> Unit,
    getCategoryIcon: (String) -> Int
){

    var showAlertDialog by remember {
        mutableStateOf(false)
    }
    var inputBudget by remember {
        mutableStateOf("")
    }
    var inputBudgetAmount by remember {
        mutableStateOf("")
    }

    val focusManager = LocalFocusManager.current

    Column() {
        UserInformation(username = "Buddy")
        BudgetsList(budgets = availableBudgets, navController = navController, totalExpenses = expenses, deleteBudget = deleteBudget, showSnackbar = showSnackbar)
        if (showAlertDialog) {
            AlertDialog(
                onDismissRequest = { showAlertDialog = false },
                modifier = Modifier.fillMaxWidth(),
                title = { Text(text = "New Budget") },
                text = {
                    Column() {
                        OutlinedTextField(
                            modifier = modifier
                                .fillMaxWidth()
                                .padding(10.dp),
                            value = inputBudget,
                            onValueChange = { inputBudget = it },
                            placeholder = { Text(text = "Budget Name") },
                            label = { Text(text = "Budget Name") },
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                            keyboardActions = KeyboardActions(onNext = {
                                focusManager.moveFocus(focusDirection = FocusDirection.Next)
                            }),
                            singleLine = true
                        )
                        Spacer(modifier = modifier.height(5.dp))

                        OutlinedTextField(
                            modifier = modifier
                                .fillMaxWidth()
                                .padding(10.dp),
                            value = inputBudgetAmount,
                            onValueChange = { inputBudgetAmount = it },
                            placeholder = { Text(text = "Amount") },
                            label = { Text(text = "Amount") },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number,
                                imeAction = ImeAction.Done
                            ),
                            singleLine = true
                        )

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
                            addNewBudget(
                                Budget(
                                    budgetName = inputBudget,
                                    amount = inputBudgetAmount.toDouble()
                                )
                            )
                            showAlertDialog = false
                            inputBudget = ""
                            inputBudgetAmount = ""
                        }) {
                            Text(text = "Add")
                        }
                    }

                }
            )
        }
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // onclick show a alert dialog to add new budget
            Button(onClick = {
                showAlertDialog = true
            }) {
                Text(
                    text = "Add New Budget",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }
            // onclick this button navigate to the detail screen
            Button(onClick = {navController.navigate(NavDestinations.BudgetsListScreen.name)})
            {
                Text(
                    text = "View All Budgets",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }
        }
        ExpensesList(
            expensesList = expenses,
            getCategoryIcon = getCategoryIcon,
            showSnackbar = showSnackbar,
            deleteExpenseItem = deleteExpenseItem
        )

    }
}