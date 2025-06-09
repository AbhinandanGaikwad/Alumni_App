package com.example.alumni.ui.project

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.testing.TestNavHostController
import com.example.alumni.R
import com.example.alumni.ui.AppScreen
import com.example.alumni.ui.viewmodel.AppViewModel
import com.google.firebase.auth.FirebaseAuth

@Composable
fun ProjectScreen(
    appViewModel: AppViewModel,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    LaunchedEffect(Unit) {
        appViewModel.fetchProjects()
    }

    val projects = appViewModel.projects

    val expandedStates = remember { mutableStateMapOf<String, Boolean>() }

    LazyColumn {
        item {
            Text(
                text = stringResource(R.string.current_projects),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.W600,
                textAlign = TextAlign.Center,
                modifier = modifier.padding(10.dp)
            )
        }

        items(projects) { project ->
            val isExpanded = expandedStates[project.id] ?: false

            ProjectCard(
                projectName = project.name,
                projectDescription = project.description,
                projectCost = project.cost,
                isExpanded = isExpanded,
                onExpandClick = {
                    expandedStates[project.id] = !isExpanded
                },
                currentUserUid = FirebaseAuth.getInstance().currentUser?.uid ?: "",
                postedByUid = project.uid,
                onDeleteClick = {
                    appViewModel.deleteProject(project)
                    expandedStates.remove(project.id)
                }
            )
        }

        item {
            Box(
                contentAlignment = Alignment.Center,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Button(
                    onClick = { navController.navigate(AppScreen.AddProjectScreen.name) },
                    modifier = Modifier.align(Alignment.Center)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = "Add",
                        modifier = modifier.padding(end = 10.dp)
                    )
                    Text(text = "Add new project")
                }
            }
        }
    }
}

@Composable
private fun ProjectItemButton(
    expanded: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = onClick,
        modifier = modifier) {
        Icon(imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
            contentDescription = "Icon Button",
            tint = MaterialTheme.colorScheme.secondary)
    }
}


@Composable
fun ProjectCard(
    projectName: String,
    projectDescription: String,
    projectCost: String,
    isExpanded: Boolean,
    onExpandClick: () -> Unit,
    currentUserUid: String,
    postedByUid: String,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier
){

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        colors = CardDefaults.cardColors(
            containerColor =  Color.White
        ),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(modifier = Modifier.animateContentSize(
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioNoBouncy,
                stiffness = Spring.StiffnessMedium
            )
        )
        ){
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {

                Spacer(modifier = modifier.weight(1.5f))

                Text(
                    text = projectName,
                    fontWeight = FontWeight.W400,
                    fontSize = 20.sp,
                    color = Color.Black,
                )

                Spacer(modifier = modifier.weight(1f))

                ProjectItemButton(
                    expanded = isExpanded,
                    onClick = onExpandClick
                )

            }

            if (isExpanded) {
                Text(
                    text = projectDescription,
                    style = MaterialTheme.typography.labelLarge,
                    color = Color.Black,
                    modifier = modifier.padding(start = 16.dp, bottom = 8.dp)
                )
                Text(
                    text = "PROJECT COST: ₹ $projectCost /-",
                    style = MaterialTheme.typography.labelLarge,
                    color = Color.Black,
                    modifier = modifier.padding(start = 16.dp, bottom = 16.dp)
                )
                if (currentUserUid == postedByUid) {
                    TextButton(
                        onClick = onDeleteClick,
                        modifier = Modifier
                            .align(Alignment.End)
                            .padding(end = 16.dp, bottom = 8.dp)
                    ) {
                        Text(text = "Delete", color = MaterialTheme.colorScheme.error)
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ProjectScreenPreview(){
    val mockNavController = TestNavHostController(LocalContext.current)

    ProjectScreen(appViewModel = viewModel(),
        navController = mockNavController)
}

/*
items(projects) { project ->
                    val projectName = stringResource(project.projectName)
                    val isExpanded = expandedStates[project.projectName] ?: false


                    ProjectCard(
                        projectName = projectName,
                        projectDescription = stringResource(project.projectDescription),
                        isExpanded = isExpanded,
                        onExpandClick = {
                            expandedStates[project.projectName] = !isExpanded
                        },
                    )
                }
 */