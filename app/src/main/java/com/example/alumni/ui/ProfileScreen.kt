package com.example.alumni.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
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
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ProfileScreen(
    appViewModel: AppViewModel,
    onSubmitClicked: () -> Unit,
    modifier: Modifier = Modifier
){
    val appUiState by appViewModel.uiState.collectAsState()

    Surface(modifier = Modifier.fillMaxSize()) {
        if (appUiState.user == "alumni") {
            Column(modifier = modifier.padding(5.dp)) {

                Text(
                    text = "Full Name",
                    style = MaterialTheme.typography.labelLarge,
                    fontSize = 17.sp
                )

                FixedSizeBorderTextField(
                    value = appUiState.fullName,
                    onValueChange = { newText -> appViewModel.setName(newText) },
                    modifier = modifier.padding(bottom = 30.dp),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    )

                )

                Text(
                    text = "Email Address",
                    style = MaterialTheme.typography.labelLarge,
                    fontSize = 17.sp
                )

                FixedSizeBorderText(
                    text = appUiState.userEmail,
                    borderSize = 2.dp,
                    borderColor = Color.Black,
                    modifier = Modifier.padding(bottom = 40.dp)
                )

                Text(
                    text = "Graduation Year",
                    style = MaterialTheme.typography.labelLarge,
                    fontSize = 17.sp
                )

                FixedSizeBorderTextField(
                    value = appUiState.passingYear,
                    onValueChange = { newText -> appViewModel.setYear(newText) },
                    modifier = modifier.padding(bottom = 30.dp),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    )

                )

                Text(
                    text = "Work Details (Name of your company,business etc)",
                    style = MaterialTheme.typography.labelLarge,
                    fontSize = 17.sp
                )

                FixedSizeBorderTextField(
                    value = appUiState.workDetails,
                    onValueChange = { newText -> appViewModel.setWork(newText) },
                    modifier = modifier.padding(bottom = 30.dp),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    )
                )

                Text(
                    text = "Work Experience (in Years)",
                    style = MaterialTheme.typography.labelLarge,
                    fontSize = 17.sp
                )

                FixedSizeBorderTextField(
                    value = appUiState.workExperience,
                    onValueChange = { newText -> appViewModel.setExperience(newText) },
                    modifier = modifier.padding(bottom = 30.dp),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    )

                )

                Text(
                    text = "Location (as City,Country) ",
                    style = MaterialTheme.typography.labelLarge,
                    fontSize = 17.sp
                )

                FixedSizeBorderTextField(
                    value = appUiState.location,
                    onValueChange = { newText -> appViewModel.setLocation(newText) },
                    modifier = modifier.padding(bottom = 30.dp),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done
                    )
                )

                Button(
                    onClick = { onSubmitClicked() },
                    modifier = modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text(text = "Submit")
                }
            }
        } else {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = modifier.padding(5.dp)
            ) {
                Text(
                    text = "Enter Your Name",
                    style = MaterialTheme.typography.labelLarge,
                    fontSize = 17.sp
                )

                FixedSizeBorderTextField(
                    value = appUiState.fullName,
                    onValueChange = { newText -> appViewModel.setName(newText) },
                    modifier = modifier.padding(30.dp),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done
                    )
                )
                Button(
                    onClick = { onSubmitClicked() },
                    modifier = modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text(text = "Submit")
                }
            }
        }
    }
}



@Composable
fun FixedSizeBorderText(
    text: String,
    modifier: Modifier = Modifier,
    borderSize: Dp = 2.dp,
    borderColor: Color = Color.Black,
    containerWidth: Dp = 390.dp,
    containerHeight: Dp = 55.dp
) {
    Box(
        modifier = modifier
            .width(containerWidth)
            .height(containerHeight)
            .border(BorderStroke(borderSize, borderColor), RoundedCornerShape(4.dp))
            .padding(top = 16.dp, start = 15.dp) // Padding inside the border
    ) {
        Text(
            text = text,
            style = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.Normal
            ),
            modifier = Modifier
        )
    }
}

@Composable
fun FixedSizeBorderTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    keyboardOptions: KeyboardOptions,
    borderSize: Dp = 2.dp,
    borderColor: Color = Color.Black,
    containerWidth: Dp = 390.dp,
    containerHeight: Dp = 55.dp,
    textStyle: TextStyle = TextStyle(fontSize = 17.sp, fontWeight = FontWeight.Normal),
) {

    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }
    var isFocused by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .width(containerWidth)
            .height(containerHeight)
            .border(BorderStroke(borderSize, borderColor), RoundedCornerShape(4.dp))
    ) {
        OutlinedTextField(
            value = value,
            singleLine = true,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxSize().focusRequester(focusRequester)
                .onFocusChanged {focusState ->
                    isFocused = focusState.isFocused
                },
            textStyle = textStyle,
            shape = RoundedCornerShape(4.dp),
            keyboardOptions = keyboardOptions,
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                }
            )
        )
    }
}



@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview(){
    ProfileScreen(appViewModel = AppViewModel(), onSubmitClicked = {})
}