package com.example.alumni.ui

import androidx.compose.runtime.Composable

@Composable
fun AlumniApp(
    viewModel: AppViewModel
) {
    LoginScreen(appViewModel = viewModel)
}