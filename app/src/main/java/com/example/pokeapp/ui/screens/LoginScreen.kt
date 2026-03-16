package com.example.pokeapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.pokeapp.R
import com.example.pokeapp.ui.components.LoadingAnimation
import com.example.pokeapp.ui.language.LanguageManager
import com.example.pokeapp.ui.screens.auth.AuthViewModel
import com.example.pokeapp.ui.theme.UiConstants
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    onGoRegister: () -> Unit = {}
) {
    val vm: AuthViewModel = hiltViewModel()
    val state by vm.state.collectAsState()
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    var isEnglish by remember {
        mutableStateOf(LanguageManager.getSavedLanguage(context) == "en")
    }

    var isChangingLanguage by remember {
        mutableStateOf(false)
    }

    if (isChangingLanguage) {
        LoadingAnimation()
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(UiConstants.ScreenOuterPadding),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
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
                            scope.launch {
                                isChangingLanguage = true
                                delay(700)
                                LanguageManager.setLanguage(
                                    context = context,
                                    languageCode = if (checked) "en" else "tr"
                                )
                            }
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(UiConstants.LargeSpacing))

            Text(
                text = stringResource(R.string.welcome_back),
                style = MaterialTheme.typography.headlineSmall,
                color = Color(0xFF5F5F5F)
            )

            Text(
                text = stringResource(R.string.fill_your_details),
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(UiConstants.LargeSpacing * 2))

            Text(
                text = stringResource(R.string.email),
                style = MaterialTheme.typography.labelMedium,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(UiConstants.SmallSpacing))

            OutlinedTextField(
                value = state.email,
                onValueChange = vm::onEmailChange,
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text(
                        text = stringResource(R.string.email),
                        color = Color(0xFFA8A8A8)
                    )
                },
                singleLine = true,
                shape = RoundedCornerShape(UiConstants.SmallSpacing),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF2B2B2B),
                    unfocusedBorderColor = Color(0xFFBDBDBD),
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    cursorColor = Color.Black
                )
            )

            Spacer(modifier = Modifier.height(UiConstants.MediumSpacing))

            Text(
                text = stringResource(R.string.password),
                style = MaterialTheme.typography.labelMedium,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(UiConstants.SmallSpacing))

            OutlinedTextField(
                value = state.password,
                onValueChange = vm::onPasswordChange,
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text(
                        text = stringResource(R.string.password),
                        color = Color(0xFFA8A8A8)
                    )
                },
                singleLine = true,
                shape = RoundedCornerShape(UiConstants.SmallSpacing),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF2B2B2B),
                    unfocusedBorderColor = Color(0xFFBDBDBD),
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    cursorColor = Color.Black
                )
            )

            Spacer(modifier = Modifier.height(UiConstants.MediumSpacing))

            state.error?.let {
                Text(
                    text = stringResource(R.string.error_prefix, it),
                    color = MaterialTheme.colorScheme.error
                )
            }
        }

        Column {
            Button(
                onClick = { vm.login(onLoginSuccess) },
                enabled = !state.isLoading,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(UiConstants.ButtonHeight),
                shape = RoundedCornerShape(UiConstants.ButtonHeight / 2),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF2447C6),
                    contentColor = Color.White,
                    disabledContainerColor = Color(0xFFE2E2E2),
                    disabledContentColor = Color(0xFF9C9C9C)
                )
            ) {
                Text(
                    text = stringResource(R.string.login),
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(modifier = Modifier.height(UiConstants.SmallSpacing))

            OutlinedButton(
                onClick = onGoRegister,
                enabled = !state.isLoading,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(UiConstants.ButtonHeight),
                shape = RoundedCornerShape(UiConstants.ButtonHeight / 2)
            ) {
                Text(stringResource(R.string.create_account))
            }
        }
    }
}