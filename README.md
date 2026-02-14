# Alumni Connect App

Alumni Connect is an Android application built using **Jetpack Compose** that helps students and alumni stay connected. The app allows users to view alumni profiles, job openings, projects, events, and success stories in a clean and modern interface.

---

## Features

### Authentication

* Firebase Email/Password login and registration
* Role-based usage (Admin / Alumni / Student)

### Alumni Profiles

* Create and manage alumni profiles
* Information such as company, experience, and location

### Events

* Admin can add and delete events
* Expandable event cards
* Real-time updates

### Job Openings

* Post and view job opportunities
* Helps students find referrals and placements

### Projects

* Share and view projects
* Useful for collaboration and showcasing work

### Success Stories

* Alumni experiences and achievements
* Motivational content for students

### Search

Search alumni by:

* Name
* Passing year
* Location
* Experience

---

## Tech Stack

**Frontend**

* Kotlin
* Jetpack Compose
* Material 3

**Architecture**

* MVVM
* ViewModel
* State management

**Backend**

* Firebase Authentication
* Firebase Firestore

---

## Project Structure

```
Alumni_App/
│
├── data/                # Data models
├── ui/                  # UI screens and components
├── viewmodel/           # ViewModels
├── MainActivity.kt
└── AppViewModel.kt
```

---

## Setup Instructions

### 1. Clone the repository

```bash
git clone https://github.com/AbhinandanGaikwad/Alumni_App.git
```

### 2. Open in Android Studio

Open the project folder and allow Gradle to sync.

### 3. Configure Firebase

1. Create a Firebase project
2. Enable Authentication (Email/Password)
3. Enable Firestore Database
4. Download `google-services.json`
5. Place it in:

```
app/google-services.json
```

### 4. Run the app

Click **Run ▶** in Android Studio.

---

## Future Improvements

* In-app chat
* Notifications
* Resume sharing
* Admin analytics
