package com.srinivas.expensetracker.data.databases

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.srinivas.expensetracker.data.daos.ExpenseManagerDao
import com.srinivas.expensetracker.data.entities.Budget
import com.srinivas.expensetracker.data.entities.Expense
import com.srinivas.expensetracker.data.entities.ExpenseCategory

@Database(entities = [Budget::class, Expense::class, ExpenseCategory::class], version = 1)
abstract class ExpenseManagerDatabase : RoomDatabase() {
    abstract val expenseManagerDao: ExpenseManagerDao

    companion object {
        @Volatile
        private var INSTANCE: ExpenseManagerDatabase? = null
        fun getInstance(context: Context): ExpenseManagerDatabase {
            var instance: ExpenseManagerDatabase
            synchronized(this) {
                instance = INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    ExpenseManagerDatabase::class.java,
                    "expense_manager_db"
                ).build()

            }
            return instance
        }
    }
}