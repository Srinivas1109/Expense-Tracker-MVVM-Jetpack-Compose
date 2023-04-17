package com.srinivas.expensetracker.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.srinivas.expensetracker.R
import com.srinivas.expensetracker.ui.theme.Shapes

@Composable
fun UserInformation(
    modifier: Modifier = Modifier,
    username: String,
    image: Int = R.mipmap.ic_launcher_foreground
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 16.dp, start = 16.dp, bottom = 10.dp, end = 16.dp),
        elevation = 15.dp,
        shape = Shapes.medium
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(5.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Hello, $username",
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp,
                modifier = modifier.padding(start = 10.dp)
            )
            Image(
                painter = painterResource(id = image), contentDescription = "App Icon",
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)                       // clip to the circle shape
//                    .border(2.dp, Color.Gray, CircleShape)   // add a border (optional)

            )
        }
    }

}