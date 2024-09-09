package com.example.alumni.ui

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.UriHandler
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.alumni.R

@Composable
fun AlumniNetworkScreen(
    appViewModel: AppViewModel,
    modifier: Modifier = Modifier
) {
    val appUiState by appViewModel.uiState.collectAsState()
    var searchEntry by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(dimensionResource(R.dimen.padding_small))
    ) {
        Row {
            TextField(
                value = searchEntry,
                onValueChange = { searchEntry = it },
                label = { Text("Search") },
                modifier = modifier
                    .weight(1f)
                    .padding(
                        dimensionResource(R.dimen.padding_small),
                        bottom = dimensionResource(R.dimen.padding_medium)
                    )
            )
            IconButton(
                onClick = { }
            ) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Search",
                    tint = MaterialTheme.colorScheme.secondary
                )
            }
        }
        if (appUiState.user == "alumni"){
            AlumniCard(
                name = appUiState.fullName,
                email = appUiState.userEmail,
                passingYear = appUiState.passingYear,
                workDetails = appUiState.workDetails,
                experience = appUiState.workExperience,
                location = appUiState.location,
                phoneNo = appUiState.phoneNo,
                linkedIn = appUiState.linkedIn
            )
        }
    }
}

@Composable
fun AlumniCard(
    name: String,
    email: String,
    passingYear: String,
    workDetails: String,
    experience: String,
    location: String,
    phoneNo: String,
    linkedIn: String,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    val uriHandler = LocalUriHandler.current

    Card(
        modifier = modifier
            .padding(bottom = dimensionResource(R.dimen.padding_medium)),
        colors = CardDefaults.cardColors(
            containerColor =  Color.White
        ),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(
            modifier = Modifier.animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioNoBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(104.dp)
                        .clip(MaterialTheme.shapes.small)
                        .padding(dimensionResource(R.dimen.padding_small))
                ) {
                    Image(
                        painter = painterResource(R.drawable.user),
                        contentScale = ContentScale.Crop,
                        contentDescription = stringResource(R.string.profile_photo),
                    )
                }
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
                        text = workDetails,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
                IconButton(
                    onClick = { expanded = !expanded },
                    modifier = modifier
                ) {
                    Icon(
                        imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                        contentDescription = "Icon Button",
                        tint = MaterialTheme.colorScheme.secondary
                    )
                }
            }
            if (expanded) {
                Column(
                    modifier = modifier.padding(dimensionResource(R.dimen.padding_small))
                ) {
                    Text(
                        text = "Passing Year: $passingYear",
                        style = MaterialTheme.typography.labelLarge
                    )
                    Text(
                        text = "Works at: $workDetails",
                        style = MaterialTheme.typography.labelLarge
                    )
                    Text(
                        text = "Experience: $experience",
                        style = MaterialTheme.typography.labelLarge
                    )
                    Text(
                        text = "Location: $location",
                        style = MaterialTheme.typography.labelLarge
                    )
                    Text(
                        text = "Connect with me on: ",
                        style = MaterialTheme.typography.labelLarge
                    )
                    Row(
                        modifier = modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Start
                    ) {
                        IconButton(
                            onClick = { uriHandler.openUri("https://wa.me/$phoneNo") }
                        ) {
                            Image(
                                painter = painterResource(R.drawable.whatsapp),
                                contentDescription = "WhatsApp",
                                modifier = Modifier.size(24.dp)
                            )
                        }
                        IconButton(
                            onClick = { uriHandler.openUri(linkedIn) },
                            modifier = modifier
                        ) {
                            Image(
                                painter = painterResource(R.drawable.linkedin),
                                contentDescription = "LinkedIn",
                                modifier = Modifier.size(24.dp)
                            )
                        }
                        IconButton(
                            onClick = { uriHandler.openUri("mailto:$email") },
                            modifier = modifier
                        ) {
                            Image(
                                painter = painterResource(R.drawable.gmail),
                                contentDescription = "Email",
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AlumniNetworkScreenPreview() {
    AlumniNetworkScreen(
        appViewModel = AppViewModel()
    )
}