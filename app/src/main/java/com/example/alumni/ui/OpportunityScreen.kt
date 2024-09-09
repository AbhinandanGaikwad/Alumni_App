package com.example.alumni.ui

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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


@Composable
fun OpportunityScreen(
    appViewModel: AppViewModel,
    navController: NavController,
    modifier: Modifier = Modifier
){
    val appUiState by appViewModel.uiState.collectAsState()
    var expandedStatic by remember { mutableStateOf(false) }
    var expandedDynamic by remember { mutableStateOf(false) }



    Surface(modifier = modifier.fillMaxSize()) {
        Column(modifier = modifier
            .fillMaxSize()
            .padding(5.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally) {

            LazyColumn {

                item {
                    Text(
                        text = stringResource(R.string.opportunities),
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.W600,
                        textAlign = TextAlign.Center,
                        modifier = modifier.padding(10.dp)
                    )
                }

                item {
                    OpeningCard(
                        openingName = stringResource(R.string.opening1) ,
                        companyName = "Company Name: Nexus Architects",
                        roleName = "Role Name: Software Developer",
                        requiredExperience = "Required Work Experience: 3 years",
                        isOpeningExpanded = expandedStatic,
                        onOpeningExpandClick = { expandedStatic = !expandedStatic}
                    )
                }

                if(appUiState.isOpeningAdded){
                    item {
                        OpeningCard(
                            openingName = appUiState.openingName,
                            companyName = "Company Name: ${appUiState.companyName}",
                            roleName = "Role Name: ${appUiState.roleName}",
                            requiredExperience = "Required Work Experience: ${appUiState.requiredExperience}",
                            isOpeningExpanded = expandedDynamic,
                            onOpeningExpandClick = { expandedDynamic = !expandedDynamic}
                        )
                    }
                }

                if(appUiState.user == "alumni") {
                    item {
                        Box(
                            modifier = modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                                .align(Alignment.CenterHorizontally)
                        ){
                            Button(
                                onClick = { navController.navigate(AppScreen.AddOpeningScreen.name) },
                                modifier = Modifier.align(Alignment.Center)
                            ) {

                                Icon(
                                    imageVector = Icons.Filled.Add,
                                    contentDescription = "Add",
                                    modifier = modifier.padding(end = 10.dp)
                                )

                                Text(text = "Add new opening")


                            }
                        }
                    }
                }

            }

        }
    }

}

@Composable
private fun OpportunityItemButton(
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
fun OpeningCard(
    openingName: String,
    companyName: String,
    roleName: String,
    requiredExperience: String,
    isOpeningExpanded: Boolean,
    onOpeningExpandClick: () -> Unit,
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
                    text = openingName,
                    fontWeight = FontWeight.W400,
                    color = Color.Black,
                    fontSize = 20.sp,
                )

                Spacer(modifier = modifier.weight(1f))

                OpportunityItemButton(
                    expanded = isOpeningExpanded,
                    onClick = onOpeningExpandClick
                )


            }

            if (isOpeningExpanded) {

                Column {
                    Text(
                        text = companyName,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Black,
                        modifier = modifier.padding(start = 16.dp, bottom = 16.dp)

                    )
                    Text(
                        text = roleName,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Black,
                        modifier = modifier.padding(start = 16.dp, bottom = 16.dp)

                    )
                    Text(
                        text = requiredExperience,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Black,
                        modifier = modifier.padding(start = 16.dp, bottom = 16.dp)

                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun OpportunityScreenPreview(){

    val mockNavController = TestNavHostController(LocalContext.current)

    OpportunityScreen(appViewModel = viewModel(),
        navController = mockNavController)
}

/*
val expandedOpeningStates = remember { mutableStateMapOf<Int, Boolean>() }
    val openings = Opening.openings

items(openings) { opening ->
                    val openingName = stringResource(opening.openingName)
                    val isOpeningExpanded = expandedOpeningStates[opening.openingName] ?: false


                    OpeningCard(
                        openingName = openingName,
                        openingDescription = stringResource(opening.openingDescription),
                        isOpeningExpanded = isOpeningExpanded,
                        onOpeningExpandClick = {
                            expandedOpeningStates[opening.openingName] = !isOpeningExpanded
                        }
                    )
                }*/