package com.example.alumni.ui


data class AppUiState(
    val user: String = "",
    val userEmail : String = "",
    val userPassword: String = "",
    var fullName: String = "",
    var passingYear: String = "",
    var workDetails: String = "",
    var position: String = "",
    var workExperience: String = "",
    var location: String = "",
    val isEmailWrong: Boolean = false,
    val isPasswordWrong: Boolean = false
)