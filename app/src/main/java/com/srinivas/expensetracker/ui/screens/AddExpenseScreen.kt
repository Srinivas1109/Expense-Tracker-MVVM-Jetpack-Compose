package com.srinivas.expensetracker.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.srinivas.expensetracker.NavDestinations
import com.srinivas.expensetracker.data.entities.Expense
import com.srinivas.expensetracker.data.entities.ExpenseCategory

@Composable
fun AddExpenseScreen(
    modifier: Modifier = Modifier,
    budgetId: Int?,
    availableCategories: List<ExpenseCategory>,
    navController: NavController,
    addNewExpense: (Expense) -> Unit
) {
    var selectedCategory by remember {
        mutableStateOf("")
    }
    var inputExpenseAmount by remember {
        mutableStateOf("")
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Add New Expense",
            modifier = modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = modifier.height(10.dp))
        OutlinedTextField(
            value = inputExpenseAmount,
            onValueChange = { inputExpenseAmount = it },
            placeholder = { Text(text = "Amount") },
            label = { Text(text = "Amount") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = modifier.fillMaxWidth()
        )
        Spacer(modifier = modifier.height(10.dp))
        Text(
            text = "Select Category",
            modifier = modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = modifier.height(4.dp))
        CategoryChips(
            categories = availableCategories,
            isSelected = { selCat -> selCat == selectedCategory }) {
            selectedCategory = it
        }
        Spacer(modifier = modifier.height(5.dp))
        Button(
            onClick = {
                budgetId?.let {
                    Expense(
                        budgetId = it,
                        expenseCategory = selectedCategory,
                        expenseAmount = -inputExpenseAmount.toDouble()
                    )
                }?.let {
                    addNewExpense(it)
                    selectedCategory = ""
                    inputExpenseAmount = ""
                    navController.navigate("${NavDestinations.HomeScreen}"){
                        popUpTo(NavDestinations.HomeScreen.name){
                            inclusive = true
                        }
                    }
                }
            },
            modifier = modifier.fillMaxWidth()
        ) {
            Text(
                text = "Add Expense",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }

    }


}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CategoryChips(
    categories: List<ExpenseCategory>,
    modifier: Modifier = Modifier,
    isSelected: (String) -> Boolean,
    selectedCategory: (String) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 120.dp),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
    ) {
        items(categories) {
            Chip(onClick = { selectedCategory(it.categoryName) }) {
                Text(
                    text = it.categoryName,
                    textAlign = TextAlign.Center,
                    modifier = modifier.fillMaxWidth(),
                    color = if (isSelected(it.categoryName)) {
                        Color.Green
                    } else {
                        Color.White
                    }
                )
            }
        }
    }
}