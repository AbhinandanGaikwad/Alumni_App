package com.example.alumni.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.testing.TestNavHostController
import com.example.alumni.R

@Composable
fun AddOpeningScreen(
    appViewModel: AppViewModel,
    onAddClicked: () -> Unit,
    modifier: Modifier = Modifier
){

    val appUiState by appViewModel.uiState.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(dimensionResource(R.dimen.padding_medium))
    ) {
        Text(
            text = "Add Opening",
            style = MaterialTheme.typography.titleMedium,
            modifier = modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = dimensionResource(R.dimen.padding_small))
        )
        OutlinedTextField(
            value = appUiState.openingName,
            onValueChange = { appViewModel.setOpeningName(it) },
            label = { Text("Opening Name") },
            modifier = modifier
                .fillMaxWidth()
                .padding(bottom = dimensionResource(R.dimen.padding_small))
        )
        OutlinedTextField(
            value = appUiState.companyName,
            onValueChange = { appViewModel.setCompanyName(it) },
            label = { Text("Company Name") },
            modifier = modifier
                .fillMaxWidth()
                .padding(bottom = dimensionResource(R.dimen.padding_small))
        )
        OutlinedTextField(
            value = appUiState.roleName,
            onValueChange = { appViewModel.setRoleName(it) },
            label = { Text("Role Name") },
            modifier = modifier
                .fillMaxWidth()
                .padding(bottom = dimensionResource(R.dimen.padding_small))
        )

        OutlinedTextField(
            value = appUiState.requiredExperience,
            onValueChange = { appViewModel.setRequiredExperience(it) },
            label = { Text("Required Work Experience") },
            modifier = modifier
                .fillMaxWidth()
                .padding(bottom = dimensionResource(R.dimen.padding_small))
        )

        Button(
            modifier = modifier.align(Alignment.CenterHorizontally),
            onClick = { onAddClicked() }
        ) {
            Text(
                text = stringResource(R.string.add_new_opening),
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddOpeningPreview() {

    AddOpeningScreen(appViewModel = AppViewModel(), onAddClicked = { })
}