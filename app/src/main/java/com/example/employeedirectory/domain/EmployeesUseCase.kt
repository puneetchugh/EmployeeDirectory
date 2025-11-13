package com.example.employeedirectory.domain

import com.example.employeedirectory.data.DataState
import kotlinx.coroutines.flow.Flow

interface EmployeesUseCase {
    operator fun invoke(): Flow<DataState>
}