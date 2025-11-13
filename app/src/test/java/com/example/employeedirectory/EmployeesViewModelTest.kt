package com.example.employeedirectory

import com.example.employeedirectory.data.DataState
import com.example.employeedirectory.data.model.Employee
import com.example.employeedirectory.data.model.Employees
import com.example.employeedirectory.domain.EmployeesUseCase
import com.example.employeedirectory.ui.viewmodel.EmployeesViewModel
import com.example.employeedirectory.ui.viewmodel.UiState
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class EmployeesViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()
    private lateinit var viewModel: EmployeesViewModel

    private var employeesUseCase: EmployeesUseCase = mockk()

    @Before
    fun setUp() {
        viewModel = EmployeesViewModel(employeesUseCase = employeesUseCase)
    }

    @Test
    fun `when EmployeesViewModel is created, initial Ui state is Loading`() = runTest {
        Assert.assertTrue(viewModel.uiState.value is UiState.ShowLoading)
    }

    @Test
    fun `when refreshData method is called, uiState is updated to ShowData if there's a successful response from EmployeesUseCase`() =
        runTest {
            val fullName = "Puneet Chugh"
            every { employeesUseCase() } returns flowOf(
                DataState.Success(
                    employees = Employees(
                        employeeList = listOf(
                            Employee(
                                fullName = fullName,
                                biography = "",
                                emailAddress = "",
                                employeeType = "",
                                phoneNumber = "",
                                photoURLLarge = "",
                                photoURLSmall = "",
                                team = "",
                                uuid = "",
                            )
                        )
                    )
                )
            )

            viewModel.refresh()
            Assert.assertTrue(viewModel.uiState.first() is UiState.ShowData)
            Assert.assertEquals(
                fullName,
                (viewModel.uiState.first() as UiState.ShowData).employees.employeeList[0].fullName
            )
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `when refreshData method is called, flow emits uiStates Loading and Success, if the api response if Successful`() =
        runTest {
            val fullName = "Puneet Chugh"
            every { employeesUseCase() } returns flowOf(
                DataState.Loading,
                DataState.Success(
                    employees = Employees(
                        employeeList = listOf(
                            Employee(
                                fullName = fullName,
                                biography = "",
                                emailAddress = "",
                                employeeType = "",
                                phoneNumber = "",
                                photoURLLarge = "",
                                photoURLSmall = "",
                                team = "",
                                uuid = "",
                            )
                        )
                    )
                )
            )

            viewModel.refresh()
            val receivedListOfStates = mutableListOf<UiState>()
            backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
                viewModel.uiState.collectLatest { it ->
                    receivedListOfStates.add(it)
                }
            }
            Assert.assertTrue(receivedListOfStates.size == 2)
            Assert.assertTrue(receivedListOfStates.first() is UiState.ShowLoading)
            Assert.assertTrue(receivedListOfStates[1] is UiState.ShowData)
            Assert.assertEquals(
                fullName,
                (receivedListOfStates[1] as UiState.ShowData).employees.employeeList[0].fullName
            )
        }

    @Test
    fun `when refreshData method is called, uiState is updated to ShowError if there's a Error response from EmployeesUseCase`() =
        runTest {
            val errorMessage = "Returned an error response"
            every { employeesUseCase() } returns flowOf(
                DataState.Loading,
                DataState.Error(
                    exception = Throwable(errorMessage)
                )
            )

            viewModel.refresh()
            Assert.assertTrue(viewModel.uiState.first() is UiState.ShowError)
            Assert.assertEquals(
                errorMessage,
                (viewModel.uiState.first() as UiState.ShowError).error
            )
        }
}