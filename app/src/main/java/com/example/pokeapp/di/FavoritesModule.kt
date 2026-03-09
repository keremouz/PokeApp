package com.example.pokeapp.di

import com.example.pokeapp.data.repository.FirestoreFavoritesRepository
import com.example.pokeapp.domain.repository.FavoritesRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FavoritesModule {

    @Provides
    @Singleton
    fun provideFavoritesRepository(
        auth: FirebaseAuth,
        db: FirebaseFirestore
    ): FavoritesRepository =  FirestoreFavoritesRepository(auth, db)
}
