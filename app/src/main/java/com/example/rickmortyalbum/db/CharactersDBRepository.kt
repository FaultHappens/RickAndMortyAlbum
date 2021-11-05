package com.example.rickmortyalbum.db

import androidx.annotation.WorkerThread
import com.example.rickmortyalbum.data.CharacterData

class CharactersDBRepository(private val charactersDAO: CharactersDAO) {
    @WorkerThread
    fun insert(user: CharacterData){
        charactersDAO.insert(user)
    }
    @WorkerThread
    fun delete(user: CharacterData){
        charactersDAO.delete(user)
    }

    fun getAll(): List<CharacterData> = charactersDAO.getAll()

    fun getById(characterID: Int): CharacterData = charactersDAO.getByID(characterID)

}