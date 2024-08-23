package com.example.alumni.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.alumni.Data.AppUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class AppViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(AppUiState())
    val uiState: StateFlow<AppUiState> = _uiState.asStateFlow()


    fun setEmail(emailInput: String){
        _uiState.update { currentState ->
            currentState.copy(
                userEmail = emailInput
            )
        }
    }

    fun setPassword(passwordInput: String){
        _uiState.update { currentState ->
            currentState.copy(
                userPassword = passwordInput
            )
        }
    }



    fun setName(nameInput: String){
        _uiState.update { currentState ->
            currentState.copy(
                fullName = nameInput
            )
        }
    }

    fun setYear(yearInput: String){
        _uiState.update { currentState ->
            currentState.copy(
                passingYear = yearInput
            )
        }
    }

    fun setWork(workInput: String){
        _uiState.update { currentState ->
            currentState.copy(
                workDetails = workInput
            )
        }
    }

    fun setExperience(experienceInput: String){
        _uiState.update { currentState ->
            currentState.copy(
                workExperience = experienceInput
            )
        }
    }

    fun setLocation(locationInput: String){
        _uiState.update { currentState ->
            currentState.copy(
                location = locationInput
            )
        }
    }

    /*fun updateEmail(changeEmail: String) {
        userEmail = changeEmail
    }

    fun updatePassword(changePassword: String) {
        userPassword = changePassword
    }*/
}