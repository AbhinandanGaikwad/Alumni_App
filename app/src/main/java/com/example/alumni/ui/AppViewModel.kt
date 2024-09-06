package com.example.alumni.ui

import androidx.lifecycle.ViewModel
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

    fun setUser(userInput: String){
        _uiState.update { currentState ->
            currentState.copy(
                user = userInput
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

    fun setStoryTrue() {
        _uiState.update { currentState ->
            currentState.copy(
                isStoryAdded = true
            )
        }
    }

    fun setEventTrue() {
        _uiState.update { currentState ->
            currentState.copy(
                isEventAdded = true
            )
        }
    }

    fun setProfileTrue() {
        _uiState.update { currentState ->
            currentState.copy(
                isProfileCreated = true
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