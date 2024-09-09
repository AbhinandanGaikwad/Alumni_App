package com.example.alumni.ui


import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role.Companion.Button
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.alumni.R

@Composable
fun DashboardScreen(
    appViewModel: AppViewModel,
    onEditButtonClicked: () -> Unit,
    onSearchClicked: () -> Unit,
    onDonateClicked: () -> Unit,
    onAddOpeningsClicked: () -> Unit,
    onProjectClicked: () -> Unit,
    onViewOpeningsClicked: () -> Unit,
    onAddStoryClicked: () -> Unit,
    onAddEventClicked: () -> Unit,
    onFeedbackClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    val appUiState by appViewModel.uiState.collectAsState()

    Surface(
        modifier = modifier
            .padding(dimensionResource(R.dimen.padding_small))
            .fillMaxHeight()
            .verticalScroll(rememberScrollState())
    ) {
        Column {
            ProfileCard(
                profilePhoto = R.drawable.user,
                name = appUiState.fullName,
                position = if (appUiState.user == "alumni") { stringResource(R.string.alumni) } else if (appUiState.user == "college") { stringResource(R.string.college_admin) } else { stringResource(R.string.student) },
                onEditButtonClicked = onEditButtonClicked
            )
            SearchAlumniButton(onSearchClicked = onSearchClicked)
            if (appUiState.user == "alumni") {
                Row(
                    modifier = modifier.padding(
                        top = dimensionResource(R.dimen.padding_medium),
                        bottom = dimensionResource(R.dimen.padding_medium)
                    )
                ) {
                    BigSelectCard(
                        text = stringResource(R.string.make_donation),
                        onSelectClicked = onDonateClicked,
                        modifier = modifier.weight(1f)
                    )
                    Spacer(modifier = modifier.width(dimensionResource(R.dimen.padding_medium)))
                    BigSelectCard(
                        text = stringResource(R.string.add_openings),
                        onSelectClicked = onAddOpeningsClicked,
                        modifier = modifier.weight(1f)
                    )
                }
            } else if (appUiState.user == "college") {
                Row(
                    modifier = modifier.padding(
                        top = dimensionResource(R.dimen.padding_medium),
                        bottom = dimensionResource(R.dimen.padding_medium)
                    )
                ) {
                    BigSelectCard(
                        text = stringResource(R.string.projects_initiatives),
                        onSelectClicked = onProjectClicked,
                        modifier = modifier.weight(1f)
                    )
                    Spacer(modifier = modifier.width(dimensionResource(R.dimen.padding_medium)))
                    BigSelectCard(
                        text = stringResource(R.string.view_openings),
                        onSelectClicked = onViewOpeningsClicked,
                        modifier = modifier.weight(1f)
                    )
                }
            } else {
                Row(
                    modifier = modifier.padding(
                        top = dimensionResource(R.dimen.padding_medium),
                        bottom = dimensionResource(R.dimen.padding_medium)
                    )
                ) {
                    ViewOpportunitiesButton(
                        onViewOpeningsClicked = onViewOpeningsClicked
                    )
                }
            }
            SuccessStoriesPanel(
                onAddStoryClicked = onAddStoryClicked,
                appViewModel = appViewModel
            )
            EventPanel(
                onAddEventClicked = onAddEventClicked,
                appViewModel = appViewModel
            )
            Button(
                onClick = { onFeedbackClicked() },
                modifier = modifier
                    .padding(dimensionResource(R.dimen.padding_small))
                    .fillMaxWidth()
            ) {
                Text(stringResource(R.string.feedback_service))
            }
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ViewOpportunitiesButton(
    onViewOpeningsClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = { onViewOpeningsClicked() },
        modifier = modifier
            .fillMaxWidth()
            .height(44.dp)
    ) {
        Row(
            modifier = modifier.padding(dimensionResource(R.dimen.padding_small))
        ) {
            Text(
                text = "View Opportunities",
                fontSize = 20.sp,
                style = MaterialTheme.typography.titleMedium,
                modifier = modifier
                    .weight(1f)
            )
            Icon(
                imageVector = Icons.Filled.ArrowForwardIos,
                contentDescription = stringResource(R.string.search_alumni),
                modifier = modifier.fillMaxHeight()
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BigSelectCard(
    text: String,
    onSelectClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = { onSelectClicked() },
        modifier = modifier
            .height(88.dp)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.titleMedium,
            fontSize = 28.sp,
            modifier = modifier.padding(dimensionResource(R.dimen.padding_small))
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SuccessStoriesPanel(
    appViewModel: AppViewModel,
    onAddStoryClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    val appUiState by appViewModel.uiState.collectAsState()

    Column(
        modifier = modifier.padding(bottom = dimensionResource(R.dimen.padding_medium))
    ) {
        Text(
            text = stringResource(R.string.success_stories),
            style = MaterialTheme.typography.titleMedium,
            modifier = modifier.padding(bottom = dimensionResource(R.dimen.padding_small))
        )
        Card(
            modifier = modifier
                .fillMaxWidth()
                .height(168.dp)
        ) {
            if (appUiState.user == "alumni") {
                if (appUiState.isStoryAdded) {
                    Row {
                        StoryCard(appViewModel = appViewModel)
                        ElevatedCard(
                            onClick = { onAddStoryClicked() },
                            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                            modifier = modifier
                                .padding(dimensionResource(R.dimen.padding_small))
                                .height(152.dp)
                                .width(124.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Add,
                                contentDescription = stringResource(R.string.add_button),
                                modifier = modifier
                                    .size(50.dp)
                                    .align(Alignment.CenterHorizontally)
                            )
                            Text(
                                text = stringResource(R.string.add_your_story),
                                fontSize = 16.sp,
                                textAlign = TextAlign.Center,
                                modifier = modifier.align(Alignment.CenterHorizontally)
                            )
                        }
                    }
                } else {
                    ElevatedCard(
                        onClick = { onAddStoryClicked() },
                        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                        modifier = modifier
                            .padding(dimensionResource(R.dimen.padding_small))
                            .height(152.dp)
                            .width(124.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Add,
                            contentDescription = stringResource(R.string.add_button),
                            modifier = modifier
                                .size(50.dp)
                                .align(Alignment.CenterHorizontally)
                        )
                        Text(
                            text = stringResource(R.string.add_your_story),
                            fontSize = 16.sp,
                            textAlign = TextAlign.Center,
                            modifier = modifier.align(Alignment.CenterHorizontally)
                        )
                    }
                }
            } else {
                if (appUiState.isStoryAdded) { StoryCard(appViewModel = appViewModel) }
            }
        }
    }
}

@Composable
fun StoryCard(
    appViewModel: AppViewModel,
    modifier: Modifier = Modifier
) {
    val appUiState by appViewModel.uiState.collectAsState()

    ElevatedCard(
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        modifier = modifier
            .padding(dimensionResource(R.dimen.padding_small))
            .height(152.dp)
            .width(124.dp)
    ) {
        Text(
            text = appUiState.nameStory,
            style = MaterialTheme.typography.titleMedium,
            modifier = modifier.padding(dimensionResource(R.dimen.padding_small))
        )
        Text(
            text = appUiState.successStory,
            style = MaterialTheme.typography.bodyLarge,
            modifier = modifier.padding(start = dimensionResource(R.dimen.padding_small))
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventPanel(
    appViewModel: AppViewModel,
    onAddEventClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    val appUiState by appViewModel.uiState.collectAsState()
    Column(
        modifier = modifier.padding(bottom = dimensionResource(R.dimen.padding_medium))
    ) {
        Text(
            text = stringResource(R.string.events),
            style = MaterialTheme.typography.titleMedium,
            modifier = modifier.padding(bottom = dimensionResource(R.dimen.padding_small))
        )
        Card(
            modifier = modifier
                .fillMaxWidth()
                .height(168.dp)
        ) {
            if (appUiState.user == "alumni") {
                if (appUiState.isEventAdded) { EventCard(appViewModel = appViewModel) }
            } else {
                if (appUiState.isEventAdded) {
                    Row {
                        EventCard(appViewModel = appViewModel)
                        ElevatedCard(
                            onClick = { onAddEventClicked() },
                            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                            modifier = modifier
                                .padding(dimensionResource(R.dimen.padding_small))
                                .height(152.dp)
                                .width(124.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Add,
                                contentDescription = stringResource(R.string.add_button),
                                modifier = modifier
                                    .size(50.dp)
                                    .align(Alignment.CenterHorizontally)
                            )
                            Text(
                                text = stringResource(R.string.add_event),
                                fontSize = 16.sp,
                                textAlign = TextAlign.Center,
                                modifier = modifier.align(Alignment.CenterHorizontally)
                            )
                        }
                    }
                } else {
                    ElevatedCard(
                        onClick = { onAddEventClicked() },
                        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                        modifier = modifier
                            .padding(dimensionResource(R.dimen.padding_small))
                            .height(152.dp)
                            .width(124.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Add,
                            contentDescription = stringResource(R.string.add_button),
                            modifier = modifier
                                .size(50.dp)
                                .align(Alignment.CenterHorizontally)
                        )
                        Text(
                            text = stringResource(R.string.add_event),
                            fontSize = 16.sp,
                            textAlign = TextAlign.Center,
                            modifier = modifier.align(Alignment.CenterHorizontally)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun EventCard(
    appViewModel: AppViewModel,
    modifier: Modifier = Modifier
) {
    val appUiState by appViewModel.uiState.collectAsState()

    ElevatedCard(
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        modifier = modifier
            .padding(dimensionResource(R.dimen.padding_small))
            .height(152.dp)
            .width(124.dp)
    ) {
        Column(
            modifier = modifier.padding(dimensionResource(R.dimen.padding_small))
        ) {
            Text(
                text = "Event: ",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = appUiState.eventDescription,
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = "Date: ",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = appUiState.eventDate,
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = "Time: ",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = appUiState.eventTime,
                style = MaterialTheme.typography.bodyLarge
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
        onSearchClicked = {},
        onDonateClicked = {},
        onAddOpeningsClicked = {},
        onProjectClicked = {},
        onViewOpeningsClicked = {},
        onAddStoryClicked = {},
        onAddEventClicked = {},
        onFeedbackClicked = {}
    )
}