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
import com.example.alumni.R

@Composable
fun AddEventScreen(
    appViewModel: AppViewModel,
    onPostClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    val appUiState by appViewModel.uiState.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(dimensionResource(R.dimen.padding_medium))
    ) {
        Text(
            text = stringResource(R.string.add_event),
            style = MaterialTheme.typography.titleMedium,
            modifier = modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = dimensionResource(R.dimen.padding_small))
        )
        OutlinedTextField(
            value = appUiState.eventDescription,
            onValueChange = { appViewModel.setEventDescription(it) },
            label = { Text("Event Description") },
            modifier = modifier
                .fillMaxWidth()
                .padding(bottom = dimensionResource(R.dimen.padding_small))
        )
        OutlinedTextField(
            value = appUiState.eventDate,
            onValueChange = { appViewModel.setEventDate(it) },
            label = { Text("Date") },
            modifier = modifier
                .fillMaxWidth()
                .padding(bottom = dimensionResource(R.dimen.padding_small))
        )
        OutlinedTextField(
            value = appUiState.eventTime,
            onValueChange = { appViewModel.setEventTime(it) },
            label = { Text("Time") },
            modifier = modifier
                .fillMaxWidth()
                .padding(bottom = dimensionResource(R.dimen.padding_small))
        )
        OutlinedTextField(
            value = appUiState.eventVenue,
            onValueChange = { appViewModel.setEventVenue(it) },
            label = { Text("Venue") },
            modifier = modifier
                .fillMaxWidth()
                .padding(bottom = dimensionResource(R.dimen.padding_medium))
        )
        Button(
            modifier = modifier.align(Alignment.CenterHorizontally),
            onClick = { onPostClicked() }
        ) {
            Text(
                text = stringResource(R.string.post),
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddEventPreview() {
    AddEventScreen(appViewModel = AppViewModel(), onPostClicked = {})
}