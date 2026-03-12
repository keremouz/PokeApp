package com.example.pokeapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.pokeapp.R
import com.example.pokeapp.ui.language.LanguageManager
import com.example.pokeapp.ui.screens.auth.AuthViewModel
import com.example.pokeapp.ui.theme.UiConstants

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    onGoRegister: () -> Unit = {}
) {
    val vm: AuthViewModel = hiltViewModel()
    val state by vm.state.collectAsState()
    val context = LocalContext.current

    var isEnglish by remember {
        mutableStateOf(LanguageManager.getSavedLanguage(context) == "en")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(UiConstants.ScreenOuterPadding),
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = stringResource(R.string.language))

            Row(horizontalArrangement = Arrangement.spacedBy(UiConstants.SmallSpacing)) {
                Text(
                    text = if (isEnglish) {
                        stringResource(R.string.english)
                    } else {
                        stringResource(R.string.turkish)
                    }
                )

                Switch(
                    checked = isEnglish,
                    onCheckedChange = { checked ->
                        isEnglish = checked
                        LanguageManager.setLanguage(
                            context = context,
                            languageCode = if (checked) "en" else "tr"
                        )
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(UiConstants.LargeSpacing))

        Text(text = stringResource(R.string.login))

        Spacer(modifier = Modifier.height(UiConstants.MediumSpacing))

        OutlinedTextField(
            value = state.email,
            onValueChange = vm::onEmailChange,
            label = { Text(stringResource(R.string.email)) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(UiConstants.SmallSpacing))

        OutlinedTextField(
            value = state.password,
            onValueChange = vm::onPasswordChange,
            label = { Text(stringResource(R.string.password)) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(UiConstants.MediumSpacing))

        state.error?.let {
            Text(
                text = stringResource(R.string.error_prefix, it),
                color = MaterialTheme.colorScheme.error
            )
            Spacer(modifier = Modifier.height(UiConstants.SmallSpacing))
        }

        Row(horizontalArrangement = Arrangement.spacedBy(UiConstants.SmallSpacing)) {
            Button(
                onClick = { vm.login(onLoginSuccess) },
                enabled = !state.isLoading
            ) {
                Text(stringResource(R.string.login))
            }

            OutlinedButton(
                onClick = onGoRegister,
                enabled = !state.isLoading
            ) {
                Text(stringResource(R.string.create_account))
            }
        }
    }
}