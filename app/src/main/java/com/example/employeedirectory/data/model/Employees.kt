package com.example.employeedirectory.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Employees(
    @SerialName("employees")
    val employeeList: List<Employee>
)