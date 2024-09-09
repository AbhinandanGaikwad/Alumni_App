package com.example.alumni.ui

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.alumni.R
import com.example.alumni.data.Project

@Composable
fun DonationScreen(
    appViewModel: AppViewModel,
    modifier: Modifier = Modifier
){
    val appUiState by appViewModel.uiState.collectAsState()
    var expandedStatic by remember { mutableStateOf(false) }
    var expandedDynamic by remember { mutableStateOf(false) }
    var selectedProject by remember { mutableStateOf<String?>(null) }

    val projects = Project.projects



    Surface(modifier = Modifier.fillMaxSize()) {

        Column(modifier = modifier
            .fillMaxSize()
            .padding(5.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally) {

            LazyColumn {

                item { Text(
                    text = stringResource(R.string.thanks_donate),
                    fontWeight = FontWeight.ExtraBold,
                    textAlign = TextAlign.Center,
                    modifier = modifier.padding(bottom = 70.dp)
                )
                }

                item {
                    Box(modifier = modifier.fillMaxWidth()) {
                        Text(
                            text = stringResource(R.string.select_project),
                            style = MaterialTheme.typography.labelLarge,
                            fontSize = 18.sp,
                            modifier = modifier.padding(bottom = 10.dp)
                                .align(Alignment.Center)
                        )
                    }
                }

                item {
                    val projectName = stringResource(R.string.project1)
                    SelectableCard(
                        projectName = stringResource(R.string.project1) ,
                        projectDescription = stringResource(R.string.project_description),
                        isSelected = selectedProject == stringResource(R.string.project1) ,
                        isExpanded = expandedStatic,
                        onExpandClick = {
                            expandedStatic = !expandedStatic},
                        onCardClick = {
                            selectedProject = if (selectedProject == projectName) null else projectName
                            selectedProject == projectName}
                    )
                }

                if(appUiState.isProjectAdded){
                    item {
                        val projectName = appUiState.projectName
                        SelectableCard(
                            projectName = appUiState.projectName,
                            projectDescription = appUiState.projectDescription,
                            isSelected = selectedProject == appUiState.projectName,
                            isExpanded = expandedDynamic,
                            onExpandClick = {
                                expandedDynamic = !expandedDynamic},
                            onCardClick = {
                                selectedProject = if (selectedProject == projectName) null else projectName
                                selectedProject == projectName }
                        )
                    }
                }

                /*items(projects) { project ->
                    val projectName = stringResource(project.projectName)

                    SelectableCard(
                        projectName = projectName,
                        isSelected = selectedProject == projectName,
                        onClick = {
                            selectedProject = if (selectedProject == projectName) null else projectName
                        }
                    )
                }*/

                item {
                    Box(modifier = modifier.fillMaxWidth()) {
                        CurrencyAmountField(
                            value = appUiState.amount,
                            onValueChange = { newAmount -> appViewModel.setAmount(newAmount) },
                            keyboardOptions = KeyboardOptions.Default.copy(
                                keyboardType = KeyboardType.Number,
                                imeAction = ImeAction.Done
                            ),
                            currencySymbol = "₹",
                            modifier = modifier.align(Alignment.Center)
                                .padding(top = 65.dp)
                        )
                    }
                }

                item {
                    Box(modifier = modifier.fillMaxWidth()) {
                        Button(
                            onClick = { },
                            modifier = modifier
                                .padding(bottom = 16.dp)
                                .align(Alignment.Center)
                        ) {
                            Text(text = "Pay")
                        }
                    }
                }
            }

            //Spacer(modifier = modifier.weight(1f))






        }


    }

}

@Composable
fun SelectableCard(
    projectName: String,
    projectDescription: String,
    isSelected: Boolean,
    onCardClick: () -> Unit,
    isExpanded: Boolean,
    onExpandClick: () -> Unit,
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .clickable { onCardClick() },
        colors = CardDefaults.cardColors(
            containerColor =  if (isSelected) MaterialTheme.colorScheme.primaryContainer else Color.White
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

                Spacer(modifier = Modifier.weight(1.5f))

                Text(
                    text = projectName,
                    fontWeight = FontWeight.W400,
                    fontSize = 20.sp,
                    color = Color.Black,
                )

                Spacer(modifier = Modifier.weight(1f))

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
                    modifier = Modifier.padding(start = 16.dp, bottom = 16.dp)

                )
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
fun CurrencyAmountField(
    modifier: Modifier = Modifier,
    value: String,
    keyboardOptions: KeyboardOptions,
    onValueChange: (String) -> Unit,
    currencySymbol: String = "$",
    fieldWidth: Dp = 150.dp
) {
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }
    var isFocused by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = value,
        onValueChange = { newValue -> onValueChange(newValue) },
        label = { Text("Amount") },
        leadingIcon = {
            Text(
                text = currencySymbol
            )
        },
        keyboardOptions = keyboardOptions,
        keyboardActions = KeyboardActions(
            onDone = {
                focusManager.clearFocus()
            }
        ),
        singleLine = true,
        modifier = modifier
            .padding(bottom = 12.dp)
            .width(fieldWidth)
            .focusRequester(focusRequester)
            .onFocusChanged { focusState ->
                isFocused = focusState.isFocused
            }
    )
}


@Preview(showBackground = true)
@Composable
fun DonationScreenPreview(){
    DonationScreen(appViewModel = viewModel())
}

