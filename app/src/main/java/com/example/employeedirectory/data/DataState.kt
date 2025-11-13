package com.example.employeedirectory.data

import com.example.employeedirectory.data.model.Employees

sealed class DataState {
    data class Success(val employees: Employees) : DataState()
    data class Error(
        val code: Int? = null,
        val exception: Throwable
    ) : DataState()

    data object Loading : DataState()
}