package com.example.alumni.ui


data class AppUiState(
    val user: String = "",
    val userEmail : String = "",
    val userPassword: String = "",
    var fullName: String = "",
    var passingYear: String = "",
    var workDetails: String = "",
    var workExperience: String = "",
    var location: String = "",
    val amount: String = "",
    val isEmailWrong: Boolean = false,
    val isPasswordWrong: Boolean = false,
    var isStoryAdded: Boolean = false,
    var isEventAdded: Boolean = false,
    var isProfileCreated: Boolean = false
)