package com.example.alumni.Data

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

data class AppUiState(
    val userEmail : String = "",
    val userPassword: String = "",
    var fullName: String = "",
    var passingYear: String = "",
    var workDetails: String = "",
    var workExperience: String = "",
    var location: String = "",
    val isEmailWrong: Boolean = false,
    val isPasswordWrong: Boolean = false
)