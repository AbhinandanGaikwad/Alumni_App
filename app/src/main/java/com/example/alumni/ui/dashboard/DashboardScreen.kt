package com.example.alumni.ui.dashboard


import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.alumni.R
import androidx.compose.foundation.lazy.items
import com.example.alumni.data.Event
import com.example.alumni.data.Story
import com.example.alumni.ui.viewmodel.AppViewModel
import com.google.firebase.auth.FirebaseAuth

@Composable
fun DashboardScreen(
    appViewModel: AppViewModel,
    onEditButtonClicked: () -> Unit,
    onSearchClicked: () -> Unit,
    onDonateClicked: () -> Unit,
    onProjectClicked: () -> Unit,
    onViewOpeningsClicked: () -> Unit,
    onAddStoryClicked: () -> Unit,
    onAddEventClicked: () -> Unit,
    onFeedbackClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    val userName by appViewModel.userName
    val userType by appViewModel.userType

    Surface(
        modifier = modifier
            .padding(dimensionResource(R.dimen.padding_small))
            .fillMaxHeight()
            .verticalScroll(rememberScrollState())
    ) {
        Column {
            ProfileCard(
                profilePhoto = R.drawable.user,
                name = userName,
                position = when (userType) {
                    "alumni" -> stringResource(R.string.alumni)
                    "college" -> stringResource(R.string.college_admin)
                    else -> stringResource(R.string.student)
                },
//                onEditButtonClicked = onEditButtonClicked
            )
            SearchAlumniButton(onSearchClicked = onSearchClicked)
            if (userType == "alumni") {
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
                        onSelectClicked = onViewOpeningsClicked,
                        modifier = modifier.weight(1f)
                    )
                }
            } else if (userType == "college") {
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
//    onEditButtonClicked: () -> Unit,
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
//            IconButton(
//                onClick = { onEditButtonClicked() },
//                modifier = modifier
//                    .padding(dimensionResource(R.dimen.padding_small))
//                    .fillMaxHeight()
//            ) {
//                Icon(
//                    imageVector = Icons.Filled.Edit,
//                    contentDescription = stringResource(R.string.edit_profile)
//                )
//            }
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
    val userType by appViewModel.userType

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
            LazyRow(
                modifier = modifier
                    .fillMaxWidth()
                    .height(180.dp),
                contentPadding = PaddingValues(horizontal = 8.dp)
            ) {
                items(appViewModel.successStories) { story ->
                    StoryCard(
                        appViewModel = appViewModel,
                        story = story
                    )
                }

                if (userType == "alumni") {
                    item {
                        ElevatedCard(
                            onClick = onAddStoryClicked,
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
                                modifier = modifier
                                    .align(Alignment.CenterHorizontally)
                                    .padding(4.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StoryCard(
    appViewModel: AppViewModel,
    story: Story,
    modifier: Modifier = Modifier
) {
    var showDialog by remember { mutableStateOf(false) }

    ElevatedCard(
        onClick = { showDialog = true },
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        modifier = modifier
            .padding(dimensionResource(R.dimen.padding_small))
            .height(152.dp)
            .width(200.dp)
    ) {
        Column(
            modifier = Modifier.padding(dimensionResource(R.dimen.padding_small))
        ) {
            Text(
                text = story.name,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = story.story,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 4,
                overflow = TextOverflow.Ellipsis
            )
        }
    }

    if (showDialog) {
        val currentUserUid = FirebaseAuth.getInstance().currentUser?.uid
        val isOwner = story.uid == currentUserUid
        val isAdmin = appViewModel.userType.value == "college"

        AlertDialog(
            onDismissRequest = { showDialog = false },
            confirmButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Close")
                }
            },
            dismissButton = {
                if (isOwner || isAdmin) {
                    TextButton(
                        onClick = {
                            appViewModel.deleteStory(story)
                            showDialog = false
                        }
                    ) {
                        Text("Delete", color = MaterialTheme.colorScheme.error)
                    }
                }
            },
            title = { Text(text = story.name) },
            text = { Text(text = story.story) }
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
    val userType by appViewModel.userType
    val events by appViewModel.events.collectAsState()

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
            LazyRow(
                modifier = modifier
                    .fillMaxWidth()
                    .height(180.dp),
                contentPadding = PaddingValues(horizontal = 8.dp)
            ) {
                items(events) { event ->
                    EventCard(appViewModel = appViewModel, event = event)
                }

                if (userType == "college" || userType == "student") {
                    item {
                        ElevatedCard(
                            onClick = onAddEventClicked,
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
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventCard(
    appViewModel: AppViewModel,
    event: Event,
    modifier: Modifier = Modifier
) {
    var showDialog by remember { mutableStateOf(false) }

    ElevatedCard(
        onClick = { showDialog = true },
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        modifier = modifier
            .padding(dimensionResource(R.dimen.padding_small))
            .height(152.dp)
            .width(200.dp)
    ) {
        Column(modifier = Modifier.padding(dimensionResource(R.dimen.padding_small))) {
            Text("Event:", style = MaterialTheme.typography.titleMedium)
            Text(event.description, style = MaterialTheme.typography.bodyLarge, maxLines = 1, overflow = TextOverflow.Ellipsis)

            Spacer(Modifier.height(4.dp))

            Text("Date: ${event.date}", style = MaterialTheme.typography.bodySmall)
            Text("Time: ${event.time}", style = MaterialTheme.typography.bodySmall)
            Text("Venue: ${event.venue}", style = MaterialTheme.typography.bodySmall)
        }
    }

    if (showDialog) {
        val currentUserUid = FirebaseAuth.getInstance().currentUser?.uid
        val isOwner = event.uid == currentUserUid
        val isAdmin = appViewModel.userType.value == "college"

        AlertDialog(
            onDismissRequest = { showDialog = false },
            confirmButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Close")
                }
            },
            dismissButton = {
                if (isOwner || isAdmin) {
                    TextButton(
                        onClick = {
                            appViewModel.deleteEvent(event)
                            showDialog = false
                        }
                    ) {
                        Text("Delete", color = MaterialTheme.colorScheme.error)
                    }
                }
            },
            title = { Text(text = "Event Details") },
            text = {
                Text("${event.description}\n\nDate: ${event.date}\nTime: ${event.time}\nVenue: ${event.venue}")
            }
        )
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
        onProjectClicked = {},
        onViewOpeningsClicked = {},
        onAddStoryClicked = {},
        onAddEventClicked = {},
        onFeedbackClicked = {}
    )
}