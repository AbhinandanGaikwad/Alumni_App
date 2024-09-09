package com.example.alumni.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.unit.dp
import com.example.alumni.R

@Composable
fun AddStoryScreen(
    appViewModel: AppViewModel,
    onPostClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    val appUiState by appViewModel.uiState.collectAsState()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxHeight()
            .padding(dimensionResource(R.dimen.padding_medium))
    ) {
        Text(
            text = stringResource(R.string.add_your_story),
            style = MaterialTheme.typography.titleMedium,
            modifier = modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = dimensionResource(R.dimen.padding_small))
        )
        OutlinedTextField(
            value = appUiState.nameStory,
            onValueChange = { newText -> appViewModel.setNameStory(newText) },
            label = { Text("Enter Your Name") },
            modifier = modifier.padding(bottom = dimensionResource(R.dimen.padding_medium))
        )
        OutlinedTextField(
            value = appUiState.successStory,
            onValueChange = { newText -> appViewModel.setStory(newText) },
            label = { Text("Enter Your Story") },
            modifier = modifier
                .height(400.dp)
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
fun AddStoryScreenPreview() {
    AddStoryScreen(appViewModel = AppViewModel(), onPostClicked = {})
}