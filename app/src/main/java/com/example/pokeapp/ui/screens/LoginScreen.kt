package com.example.pokeapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.pokeapp.data.auth.FirebaseAuthRepository
import com.example.pokeapp.ui.screens.auth.AuthViewModel
import com.google.firebase.auth.FirebaseAuth

@Composable
fun LoginScreen(onLoginSuccess: () -> Unit) {

    val vm = remember {
        AuthViewModel(
            repo = FirebaseAuthRepository(FirebaseAuth.getInstance())
        )
    }

    val state by vm.state.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text("Login / Register")

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = state.email,
            onValueChange = vm::onEmailChange,
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = state.password,
            onValueChange = vm::onPasswordChange,
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        if (state.error != null) {
            Text("Hata: ${state.error}")
            Spacer(modifier = Modifier.height(8.dp))
        }

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(
                onClick = { vm.login(onLoginSuccess) },
                enabled = !state.isLoading
            ) { Text("Login") }

            OutlinedButton(
                onClick = { vm.register(onLoginSuccess) },
                enabled = !state.isLoading
            ) { Text("Register") }
        }
    }
}