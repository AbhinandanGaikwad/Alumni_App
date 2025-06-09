package com.example.alumni.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.alumni.data.AlumniProfile
import com.example.alumni.data.Event
import com.example.alumni.data.Opening
import com.example.alumni.data.Project
import com.example.alumni.data.Story
import com.example.alumni.ui.viewmodel.AppUiState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class AppViewModel: ViewModel() {
    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()

    private val _uiState = MutableStateFlow(AppUiState())
    val uiState: StateFlow<AppUiState> = _uiState.asStateFlow()

    fun registerUser(onSuccess: () -> Unit, onError: (String) -> Unit) {
        val state = _uiState.value
        auth.createUserWithEmailAndPassword(state.email, state.password)
            .addOnSuccessListener {
                saveUserProfileToFirestore(onSuccess) { e -> onError(e.message ?: "Saving to Firestore failed") }
            }
            .addOnFailureListener { e ->
                onError(e.message ?: "Registration failed")
            }
    }

    fun saveUserProfileToFirestore(onSuccess: () -> Unit, onError: (Exception) -> Unit) {
        val currentUser = auth.currentUser
        val state = _uiState.value

        currentUser?.let { user ->
            val userData = hashMapOf(
                "type" to state.user,
                "email" to state.email,
                "fullName" to state.fullName
            )

            // Add alumni-specific fields only if user is alumni
            if (state.user == "alumni") {
                userData["graduationYear"] = state.passingYear
                userData["phone"] = state.phoneNo
                userData["linkedIn"] = state.linkedIn
                userData["workDetails"] = state.workDetails
                userData["workExperience"] = state.workExperience
                userData["location"] = state.location
            }

            firestore.collection("users")
                .document(user.uid)
                .set(userData)
                .addOnSuccessListener { onSuccess() }
                .addOnFailureListener { exception -> onError(exception) }
        } ?: onError(Exception("User not logged in"))
    }

    fun loginUser(onSuccess: () -> Unit, onError: (String) -> Unit) {
        val email = uiState.value.userEmail
        val password = uiState.value.userPassword

        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                _uiState.update {
                    it.copy(
                        isEmailWrong = false,
                        isPasswordWrong = false
                    )
                }
                fetchUserData()
                onSuccess()
            }
            .addOnFailureListener { exception ->
                val errorMessage = exception.message ?: "Login failed"
                _uiState.update {
                    it.copy(
                        isEmailWrong = false,
                        isPasswordWrong = true // or do more fine-grained error check
                    )
                }
                onError(errorMessage)
            }
    }

    private val _userName = mutableStateOf("")
    val userName: State<String> = _userName

    private val _userType = mutableStateOf("")
    val userType: State<String> = _userType

    init {
        fetchUserData()
    }

    private fun fetchUserData() {
        val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return

        FirebaseFirestore.getInstance()
            .collection("users") // or your collection name
            .document(uid)
            .get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    _userName.value = document.getString("fullName") ?: ""
                    _userType.value = document.getString("type") ?: ""
                }
            }
            .addOnFailureListener {
                // handle error
            }
    }

    private val _alumniList = MutableStateFlow<List<AlumniProfile>>(emptyList())
    val alumniList: StateFlow<List<AlumniProfile>> = _alumniList.asStateFlow()

    fun fetchAllAlumni() {
        firestore.collection("users")
            .whereEqualTo("type", "alumni")
            .get()
            .addOnSuccessListener { result ->
                val alumni = result.mapNotNull { doc ->
                    doc.toObject(AlumniProfile::class.java)
                }
                _alumniList.value = alumni
            }
            .addOnFailureListener { exception ->
                // Log error if needed
            }
    }

    fun saveOpeningToFirebase() {
        val openingData = hashMapOf(
            "openingName" to uiState.value.openingName,
            "companyName" to uiState.value.companyName,
            "roleName" to uiState.value.roleName,
            "requiredExperience" to uiState.value.requiredExperience,
            "contactEmail" to uiState.value.contactEmail
        )

        firestore.collection("jobOpenings")
            .add(openingData)
            .addOnSuccessListener { documentReference ->
                Log.d("AddOpening", "DocumentSnapshot added with ID: ${documentReference.id}")

                _uiState.value = _uiState.value.copy(
                    openingName = "",
                    companyName = "",
                    roleName = "",
                    requiredExperience = "",
                    contactEmail = ""
                )
            }
            .addOnFailureListener { e ->
                Log.w("AddOpening", "Error adding document", e)
            }
    }

    private val _openings = MutableStateFlow<List<Opening>>(emptyList())
    val openings: StateFlow<List<Opening>> = _openings

    init {
        fetchSuccessStories()
        fetchEventsFromFirebase()
    }
    fun fetchOpeningsFromFirebase() {
        firestore.collection("jobOpenings")
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    Log.w("ViewModel", "Listen failed.", error)
                    return@addSnapshotListener
                }
                if (snapshot != null) {
                    val openingsList = snapshot.documents.mapNotNull { doc ->
                        doc.toObject(Opening::class.java)?.copy(id = doc.id)
                    }
                    _openings.value = openingsList
                }
            }
    }

    fun postStoryToFirebase(onSuccess: () -> Unit = {}, onFailure: (Exception) -> Unit = {}) {
        val name = userName.value
        val storyText = _uiState.value.successStory
        val uid = FirebaseAuth.getInstance().currentUser?.uid ?: ""

        if (name.isBlank() || storyText.isBlank()) {
            onFailure(Exception("Name or story cannot be empty"))
            return
        }

        val story = hashMapOf(
            "name" to name,
            "story" to storyText,
            "uid" to uid,
            "timestamp" to System.currentTimeMillis()
        )

        firestore.collection("successStories")
            .add(story)
            .addOnSuccessListener {
                setStory("")
                setStoryTrue()
                fetchSuccessStories()
                onSuccess()
            }
            .addOnFailureListener { e ->
                onFailure(e)
            }
    }

    private val _successStories = mutableStateListOf<Story>()
    val successStories: List<Story> get() = _successStories

    private fun fetchSuccessStories() {
        FirebaseFirestore.getInstance()
            .collection("successStories")
            .get()
            .addOnSuccessListener { result ->
                _successStories.clear()
                for (document in result) {
                    val name = document.getString("name") ?: ""
                    val story = document.getString("story") ?: ""
                    val uid = document.getString("uid") ?: ""
                    _successStories.add(Story(name, story, uid))
                }
                Log.d("Firestore", "Fetched ${_successStories.size} stories")
            }
            .addOnFailureListener { exception ->
                Log.e("Firestore", "Error getting success stories", exception)
            }
    }

    fun deleteStory(story: Story) {
        val db = FirebaseFirestore.getInstance()
        db.collection("successStories")
            .whereEqualTo("name", story.name)
            .whereEqualTo("story", story.story)
            .whereEqualTo("uid", story.uid)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    db.collection("successStories").document(document.id).delete()
                }
                _successStories.remove(story)
                fetchSuccessStories()
            }
            .addOnFailureListener {
                Log.e("Firestore", "Failed to delete story", it)
            }
    }

    fun postEventToFirebase(
        onSuccess: () -> Unit = {},
        onFailure: (Exception) -> Unit = {}
    ) {
        val description = _uiState.value.eventDescription
        val date = _uiState.value.eventDate
        val time = _uiState.value.eventTime
        val venue = _uiState.value.eventVenue
        val uid = FirebaseAuth.getInstance().currentUser?.uid ?: ""

        if (description.isBlank() || date.isBlank() || time.isBlank() || venue.isBlank()) {
            onFailure(Exception("Please fill in all event fields"))
            return
        }

        val event = hashMapOf(
            "description" to description,
            "date" to date,
            "time" to time,
            "venue" to venue,
            "timestamp" to System.currentTimeMillis(),
            "uid" to uid
        )

        FirebaseFirestore.getInstance()
            .collection("events")
            .add(event)
            .addOnSuccessListener {
                setEventDescription("")
                setEventDate("")
                setEventTime("")
                setEventVenue("")
                onSuccess()
            }
            .addOnFailureListener { e ->
                onFailure(e)
            }
    }

    private val _events = MutableStateFlow<List<Event>>(emptyList())
    val events: StateFlow<List<Event>> = _events.asStateFlow()

    fun fetchEventsFromFirebase() {
        firestore.collection("events")
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    Log.e("Events", "Error fetching events", error)
                    return@addSnapshotListener
                }
                if (snapshot != null) {
                    val eventList = snapshot.documents.mapNotNull { doc ->
                        doc.toObject(Event::class.java)?.copy(id = doc.id)
                    }
                    _events.value = eventList
                }
            }
    }

    fun deleteEvent(event: Event) {
        firestore.collection("events")
            .document(event.id)
            .delete()
            .addOnSuccessListener {
                fetchEventsFromFirebase()
                Log.d("EventDelete", "Event deleted successfully")
            }
            .addOnFailureListener { e ->
                Log.e("EventDelete", "Error deleting event", e)
            }
    }

    fun postProjectToFirebase(onSuccess: () -> Unit = {}, onFailure: (Exception) -> Unit = {}) {
        val uid = auth.currentUser?.uid ?: return onFailure(Exception("User not logged in"))

        val project = hashMapOf(
            "name" to _uiState.value.projectName,
            "description" to _uiState.value.projectDescription,
            "cost" to _uiState.value.projectCost,
            "uid" to uid,
            "timestamp" to System.currentTimeMillis()
        )

        firestore.collection("projects")
            .add(project)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { e -> onFailure(e) }
    }

    fun fetchProjects() {
        FirebaseFirestore.getInstance().collection("projects")
            .get()
            .addOnSuccessListener { result ->
                _projects.clear()
                for (document in result) {
                    val project = Project(
                        id = document.id,
                        name = document.getString("name") ?: "",
                        description = document.getString("description") ?: "",
                        cost = document.getString("cost") ?: "",
                        uid = document.getString("uid") ?: ""
                    )
                    _projects.add(project)
                }
            }
            .addOnFailureListener {
                Log.e("Firestore", "Failed to fetch projects", it)
            }
    }

    init {
        fetchProjects()
    }

    private val _projects = mutableStateListOf<Project>()
    val projects: List<Project> get() = _projects


    fun deleteProject(project: Project) {
        FirebaseFirestore.getInstance().collection("projects")
            .document(project.id)
            .delete()
            .addOnSuccessListener {
                _projects.remove(project)
                fetchProjects()
            }
            .addOnFailureListener {
                Log.e("Firestore", "Failed to delete project", it)
            }
    }


    fun onEmailChanged(newEmail: String) {
        _uiState.update { it.copy(email = newEmail) }
    }

    fun onPasswordChanged(newPassword: String) {
        _uiState.update { it.copy(password = newPassword) }
    }

    fun onConfirmPasswordChanged(newConfirmPassword: String) {
        _uiState.update { it.copy(confirmPassword = newConfirmPassword) }
    }

    fun togglePasswordVisibility() {
        _uiState.update { it.copy(passwordVisible = !it.passwordVisible) }
    }

    fun toggleConfirmPasswordVisibility() {
        _uiState.update { it.copy(confirmPasswordVisible = !it.confirmPasswordVisible) }
    }
