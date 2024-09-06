package com.example.alumni.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.alumni.R

enum class AppScreen(@StringRes val title: Int){
    Login(title = R.string.login_screen),
    UserSelection(title = R.string.select_user),
    ProfileUpdate(title = R.string.profile_update),
    DashboardScreen(title = R.string.dashboard)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlumniAppBar(
    currentScreen: AppScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    CenterAlignedTopAppBar(
        title = { Text(stringResource(currentScreen.title)) },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        }
    )
}

@Composable
fun AlumniApp(
    //viewModel: AppViewModel
) {
    val navController = rememberNavController()
    val viewModel: AppViewModel = viewModel()
    val appUiState by viewModel.uiState.collectAsState()

    val backStackEntry by navController.currentBackStackEntryAsState()
    // Get the name of the current screen
    val currentScreen = AppScreen.valueOf(
        backStackEntry?.destination?.route ?: AppScreen.Login.name
    )

    Scaffold(topBar = {
        AlumniAppBar(currentScreen = currentScreen,
            canNavigateBack = navController.previousBackStackEntry != null,
            navigateUp = { navController.navigateUp() }
        )
    }
    ) { innerPadding ->
        NavHost(navController = navController,
            startDestination = AppScreen.Login.name,
            modifier = Modifier.padding(innerPadding)) {
            composable(route = AppScreen.Login.name){
                LoginScreen(
                    appViewModel = viewModel,
                    onLoginButtonClicked = {
                        if (appUiState.isProfileCreated) {
                            navController.navigate(AppScreen.DashboardScreen.name)
                        } else {
                            navController.navigate(AppScreen.UserSelection.name)
                        }
                    }
                )
            }

            composable(route = AppScreen.UserSelection.name) {
                UserSelectionScreen(
                    appViewModel = viewModel,
                    onButtonClick = { navController.navigate(AppScreen.ProfileUpdate.name) }
                )
            }

            composable(route = AppScreen.ProfileUpdate.name) {
                ProfileScreen(
                    appViewModel = viewModel,
                    onSubmitClicked = {
                        navController.navigate(AppScreen.DashboardScreen.name)
                        viewModel.setProfileTrue()
                    }
                )
            }

            composable(route = AppScreen.DashboardScreen.name) {
                DashboardScreen(
                    appViewModel = viewModel,
                    onEditButtonClicked = { navController.navigate(AppScreen.ProfileUpdate.name) },
                    onSearchClicked = { /*TODO*/ },
                    onDonateClicked = { /*TODO*/ },
                    onAddOpeningsClicked = { /*TODO*/ },
                    onProjectClicked = { /*TODO*/ },
                    onViewOpeningsClicked = { /*TODO*/ },
                    onAddStoryClicked = { /*TODO*/ },
                    onAddEventClicked = { /*TODO*/ }
                )
            }
        }
    }

    /*NavHost(navController = navController,
        startDestination = AppScreen.Login.name) {
        composable(route = AppScreen.Login.name){
            LoginScreen(appViewModel = viewModel,
                onLoginButtonClicked = { navController.navigate(AppScreen.ProfileUpdate.name) })
        }

        composable(route = AppScreen.ProfileUpdate.name){
            ProfileScreen(appViewModel = viewModel)
        }
    }*/

}