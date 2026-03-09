package com.example.pokeapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.pokeapp.ui.screens.auth.AuthViewModel

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
                title = { Text("Hesap Oluştur", fontWeight = FontWeight.SemiBold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Geri")
                    }
                }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(horizontal = 24.dp)
                .fillMaxSize()
        ) {
            Spacer(Modifier.height(24.dp))


            Text(
                text = "Hadi başlayalım!",
                style = MaterialTheme.typography.headlineSmall
            )
            Text(
                text = "E-posta adresin nedir?",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(16.dp))

            OutlinedTextField(
                value = state.email,
                onValueChange = vm::onEmailChange,
                label = { Text("E-posta") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(Modifier.height(8.dp))

            Text(
                text = "Geçerli bir e-posta adresi kullan.",
                style = MaterialTheme.typography.bodySmall
            )

            Spacer(Modifier.height(16.dp))

            var showPassword by remember { mutableStateOf(false) }

            OutlinedTextField(
                value = state.password,
                onValueChange = vm::onPasswordChange,
                label = { Text("Şifre") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    TextButton(onClick = { showPassword = !showPassword }) {
                        Text(if (showPassword) "Gizle" else "Göster")
                    }
                }
            )

            state.error?.let { err ->
                Spacer(Modifier.height(12.dp))
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
                    .height(56.dp),
                shape = MaterialTheme.shapes.extraLarge
            ) {
                if (state.isLoading) {
                    CircularProgressIndicator(
                        strokeWidth = 2.dp,
                        modifier = Modifier.size(20.dp)
                    )
                } else {
                    Text("Devam Et")
                }
            }

            Spacer(Modifier.height(16.dp))
        }
    }
}