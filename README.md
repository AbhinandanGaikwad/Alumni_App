[🧑‍🎓 Alumni Connect App
Alumni Connect is a modern Android app built with Jetpack Compose that bridges the gap between alumni and current students. It supports alumni networking, events, job openings, projects, and success stories — all managed in real time with Firebase Firestore and Authentication.

🚀 Features:

🔐 Authentication

Firebase Email/Password login & registration

User type-based routing (Admin / Alumni / Student)

🧑‍💼 Alumni Profiles

Alumni can register and create profiles

Displays work details, experience, location, contact options (LinkedIn, WhatsApp, Email)

📅 Event Management

Admins/owners can add and delete events

Events shown with expandable cards

🧠 Success Stories

Add and view inspirational alumni stories

💼 Job Openings

Post, view, and manage job openings

🚀 Projects

Alumni/students can share projects they’ve worked on

🔍 Search Functionality

Search alumni by name, graduation year, location, job, or experience

🎨 Jetpack Compose UI

Beautiful, responsive UI with clean animations and theming

🛠️ Tech Stack
Layer	Tools/Tech Used
UI	Jetpack Compose, Material3
State Mgmt	ViewModel + collectAsState()
Auth	Firebase Authentication
Database	Firebase Firestore
Image Assets	Local painterResource() (for profile/icons)
Architecture	MVVM + Firebase Realtime Integration

📁 Folder Structure
graphql
Copy
Edit
com.example.alumni
├── data
│   ├── AlumniProfile.kt
│   ├── Event.kt
│   ├── Opening.kt
│   ├── Project.kt
│   └── Story.kt
│
├── ui
│   ├── auth/                  # Login & Register screens
│   ├── dashboard/             # Main navigation screen
│   ├── event/                 # AddEventScreen, Event UI
│   ├── project/               # AddProjectScreen, ProjectScreen
│   ├── story/                 # AddStoryScreen, Story UI
│   ├── opening/               # AddOpeningScreen, Job UI
│   ├── profile/               # Alumni profile and view
│   ├── selection/             # User type selection
│   ├── feedback/              # Feedback screen (if any)
│   ├── theme/                 # UI themes and styles
│   └── AlumniNetworkScreen.kt# Main networking screen
│
├── AppViewModel.kt           # Central ViewModel for state & Firebase
├── AppUiState.kt             # Sealed class for managing UI states
└── MainActivity.kt

🔧 Setup & Run Locally
Clone the repository:
bash
Copy
Edit
git clone https://github.com/your-username/alumni-connect.git
Open in Android Studio

Setup Firebase:

Add google-services.json to app/

Enable Firestore and Auth (Email/Password) in Firebase console

Sync Gradle and Run the app! ✅
](https://github.com/AbhinandanGaikwad/Alumni_App)
