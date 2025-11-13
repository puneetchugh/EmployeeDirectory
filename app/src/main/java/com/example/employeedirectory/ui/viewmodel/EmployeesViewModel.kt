package com.example.employeedirectory.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.employeedirectory.data.DataState
import com.example.employeedirectory.data.model.Employees
import com.example.employeedirectory.domain.EmployeesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EmployeesViewModel @Inject constructor(
    val employeesUseCase: EmployeesUseCase
) : ViewModel() {

    private val trigger = MutableSharedFlow<Unit>(replay = 1)

    @OptIn(ExperimentalCoroutinesApi::class)
    val uiState = trigger.flatMapLatest { _ ->
        employeesUseCase().map {
            when (it) {
                is DataState.Error -> UiState.ShowError(
                    // Error could be reported to analytics from the viewmodel layer
                    error = it.exception.message ?: it.code.toString()
                )

                is DataState.Success -> UiState.ShowData(employees = it.employees)
                is DataState.Loading -> UiState.ShowLoading
            }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted
            .WhileSubscribed(stopTimeoutMillis = 5000),
        initialValue = UiState.ShowLoading
    )

    init {
        refresh()
    }

    fun refresh() {
        viewModelScope.launch {
            trigger.emit(Unit)
        }
    }
}

sealed class UiState {
    data class ShowData(val employees: Employees) : UiState()
    data class ShowError(val error: String) : UiState()
    data object ShowLoading : UiState()
}