package com.example.alumni.ui

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.alumni.R


@Composable
fun DashboardScreen(
    appViewModel: AppViewModel,
    onEditButtonClicked: () -> Unit,
    onSearchClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    val appUiState by appViewModel.uiState.collectAsState()

    Surface(
        modifier = modifier
            .padding(dimensionResource(R.dimen.padding_small))
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Column {
            ProfileCard(
                profilePhoto = R.drawable.user,
                name = appUiState.fullName,
                position = appUiState.position,
                onEditButtonClicked = onEditButtonClicked
            )
            SearchAlumniButton(onSearchClicked = onSearchClicked)
        }
    }
}

@Composable
fun ProfileCard(
    @DrawableRes profilePhoto: Int,
    name: String,
    position: String,
    onEditButtonClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .height(100.dp)
            .padding(bottom = dimensionResource(R.dimen.padding_medium))
    ) {
        Row {
            Image(
                painter = painterResource(profilePhoto),
                contentDescription = stringResource(R.string.profile_photo),
                modifier = modifier
                    .padding(dimensionResource(R.dimen.padding_small))
                    .fillMaxHeight()
            )
            Column(
                modifier = Modifier
                    .padding(dimensionResource(R.dimen.padding_small))
                    .weight(1f)
            ) {
                Text(
                    text = name,
                    style = MaterialTheme.typography.headlineMedium
                )
                Text(
                    text = position,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            IconButton(
                onClick = { onEditButtonClicked() },
                modifier = modifier
                    .padding(dimensionResource(R.dimen.padding_small))
                    .fillMaxHeight()
            ) {
                Icon(
                    imageVector = Icons.Filled.Edit,
                    contentDescription = stringResource(R.string.edit_profile)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchAlumniButton(
    onSearchClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = { onSearchClicked() },
        modifier = modifier
            .fillMaxWidth()
            .height(44.dp)
    ) {
        Row(
            modifier = modifier.padding(dimensionResource(R.dimen.padding_small))
        ) {
            Text(
                text = stringResource(R.string.search_alumni_network),
                fontSize = 20.sp,
                style = MaterialTheme.typography.titleMedium,
                modifier = modifier
                    .weight(1f)
            )
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = stringResource(R.string.search_alumni),
                modifier = modifier.fillMaxHeight()
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DashboardPreview() {
    DashboardScreen(
        AppViewModel(),
        onEditButtonClicked = {},
        onSearchClicked = {}
    )
}