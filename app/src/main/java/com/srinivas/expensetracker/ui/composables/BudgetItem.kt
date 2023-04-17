package com.srinivas.expensetracker.ui.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.srinivas.expensetracker.NavDestinations
import com.srinivas.expensetracker.R
import com.srinivas.expensetracker.data.entities.Budget
import com.srinivas.expensetracker.ui.theme.Shapes
import java.math.RoundingMode
import java.text.DecimalFormat

@Composable
fun BudgetItem(
    modifier: Modifier = Modifier,
    budget: Budget,
    progressBarWidth: Dp = 120.dp,
    imageWidth: Float = 0.25f,
    navController: NavController,
    totalExpenses: Double,
    showSnackbar: MutableState<Boolean>,
    deleteBudget: (Budget) -> Unit
) {
    // total expenses should be calculated to each budget but as of now it is calculating to all budgets
    val remainingAmount = budget.amount + totalExpenses
    val percentage = (remainingAmount / budget.amount)

    Card(
        shape = Shapes.medium,
        modifier = Modifier
            .fillMaxWidth(1f)
            .padding(16.dp)
            .heightIn(min = 120.dp)
            .widthIn(min = 300.dp)
            .clickable {
                navController.navigate("${NavDestinations.BudgetDetailScreen.name}/${budget.budgetId}")
            },
        elevation = 20.dp
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth(1f)
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column() {
                Text(
                    text = budget.budgetName,
                    fontWeight = FontWeight.Bold,
                    fontStyle = FontStyle.Italic,
                    fontSize = 26.sp
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    LinearProgressIndicator(
                        progress = percentage.toFloat(),
                        modifier = Modifier
                            .width(progressBarWidth)
                    )
                    Text(text = "  ${roundOffDecimal(percentage * 100)}% left")
                }

                Spacer(modifier = modifier.height(5.dp))

                Text(text = "Initial Amount: ₹${roundOffDecimal(budget.amount)}")
                Text(text = "Remaining Amount: ₹${roundOffDecimal(remainingAmount)}")

            }

            IconButton(onClick = {
                deleteBudget(budget)
//                showSnackbar.value = true
            }) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete Budget Icon",
                    tint = Color.Gray,
                    modifier = modifier
                        .fillMaxWidth(imageWidth)
                        .clip(CircleShape)
                        .size(64.dp)
                )
            }

        }
    }
}

fun roundOffDecimal(number: Double): Double {
    val df = DecimalFormat("#.##")
    df.roundingMode = RoundingMode.CEILING
    return df.format(number).toDouble()
}