//
//    fun onSubmit(onValid: (String, String) -> Unit) {
//        val state = _uiState.value
//        if (
//            state.email.isNotBlank() &&
//            state.password == state.confirmPassword &&
//            state.password.isNotBlank()
//        ) {
//            onValid(state.email, state.password)
//        } else {
//            // TODO: Add validation state or error handling
//        }
//    }

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

    fun setPhone(phoneInput: String) {
        _uiState.update { currentState ->
            currentState.copy(
                phoneNo = phoneInput
            )
        }
    }

    fun setLinkedIn(linkedInInput: String) {
        _uiState.update { currentState ->
            currentState.copy(
                linkedIn = linkedInInput
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

    fun setProfileTrue() {
        _uiState.update { currentState ->
            currentState.copy(
                isProfileCreated = true
            )
        }
    }

    fun resetState() {
        _uiState.update { currentState ->
            AppUiState(
                userEmail = "",
                userPassword = "",
                user = "",
                fullName = "",
                passingYear = "",
                workDetails = "",
                workExperience = "",
                location = "",
                phoneNo = "",
                linkedIn = "",
                amount = "",
                isOpeningAdded = currentState.isOpeningAdded,
                isProjectAdded = currentState.isProjectAdded,
                projectName = currentState.projectName,
                projectDescription = currentState.projectDescription,
                openingName = currentState.openingName,
                companyName = currentState.companyName,
                roleName = currentState.roleName,
                requiredExperience = currentState.requiredExperience,
                nameStory = currentState.nameStory,
                isStoryAdded = currentState.isStoryAdded,
                isEventAdded = currentState.isEventAdded,
                successStory = currentState.successStory,
                eventDescription = currentState.eventDescription,
                eventDate = currentState.eventDate,
                eventTime = currentState.eventTime,
                eventVenue = currentState.eventVenue
            )
        }
    }

    fun setAmount(newAmount: String) {
        _uiState.update { currentState ->
            currentState.copy(
                amount = newAmount
            )
        }
    }

    fun setStory(storyInput: String) {
        _uiState.update { currentState ->
            currentState.copy(
                successStory = storyInput
            )
        }
    }

    fun setProjectCost(projectCostInput: String) {
        _uiState.update { currentState ->
            currentState.copy(
                projectCost = projectCostInput
            )
        }
    }

    fun setEventDescription(eventDescriptionInput: String) {
        _uiState.update { currentState ->
            currentState.copy(
                eventDescription = eventDescriptionInput
            )
        }
    }

    fun setEventDate(eventDateInput: String) {
        _uiState.update { currentState ->
            currentState.copy(
                eventDate = eventDateInput
            )
        }
    }

    fun setEventTime(eventTimeInput: String) {
        _uiState.update { currentState ->
            currentState.copy(
                eventTime = eventTimeInput
            )
        }
    }

    fun setEventVenue(eventVenueInput: String) {
        _uiState.update { currentState ->
            currentState.copy(
                eventVenue = eventVenueInput
            )
        }
    }

    fun setOpeningName(openingNameInput: String) {
        _uiState.update { currentState ->
            currentState.copy(
                openingName = openingNameInput
            )
        }
    }

    fun setCompanyName(companyNameInput: String) {
        _uiState.update { currentState ->
            currentState.copy(
                companyName = companyNameInput
            )
        }
    }

    fun setRoleName(roleNameInput: String) {
        _uiState.update { currentState ->
            currentState.copy(
                roleName = roleNameInput
            )
        }
    }

    fun setRequiredExperience(requiredExperienceInput: String) {
        _uiState.update { currentState ->
            currentState.copy(
                requiredExperience = requiredExperienceInput
            )
        }
    }

    fun setProjectName(projectNameInput: String){
        _uiState.update { currentState ->
            currentState.copy(
                projectName = projectNameInput
            )
        }
    }

    fun setProjectDescription(projectDescriptionInput: String){
        _uiState.update { currentState ->
            currentState.copy(
                projectDescription = projectDescriptionInput
            )
        }
    }

    fun setContactEmail(contactEmailInput: String) {
        _uiState.update { currentState ->
            currentState.copy(
                contactEmail = contactEmailInput
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