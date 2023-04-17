package com.srinivas.expensetracker.ui.composables

import android.widget.Toast
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.srinivas.expensetracker.data.relations.ExpenseWithBudgetAndCategory
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun ExpenseItem(
    modifier: Modifier = Modifier,
    expenseItem: ExpenseWithBudgetAndCategory,
    showSnackbar: MutableState<Boolean>,
    deleteExpenseItem: (Int, ExpenseWithBudgetAndCategory) -> Unit,
    categoryIcon: Int
) {
    var dragChange by remember {
        mutableStateOf(0.0f)
    }
    Card(
        elevation = 10.dp,
        modifier = modifier
            .offset(
                x = if (dragChange < 0) {
                    dragChange.dp
                } else {
                    0.dp
                }
            )
            .padding(top = 4.dp, start = 16.dp, end = 16.dp, bottom = 6.dp)
            .pointerInput(Unit) {
                detectHorizontalDragGestures { _, dragAmount ->
                    dragChange = dragAmount
                    if (dragChange < -30) {
                        deleteExpenseItem(expenseItem.expense.expenseId, expenseItem)
                        showSnackbar.value = true
                        dragChange = 0.0f
                    }
                }
            }
    ) {

        Row(
            modifier = modifier
                .fillMaxWidth(1f)
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = modifier.fillMaxWidth(0.75f)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = expenseItem.expenseCategory.categoryName,
                        fontWeight = FontWeight.Bold,
                        fontSize = 26.sp
                    )
                    Spacer(modifier = modifier.width(10.dp))
                    Icon(
                        painter = painterResource(id = categoryIcon),
                        contentDescription = "Icon",
                        tint = Color.Gray,
                        modifier = modifier.size(24.dp)
                    )
                }
                Text(text = expenseItem.budget.budgetName)
                Text(text = formatTimestamp(expenseItem.expense.createdOn))
            }
            Text(
                text = "₹ ${expenseItem.expense.expenseAmount}",
                fontSize = 22.sp,
                fontWeight = FontWeight.Normal,
                fontStyle = FontStyle.Italic,
                color = if (expenseItem.expense.expenseAmount > 0) Color.Green else Color.Red
            )
        }
    }
}

fun formatTimestamp(timestamp: Long): String {
    val sdf = SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.getDefault())
    val date = Date(timestamp)
    return sdf.format(date)
}