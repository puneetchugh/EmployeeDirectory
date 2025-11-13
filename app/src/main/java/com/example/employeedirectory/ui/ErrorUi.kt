package com.example.employeedirectory.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.employeedirectory.R

@Composable
fun ErrorUi(
    modifier: Modifier = Modifier,
    message: String
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize()
    ) {
        Column {
            Image(
                modifier = Modifier
                    .align(alignment = Alignment.CenterHorizontally)
                    .background(color = Color.LightGray)
                    .size(128.dp),
                alignment = Alignment.Center,
                painter = painterResource(android.R.drawable.stat_notify_error),
                contentDescription = "error: $message"
            )
            Spacer(modifier = Modifier.size(16.dp))
            Text(
                text = message,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Red,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
@Preview
fun PreviewErrorUi() {
    ErrorUi(
        message = stringResource(R.string.error_state)
    )
}