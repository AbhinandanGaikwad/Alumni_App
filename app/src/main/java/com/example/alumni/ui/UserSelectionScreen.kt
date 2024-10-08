package com.example.alumni.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.alumni.R

@Composable
fun UserSelectionScreen(
    appViewModel: AppViewModel,
    onButtonClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(R.string.login_as),
                style = MaterialTheme.typography.titleMedium,
                modifier = modifier.padding(dimensionResource(R.dimen.padding_small))
            )
            Button(
                onClick = {
                    appViewModel.setUser(userInput = "alumni")
                    onButtonClick()
                },
                modifier = modifier
                    .width(156.dp)
                    .padding(dimensionResource(R.dimen.padding_small))
            ) {
                Text(text = stringResource(R.string.alumni))
            }
            Button(
                onClick = {
                    appViewModel.setUser(userInput = "student")
                    onButtonClick()
                },
                modifier = modifier
                    .width(156.dp)
                    .padding(dimensionResource(R.dimen.padding_small))
            ) {
                Text(text = stringResource(R.string.student))
            }
            Button(
                onClick = {
                    appViewModel.setUser(userInput = "college")
                    onButtonClick()
                },
                modifier = modifier
                    .width(164.dp)
                    .padding(dimensionResource(R.dimen.padding_small))
            ) {
                Text(text = stringResource(R.string.college_admin),
                    fontSize = 12.sp)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UserSelectionPreview() {
    UserSelectionScreen(appViewModel = AppViewModel(), onButtonClick = {})
}