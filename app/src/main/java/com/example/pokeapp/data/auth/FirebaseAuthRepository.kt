package com.example.pokeapp.data.auth

import com.example.pokeapp.domain.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

class FirebaseAuthRepository(
    private val auth: FirebaseAuth
) : AuthRepository {

    override suspend fun login(email: String, password: String): Result<Unit> =
        runCatching {
            auth.signInWithEmailAndPassword(email, password).await()
            Unit
        }

    override suspend fun register(email: String, password: String): Result<Unit> =
        runCatching {
            auth.createUserWithEmailAndPassword(email, password).await()
            Unit
        }

    override fun isLoggedIn(): Boolean = auth.currentUser != null

    override fun logout() {
        auth.signOut()
    }
}