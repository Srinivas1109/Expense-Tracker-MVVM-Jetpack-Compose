package com.srinivas.expensetracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.srinivas.expensetracker.data.databases.ExpenseManagerDatabase
import com.srinivas.expensetracker.data.repositories.LocalExpenseManagerRepository
import com.srinivas.expensetracker.ui.theme.ExpenseTrackerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val expenseManagerDao =
            ExpenseManagerDatabase.getInstance(applicationContext).expenseManagerDao
        val repository = LocalExpenseManagerRepository(expenseManagerDao)
        val factory = ExpenseManagerViewModelFactory(repository)


        setContent {
            ExpenseTrackerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    ExpenseManagerApp(factory = factory)
                }
            }
        }
    }
}
