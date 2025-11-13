package com.example.employeedirectory.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.employeedirectory.R
import com.example.employeedirectory.data.model.Employee

@Composable
fun EmployeeUi(
    modifier: Modifier = Modifier,
    employee: Employee,
    index: Int
) {
    Card(
        modifier = modifier
            .padding(4.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        ListItem(
            leadingContent = {
                AsyncImage(
                    modifier = Modifier
                        .size(size = 96.dp)
                        .clip(CircleShape),
                    model = employee.photoURLSmall,
                    contentDescription = "employee  $index: ${employee.fullName}, ${employee.uuid}",
                    error = painterResource(R.drawable.ic_placeholder_foreground),
                    fallback = painterResource(R.drawable.ic_placeholder_foreground),
                )
            },
            headlineContent = {
                Column(
                    modifier = Modifier,
                    verticalArrangement = Arrangement.Top
                ) {
                    Text(
                        text = employee.fullName,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Text(
                        modifier = Modifier,
                        text = employee.emailAddress,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        fontStyle = FontStyle.Italic,
                        color = Color.DarkGray
                    )
                }
            },
            supportingContent = {
                Text(
                    text = employee.team,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.DarkGray
                )
            }
        )
    }
}

@Composable
@Preview
fun PreviewEmployeeUi() {
    EmployeeUi(
        index = 1,
        employee = Employee(
            biography = "",
            emailAddress = "some_email@email.com",
            employeeType = "Full-time",
            fullName = "John Smith",
            phoneNumber = "999-999-999",
            photoURLLarge = "",
            photoURLSmall = "",
            team = "Ui/Ux",
            uuid = "abcd-1234-xy09"
        )
    )
}