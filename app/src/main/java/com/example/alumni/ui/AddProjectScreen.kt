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
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.alumni.R

@Composable
fun AddProjectScreen(
    appViewModel: AppViewModel,
    onAddProjectClicked: () -> Unit,
    modifier: Modifier = Modifier
){
    val appUiState by appViewModel.uiState.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(dimensionResource(R.dimen.padding_medium))
    ) {
        Text(
            text = "Add New Project",
            style = MaterialTheme.typography.titleMedium,
            modifier = modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = dimensionResource(R.dimen.padding_small))
        )
        OutlinedTextField(
            value = appUiState.projectName,
            onValueChange = { appViewModel.setProjectName(it) },
            label = { Text("Project Name") },
            modifier = modifier
                .fillMaxWidth()
                .padding(bottom = dimensionResource(R.dimen.padding_small))
        )
        OutlinedTextField(
            value = appUiState.projectDescription,
            onValueChange = { appViewModel.setProjectDescription(it) },
            label = { Text("Project Description") },
            modifier = modifier
                .fillMaxWidth()
                .padding(bottom = dimensionResource(R.dimen.padding_small))
        )

        Button(
            modifier = modifier.align(Alignment.CenterHorizontally),
            onClick = { onAddProjectClicked() }
        ) {
            Text(
                text = stringResource(R.string.add_new_project),
                style = MaterialTheme.typography.titleMedium
            )
        }

    }
}

@Preview(showBackground = true)
@Composable
fun AddProjectScreenPreview(){
    AddProjectScreen(appViewModel = viewModel(), onAddProjectClicked = { })
}