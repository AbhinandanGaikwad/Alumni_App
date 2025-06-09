package com.example.alumni.ui.alumn_dir

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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
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
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.items
import com.example.alumni.R
import com.example.alumni.data.AlumniProfile
import com.example.alumni.ui.viewmodel.AppViewModel

@Composable
fun AlumniNetworkScreen(
    appViewModel: AppViewModel,
    modifier: Modifier = Modifier
) {
    val alumniList by appViewModel.alumniList.collectAsState()

    LaunchedEffect(Unit) {
        appViewModel.fetchAllAlumni()
    }

    var searchEntry by remember { mutableStateOf("") }
    var searchQuery by remember { mutableStateOf("") }

    val filteredAlumni = if (searchQuery.isBlank()) {
        alumniList.filter { it.type.equals("alumni", ignoreCase = true) }
    } else {
        alumniList.filter { alumni ->
            alumni.type.equals("alumni", ignoreCase = true) && (
                    alumni.fullName.contains(searchQuery, ignoreCase = true) ||
                            alumni.workDetails.contains(searchQuery, ignoreCase = true) ||
                            alumni.graduationYear.toString().contains(searchQuery, ignoreCase = true) ||
                            alumni.location.contains(searchQuery, ignoreCase = true) ||
                            alumni.linkedIn.contains(searchQuery, ignoreCase = true) ||
                            alumni.email.contains(searchQuery, ignoreCase = true) ||
                            alumni.phoneNo.contains(searchQuery, ignoreCase = true)
                    )
        }
    }

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
                onClick = {
                    searchQuery = searchEntry
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Search",
                    tint = MaterialTheme.colorScheme.secondary
                )
            }
        }

        LazyColumn {
            items(filteredAlumni) { alumni ->
                AlumniCard(alumni = alumni)
            }
        }
    }
}


@Composable
fun AlumniCard(
    alumni: AlumniProfile,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    val uriHandler = LocalUriHandler.current

    Card(
        modifier = modifier
            .padding(bottom = dimensionResource(R.dimen.padding_medium)),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
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
                        text = alumni.fullName,
                        style = MaterialTheme.typography.headlineMedium,
                        color = Color.Black
                    )
                    Text(
                        text = alumni.workDetails,
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.Black
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
                    Text("Passing Year: ${alumni.graduationYear}", style = MaterialTheme.typography.labelLarge, color = Color.Black)
                    Text("Works at: ${alumni.workDetails}", style = MaterialTheme.typography.labelLarge, color = Color.Black)
                    Text("Experience: ${alumni.workExperience} years", style = MaterialTheme.typography.labelLarge, color = Color.Black)
                    Text("Location: ${alumni.location}", style = MaterialTheme.typography.labelLarge, color = Color.Black)
                    Text("Connect with me on:", style = MaterialTheme.typography.labelLarge, color = Color.Black)

                    Row(
                        modifier = modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Start
                    ) {
                        IconButton(onClick = {
                            if (alumni.phoneNo.isNotBlank()) {
                                uriHandler.openUri("https://wa.me/${alumni.phoneNo}")
                            }
                        }) {
                            Image(
                                painter = painterResource(R.drawable.whatsapp),
                                contentDescription = "WhatsApp",
                                modifier = Modifier.size(24.dp)
                            )
                        }
                        IconButton(onClick = {
                            if (alumni.linkedIn.isNotBlank()) {
                                uriHandler.openUri(alumni.linkedIn)
                            }
                        }) {
                            Image(
                                painter = painterResource(R.drawable.linkedin),
                                contentDescription = "LinkedIn",
                                modifier = Modifier.size(24.dp)
                            )
                        }
                        IconButton(onClick = {
                            if (alumni.email.isNotBlank()) {
                                uriHandler.openUri("mailto:${alumni.email}")
                            }
                        }) {
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