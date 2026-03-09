package com.example.pokeapp.data.auth

import com.example.pokeapp.domain.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FirebaseAuthRepository(
    private val auth: FirebaseAuth,
    private val db: FirebaseFirestore
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

    override suspend fun saveUserProfile(email: String): Result<Unit> =
        runCatching {
            val uid = auth.currentUser?.uid ?: error("User not logged in")

            val userMap = mapOf(
                "uid" to uid,
                "email" to email,
                "createdAt" to System.currentTimeMillis()
            )

            db.collection("users")
                .document(uid)
                .set(userMap)
                .await()

            Unit
        }
}