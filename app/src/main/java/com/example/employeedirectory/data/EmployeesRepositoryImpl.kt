package com.example.employeedirectory.data

import android.os.Build
import androidx.annotation.RequiresExtension
import com.example.employeedirectory.Constants.EMPTY_LIST_ERROR
import com.example.employeedirectory.data.model.Employees
import com.example.employeedirectory.di.IoDispatcher
import com.example.employeedirectory.domain.EmployeesRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class EmployeesRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : EmployeesRepository {

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override fun getEmployees(): Flow<DataState> = flow {
        emit(DataState.Loading)
        val response = apiService.fetchEmployees()
        if (response.isSuccessful && response.body()?.employeeList?.isNotEmpty() == true) {
            response.body()?.let { employees: Employees ->
                emit(DataState.Success(employees = employees))
            }
        } else {
            val code = response.code()
            // Analytics could be sent here for unsuccessful api call with error code
            emit(
                DataState.Error(
                    code = code,
                    exception = Throwable(EMPTY_LIST_ERROR)
                )
            )
        }
    }.flowOn(dispatcher)
        .catch { throwable ->
            // Analytics could be sent here for unsuccessful api call with exception
            emit(DataState.Error(exception = throwable))
        }
}