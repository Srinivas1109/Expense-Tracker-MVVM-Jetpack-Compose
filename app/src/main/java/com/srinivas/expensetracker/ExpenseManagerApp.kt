package com.srinivas.expensetracker

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.srinivas.expensetracker.data.viewmodels.ExpenseManagerViewModel
import com.srinivas.expensetracker.ui.screens.AddExpenseScreen
import com.srinivas.expensetracker.ui.screens.AllBudgetsScreen
import com.srinivas.expensetracker.ui.screens.DetailBudgetScreen
import com.srinivas.expensetracker.ui.screens.HomeScreen

enum class NavDestinations {
    HomeScreen,
    BudgetDetailScreen,
    AddExpenseScreen,
    BudgetsListScreen
}

@Composable
fun ExpenseManagerApp(
    factory: ExpenseManagerViewModelFactory,
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()

    val expenseManagerViewModel: ExpenseManagerViewModel = viewModel(factory = factory)
    val expenses =
        expenseManagerViewModel.expensesWithBudgetAndCategory.collectAsState(initial = emptyList())
    val availableCategories =
        expenseManagerViewModel.availableCategories.collectAsState(initial = emptyList())
    val availableBudgets =
        expenseManagerViewModel.availableBudgets.collectAsState(initial = emptyList())

    val scaffoldState: ScaffoldState = rememberScaffoldState()
    val showSnackbar = remember {
        mutableStateOf(false)
    }

    Scaffold(
        scaffoldState = scaffoldState
    ) { it ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(it)
        ) {
            NavHost(
                navController = navController,
                startDestination = NavDestinations.HomeScreen.name
            ) {
                composable(route = NavDestinations.HomeScreen.name) {
                    HomeScreen(
                        expenses = expenses.value,
                        availableBudgets = availableBudgets.value,
                        addNewBudget = {
                            expenseManagerViewModel.addNewBudget(it)
                        },
                        deleteExpenseItem = { id, deletedItem ->
                            expenseManagerViewModel.cachedExpenseItem = deletedItem
                            expenseManagerViewModel.deleteExpense(id)
                        },
                        showSnackbar = showSnackbar,
                        deleteBudget = {
                            expenseManagerViewModel.cachedBudgetItem = it
                            expenseManagerViewModel.deleteBudget(it)
                        },
                        navController = navController,
                    ) {
                        expenseManagerViewModel.getCategorySymbol(it)
                    }
                }
                composable(route = NavDestinations.BudgetsListScreen.name) {
                    AllBudgetsScreen(
                        budgets = availableBudgets.value,
                        navController = navController,
                        totalExpenses = expenses.value,
                        showSnackbar = showSnackbar,
                        deleteBudget = {
                            expenseManagerViewModel.cachedBudgetItem = it
                            expenseManagerViewModel.deleteBudget(it)
                        },
                    )
                }
                composable(
                    route = "${NavDestinations.BudgetDetailScreen.name}/{budgetId}",
                    arguments = listOf(navArgument("budgetId") {
                        type = NavType.IntType
                    })
                ) { nav ->
                    DetailBudgetScreen(
                        budgetId = nav.arguments?.getInt("budgetId"),
                        deleteExpenseItem = { id, deletedItem ->
                            expenseManagerViewModel.cachedExpenseItem = deletedItem
                            expenseManagerViewModel.deleteExpense(id)
                        },
                        showSnackbar = showSnackbar,
                        getCategoryIcon = { categoryName ->
                            expenseManagerViewModel.getCategorySymbol(
                                categoryName
                            )
                        },
                        updateBudget = {
                            expenseManagerViewModel.updateBudgetAmount(it)
                        },
                        navController = navController
                    ) { id ->
                        expenseManagerViewModel.getExpenseByBudgetId(id)
                    }
                }

                composable(
                    route = "${NavDestinations.AddExpenseScreen.name}/{budgetId}",
                    arguments = listOf(navArgument("budgetId") {
                        type = NavType.IntType
                    })
                ) {
                    AddExpenseScreen(
                        budgetId = it.arguments?.getInt("budgetId"),
                        navController = navController,
                        availableCategories = availableCategories.value
                    ) { newExpense ->
                        expenseManagerViewModel.addNewExpense(newExpense)
                    }
                }
            }

            if (showSnackbar.value) {
                LaunchedEffect(key1 = showSnackbar.value) {
                    val result = scaffoldState.snackbarHostState.showSnackbar(
                        message = "Deleted Successfully",
                        actionLabel = "undo",
                        duration = SnackbarDuration.Short
                    )
                    when (result) {
                        SnackbarResult.ActionPerformed -> {
                            val cachedItem = expenseManagerViewModel.cachedExpenseItem
                            if (cachedItem != null) {
                                expenseManagerViewModel.addNewExpense(cachedItem.expense)
                            }
                        }
                        SnackbarResult.Dismissed -> {
                            showSnackbar.value = false
                            expenseManagerViewModel.cachedExpenseItem = null
                            // make cached to todoItem null
                        }
                    }
                    showSnackbar.value = false
//                }
                }
            }
        }
    }


}
