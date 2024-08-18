package com.example.alumni.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class AppViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(AppUiState())
    val uiState: StateFlow<AppUiState> = _uiState.asStateFlow()
    var userEmail by mutableStateOf("")
    var userPassword by mutableStateOf("")

    fun updateEmail(changeEmail: String) {
        userEmail = changeEmail
    }

    fun updatePassword(changePassword: String) {
        userPassword = changePassword
    }
}