package com.example.pokeapp.data.repository

import com.example.pokeapp.domain.model.PokemonListItem
import com.example.pokeapp.domain.repository.FavoritesRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import kotlin.coroutines.cancellation.CancellationException

class FirestoreFavoritesRepository (
    private val auth: FirebaseAuth,
    private val db: FirebaseFirestore
): FavoritesRepository{
    private fun favCollection()=
        db.collection("users")
            .document(auth.currentUser?.uid ?: error("User not logged in"))
            .collection("favorites")

    override fun observeFavorites(): Flow<List<PokemonListItem>> = callbackFlow {
        val col = favCollection()
        val listener = col.addSnapshotListener{ snap, err ->
            if (err != null){
                close(err)
                return@addSnapshotListener
            }
            val list = snap?.documents?.mapNotNull { it.toObject<PokemonListItem>() } ?: emptyList()
            trySend(list)
        }
        awaitClose{ listener.remove()}
    }

    override suspend fun isFavorite(pokemonId: Int): Boolean {
        val doc = favCollection().document(pokemonId.toString()).get().await()
        return doc.exists()
    }

    override suspend fun toggleFavorite(pokemon: PokemonListItem) {
        try {
            val docRef = favCollection().document(pokemon.id.toString())
            val existing = docRef.get().await()
            if (existing.exists()){
                docRef.delete().await()
            }else{
                docRef.set(pokemon).await()
            }
        }catch (e: Exception){
            if ( e is CancellationException) throw e
            throw e
        }
    }
}