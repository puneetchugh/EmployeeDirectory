package com.example.employeedirectory.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.employeedirectory.data.model.Employee
import com.example.employeedirectory.data.model.Employees


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun EmployeeListUi(
    employees: Employees,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
    ) {
        val employeesMap = employees.employeeList.groupBy { it.fullName.first() }.toSortedMap()
        employeesMap.entries.forEach { entry: Map.Entry<Char, List<Employee>> ->
            stickyHeader {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    text = entry.key.toString(),
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.DarkGray
                )
            }
            itemsIndexed(entry.value) { index, item ->
                EmployeeUi(
                    index = index,
                    employee = item
                )
            }

        }
    }
}

@Composable
@Preview
fun PreviewEmployeeListUi() {
    val employee1 = Employee(
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

    val employee2 = employee1.copy(uuid = "efgh-1234-xy09", fullName = "Jonna Smith")
    val employee3 = employee1.copy(uuid = "efgh-1234-abcd", fullName = "Jacob Smith")
    val employee4 = employee1.copy(uuid = "a1b2-1234-xy09", fullName = "Cynthia Turner")
    EmployeeListUi(
        employees = Employees(
            employeeList = listOf(employee1, employee2, employee3, employee4)
        )
    )
}
