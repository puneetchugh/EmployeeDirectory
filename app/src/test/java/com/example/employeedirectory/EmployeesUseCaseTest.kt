package com.example.employeedirectory

import com.example.employeedirectory.data.DataState
import com.example.employeedirectory.data.model.Employee
import com.example.employeedirectory.data.model.Employees
import com.example.employeedirectory.domain.EmployeesRepository
import com.example.employeedirectory.domain.EmployeesUseCase
import com.example.employeedirectory.domain.EmployeesUseCaseImpl
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class EmployeesUseCaseTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    private val ioDispatcher = UnconfinedTestDispatcher()

    private lateinit var employeesUseCase: EmployeesUseCase

    private val employeesRepository = mockk<EmployeesRepository>(relaxed = true)

    @Before
    fun setUp() {
        employeesUseCase = EmployeesUseCaseImpl(employeesRepository = employeesRepository)
    }

    @Test
    fun `when EmployeesRepository getEmployees returns DataState Success, EmployeeUseCase invoke() also passes the DataState Success`() = runTest {
        coEvery { employeesRepository.getEmployees() } returns flowOf(
            DataState.Loading, DataState.Success(
                Employees(
                    employeeList = listOf(
                        Employee(
                            fullName = "Puneet Chugh",
                            biography = "Android Dev",
                            emailAddress = "",
                            employeeType = "",
                            phoneNumber = "",
                            photoURLLarge = "",
                            photoURLSmall = "",
                            team = "Team Android",
                            uuid = "xyz-123",
                        )
                    )
                )
            )
        )

        val listOfDataStates = mutableListOf<DataState>()
        backgroundScope.launch(ioDispatcher) {
            employeesRepository.getEmployees().collectLatest {
                listOfDataStates.add(it)
            }
        }
        Assert.assertEquals( 2, listOfDataStates.size)
        Assert.assertEquals(DataState.Loading, listOfDataStates.first())
        Assert.assertTrue(listOfDataStates[1] is DataState.Success)
    }

    @Test
    fun `when EmployeesRepository getEmployees returns DataState Error, EmployeeUseCase invoke() also passes the DataState Error`() = runTest {
        val code = 401
        val message = "Exception in network api"
        coEvery { employeesRepository.getEmployees() } returns flowOf(
            DataState.Loading, DataState.Error(exception = Throwable(message), code = code)
        )

        val listOfDataStates = mutableListOf<DataState>()
        backgroundScope.launch(ioDispatcher) {
            employeesRepository.getEmployees().collectLatest {
                listOfDataStates.add(it)
            }
        }
        Assert.assertEquals( 2, listOfDataStates.size)
        Assert.assertEquals(DataState.Loading, listOfDataStates.first())
        Assert.assertTrue(listOfDataStates[1] is DataState.Error)
        Assert.assertEquals(message, (listOfDataStates[1] as DataState.Error).exception.message)
        Assert.assertEquals(code, (listOfDataStates[1] as DataState.Error).code)

    }

}