package com.example.employeedirectory.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.employeedirectory.R
import com.example.employeedirectory.ui.viewmodel.EmployeesViewModel
import com.example.employeedirectory.ui.viewmodel.UiState
import com.example.employeedirectory.ui.viewmodel.UiState.ShowError

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(employeesViewModel: EmployeesViewModel) {

    val uiState = employeesViewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        modifier = Modifier.background(Color.Transparent),
                        text = stringResource(R.string.title),
                        fontWeight = FontWeight.Bold,

                        )
                })
        },
        content = { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                when (uiState.value) {
                    is UiState.ShowData -> EmployeeListUi(
                        modifier = Modifier.padding(innerPadding),
                        employees = (uiState.value as UiState.ShowData).employees
                    )

                    is UiState.ShowLoading -> LoadingUi(
                        modifier = Modifier
                            .padding(innerPadding)
                    )

                    is ShowError -> ErrorUi(
                        modifier = Modifier
                            .padding(innerPadding),
                        message = stringResource(R.string.error_state)
                    )
                }
                FloatingActionButton(
                    modifier = Modifier
                        .offset(x = (-32).dp, y = (-32).dp)
                        .align(alignment = Alignment.BottomEnd),
                    onClick = { employeesViewModel.refresh() },
                    shape = CircleShape,
                    content = {
                        Text(
                            modifier = Modifier.padding(16.dp),
                            text = stringResource(R.string.refresh_button),
                            fontWeight = FontWeight.Bold,
                            color = Color.Blue
                        )
                    }
                )
            }
        }
    )
}

@Composable
@Preview
fun PreviewMainScreen() {
    //MainScreen()
}

