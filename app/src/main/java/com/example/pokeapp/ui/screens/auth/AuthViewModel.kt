package com.example.pokeapp.ui.screens.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokeapp.domain.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repo: AuthRepository
) : ViewModel() {

    private val _state = MutableStateFlow(AuthUiState())
    val state: StateFlow<AuthUiState> = _state

    fun onEmailChange(v: String) = _state.update { it.copy(email = v) }
    fun onPasswordChange(v: String) = _state.update { it.copy(password = v) }

    fun login(onSuccess: () -> Unit) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            val s = _state.value
            repo.login(s.email.trim(), s.password)
                .onSuccess { onSuccess() }
                .onFailure { e -> _state.update { it.copy(error = e.message) } }
            _state.update { it.copy(isLoading = false) }
        }
    }

    fun register(onSuccess: () -> Unit) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            val s = _state.value

            repo.register(s.email.trim(), s.password)
                .onSuccess {
                    repo.saveUserProfile(s.email.trim())
                        .onSuccess { onSuccess() }
                        .onFailure { e ->
                            _state.update { it.copy(error = e.message) }
                        }
                }
                .onFailure { e ->
                    _state.update { it.copy(error = e.message) }
                }

            _state.update { it.copy(isLoading = false) }
        }
    }
    fun logout() {
        repo.logout()
    }
}