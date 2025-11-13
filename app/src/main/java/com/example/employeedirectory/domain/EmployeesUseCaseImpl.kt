package com.example.employeedirectory.domain

import com.example.employeedirectory.data.DataState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class EmployeesUseCaseImpl @Inject constructor(
    private val employeesRepository: EmployeesRepository
) :
    EmployeesUseCase {

    override operator fun invoke(): Flow<DataState> {
        return employeesRepository.getEmployees()
    }
}