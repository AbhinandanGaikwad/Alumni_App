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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.alumni.R

@Composable
fun FeedbackScreen(
    onSubmitClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    var feedback by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxHeight()
            .padding(dimensionResource(R.dimen.padding_medium))
    ) {
        Text(
            text = stringResource(R.string.feedback_or_service),
            style = MaterialTheme.typography.titleMedium,
            modifier = modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = dimensionResource(R.dimen.padding_small))
        )
        OutlinedTextField(
            value = feedback,
            onValueChange = { feedback = it },
            modifier = modifier
                .height(400.dp)
                .padding(bottom = dimensionResource(R.dimen.padding_medium))
        )
        Button(
            modifier = modifier.align(Alignment.CenterHorizontally),
            onClick = { onSubmitClicked() }
        ) {
            Text(
                text = stringResource(R.string.submit),
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FeedbackPreview() {
    FeedbackScreen(onSubmitClicked = {})
}