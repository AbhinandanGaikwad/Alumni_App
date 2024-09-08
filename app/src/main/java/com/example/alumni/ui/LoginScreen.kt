package com.example.alumni.ui

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.alumni.R


@Composable
fun LoginScreen(
    appViewModel: AppViewModel,
    onLoginButtonClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    val appUiState by appViewModel.uiState.collectAsState()
    var passwordVisible: Boolean by remember { mutableStateOf(false) }

    Surface(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = modifier.padding(dimensionResource(R.dimen.padding_medium)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(R.drawable.logo),
                contentDescription = stringResource(R.string.logo)
            )
            Card(
                modifier = modifier
                    .padding(dimensionResource(R.dimen.padding_medium))
                    .fillMaxWidth()
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = modifier.fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(R.string.login),
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))
                    )
                    OutlinedTextField(
                        value = appUiState.userEmail,
                        singleLine = true,
                        shape = shapes.large,
                        onValueChange = {
                                newEmail->
                            appViewModel.setEmail(newEmail)
                        },
                        label = {
                            if (appUiState.isEmailWrong) {
                                Text(stringResource(R.string.wrong_email))
                            } else {
                                Text(stringResource(R.string.email_id))
                            }
                        },
                        isError = appUiState.isEmailWrong,
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Email,
                            imeAction = ImeAction.Next
                        ),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = colorScheme.surface,
                            unfocusedContainerColor = colorScheme.surface,
                            disabledContainerColor = colorScheme.surface,
                        )
                    )
                    OutlinedTextField(
                        value = appUiState.userPassword,
                        singleLine = true,
                        shape = shapes.large,
                        onValueChange = { newPassword ->
                                        appViewModel.setPassword(newPassword)
                        },
                        modifier = Modifier.padding(dimensionResource(R.dimen.padding_small)),
                        label = {
                            if (appUiState.isPasswordWrong) {
                                Text(stringResource(R.string.incorrect_password))
                            } else {
                                Text(stringResource(R.string.password))
                            }
                        },
                        isError = appUiState.isPasswordWrong,
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Password,
                            imeAction = ImeAction.Done
                        ),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = colorScheme.surface,
                            unfocusedContainerColor = colorScheme.surface,
                            disabledContainerColor = colorScheme.surface,
                        ),
                        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        trailingIcon = {
                            val image = if (passwordVisible)
                                Icons.Filled.Visibility
                            else Icons.Filled.VisibilityOff

                            // Localized description for accessibility services
                            val description = if (passwordVisible) "Hide password" else "Show password"

                            // Toggle button to hide or display password
                            IconButton(onClick = {passwordVisible = !passwordVisible}){
                                Icon(imageVector  = image, description)
                            }
                        }
                    )
                    Button(
                        onClick = { onLoginButtonClicked() },
                        modifier = modifier
                            .padding(dimensionResource(R.dimen.padding_medium))
                            .fillMaxWidth()
                    ) {
                        Text(stringResource(R.string.login))
                    }
                    Divider(
                        thickness = dimensionResource(R.dimen.thickness_divider),
                        modifier = modifier.padding(
                            start = dimensionResource(R.dimen.padding_medium),
                            end = dimensionResource(R.dimen.padding_medium)
                        )
                    )
                    GoogleButton(
                        text = "Sign Up with Google",
                        loadingText = "Creating Account...",
                        onClicked = { },
                        modifier = modifier
                            .padding(dimensionResource(R.dimen.padding_medium))
                            .fillMaxWidth()
                    )
                }
            }
        }
    }
}

@Composable
fun GoogleButton(
    modifier: Modifier = Modifier,
    text: String = "Sign Up with Google",
    loadingText: String = "Creating Account...",
    icon: Int = R.drawable.ic_google_logo,
    shape: Shape = MaterialTheme.shapes.medium,
    backgroundColor: Color = MaterialTheme.colorScheme.surface,
    progressIndicatorColor: Color = MaterialTheme.colorScheme.primary,
    onClicked: () -> Unit,
) {
    var clicked by remember { mutableStateOf(false) }
    Surface(
        modifier = modifier
            .clip(MaterialTheme.shapes.medium)
            .clickable {
                clicked = !clicked
                onClicked()
            },
        shape = shape,
        color = backgroundColor
    ) {
        Row(
            modifier = Modifier
                .padding(
                    start = 12.dp,
                    end = 16.dp,
                    top = 12.dp,
                    bottom = 12.dp
                )
                .animateContentSize(
                    animationSpec = tween(
                        durationMillis = 300,
                        easing = LinearOutSlowInEasing
                    )
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = "Google Button",
                tint = Color.Unspecified
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = if (clicked) loadingText else text)
            if (clicked) {
                Spacer(modifier = Modifier.width(16.dp))
                CircularProgressIndicator(
                    modifier = Modifier
                        .height(16.dp)
                        .width(16.dp),
                    strokeWidth = 2.dp,
                    color = progressIndicatorColor
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    LoginScreen(AppViewModel(), onLoginButtonClicked = {})
}