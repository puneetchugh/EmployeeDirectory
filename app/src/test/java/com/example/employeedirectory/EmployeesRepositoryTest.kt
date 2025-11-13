package com.example.employeedirectory

import com.example.employeedirectory.data.ApiService
import com.example.employeedirectory.data.DataState
import com.example.employeedirectory.data.EmployeesRepositoryImpl
import com.example.employeedirectory.data.model.Employee
import com.example.employeedirectory.data.model.Employees
import com.example.employeedirectory.domain.EmployeesRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Response

class EmployeesRepositoryTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()
    private lateinit var employeesRepository: EmployeesRepository

    private val apiService = mockk<ApiService>(relaxed = true)

    @OptIn(ExperimentalCoroutinesApi::class)
    private val ioDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        employeesRepository = EmployeesRepositoryImpl(
            apiService = apiService,
            dispatcher = ioDispatcher
        )
    }

    @Test
    fun `when EmployeesRepository getEmployees method is called, an empty list is returned by the api, Repository returns an Error State`() =
        runTest {
            coEvery { apiService.fetchEmployees() } returns Response.success(Employees(employeeList = listOf()))

            val listOfDataStates = mutableListOf<DataState>()
            backgroundScope.launch(ioDispatcher) {
                employeesRepository.getEmployees().collectLatest {
                    listOfDataStates.add(it)
                }
            }
            Assert.assertEquals(2, listOfDataStates.size)
            Assert.assertTrue(listOfDataStates.first() is DataState.Loading)
            Assert.assertTrue(listOfDataStates[1] is DataState.Error)
        }

    @Test
    fun `when EmployeesRepository getEmployees method is called, an error is thrown, Repository returns an Error State`() =
        runTest {
            val exceptionMessage = "Network exception"
            coEvery { apiService.fetchEmployees() } throws Exception(exceptionMessage)

            val listOfDataStates = mutableListOf<DataState>()
            backgroundScope.launch(ioDispatcher) {
                employeesRepository.getEmployees().collectLatest {
                    listOfDataStates.add(it)
                }
            }
            Assert.assertEquals(2, listOfDataStates.size)
            Assert.assertTrue(listOfDataStates.first() is DataState.Loading)
            Assert.assertTrue(listOfDataStates[1] is DataState.Error)
            Assert.assertEquals(
                exceptionMessage,
                (listOfDataStates[1] as DataState.Error).exception.message
            )
        }

    @Test
    fun `when EmployeesRepository getEmployees method is called, an valid Employees data is returned by the api, Repository returns a Success State`() =
        runTest {
            val fullName = "Puneet Chugh"
            val biography = "Android Dev"
            coEvery { apiService.fetchEmployees() } returns Response.success(
                Employees(
                    employeeList = listOf(

                        Employee(
                            fullName = fullName,
                            biography = biography,
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

            val listOfDataStates = mutableListOf<DataState>()
            employeesRepository.getEmployees()
            backgroundScope.launch(ioDispatcher) {
                employeesRepository.getEmployees().collect {
                    listOfDataStates.add(it)
                }
            }
            Assert.assertEquals(2, listOfDataStates.size)
            Assert.assertTrue(listOfDataStates.first() is DataState.Loading)
            Assert.assertTrue(listOfDataStates[1] is DataState.Success)
            Assert.assertEquals(
                fullName,
                (listOfDataStates[1] as DataState.Success).employees.employeeList.first().fullName
            )
            Assert.assertEquals(
                biography,
                (listOfDataStates[1] as DataState.Success).employees.employeeList.first().biography
            )
        }
}