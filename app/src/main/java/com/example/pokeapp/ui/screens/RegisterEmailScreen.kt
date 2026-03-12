package com.example.pokeapp.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.pokeapp.R
import com.example.pokeapp.ui.screens.auth.AuthViewModel
import com.example.pokeapp.ui.theme.UiConstants

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterEmailScreen(
    onBack: () -> Unit,
    onRegisterSuccess: () -> Unit
) {
    val vm: AuthViewModel = hiltViewModel()
    val state by vm.state.collectAsState()

    val canContinue = state.email.isNotBlank() && state.password.isNotBlank() && !state.isLoading

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.create_account_title),
                        fontWeight = FontWeight.SemiBold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = stringResource(R.string.back)
                        )
                    }
                }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(horizontal = UiConstants.ScreenOuterPadding)
                .fillMaxSize()
        ) {
            Spacer(Modifier.height(UiConstants.ScreenOuterPadding))

            Text(
                text = stringResource(R.string.lets_start),
                style = MaterialTheme.typography.headlineSmall
            )

            Text(
                text = stringResource(R.string.what_is_your_email),
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(UiConstants.CardPadding))

            OutlinedTextField(
                value = state.email,
                onValueChange = vm::onEmailChange,
                label = { Text(stringResource(R.string.email_hint)) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(Modifier.height(UiConstants.SmallSpacing))

            Text(
                text = stringResource(R.string.use_valid_email),
                style = MaterialTheme.typography.bodySmall
            )

            Spacer(Modifier.height(UiConstants.CardPadding))

            var showPassword by remember { mutableStateOf(false) }

            OutlinedTextField(
                value = state.password,
                onValueChange = vm::onPasswordChange,
                label = { Text(stringResource(R.string.password_hint)) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                visualTransformation = if (showPassword) {
                    VisualTransformation.None
                } else {
                    PasswordVisualTransformation()
                },
                trailingIcon = {
                    TextButton(onClick = { showPassword = !showPassword }) {
                        Text(
                            text = if (showPassword) {
                                stringResource(R.string.hide)
                            } else {
                                stringResource(R.string.show)
                            }
                        )
                    }
                }
            )

            state.error?.let { err ->
                Spacer(Modifier.height(UiConstants.MediumSpacing))
                Text(
                    text = err,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Spacer(Modifier.weight(1f))

            Button(
                onClick = { vm.register(onRegisterSuccess) },
                enabled = canContinue,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(UiConstants.ButtonHeight),
                shape = MaterialTheme.shapes.extraLarge
            ) {
                if (state.isLoading) {
                    CircularProgressIndicator(
                        strokeWidth = UiConstants.LoadingStrokeWidth,
                        modifier = Modifier.size(UiConstants.LoadingSize)
                    )
                } else {
                    Text(stringResource(R.string.continue_text))
                }
            }

            Spacer(Modifier.height(UiConstants.LargeSpacing))
        }
    }
}