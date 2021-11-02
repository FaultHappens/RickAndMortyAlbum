package com.example.rickmortyalbum.db

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.example.rickmortyalbum.data.CharacterData

class CharactersDBRepository(private val charactersDAO: CharactersDAO) {
    @WorkerThread
    suspend fun insert(user: CharacterData){
        charactersDAO.insert(user)
    }
    @WorkerThread
    suspend fun delete(user: CharacterData){
        charactersDAO.delete(user)
    }

    fun getAll(): List<CharacterData> = charactersDAO.getAll()

    fun getById(characterID: Int): CharacterData = charactersDAO.getByID(characterID)

}