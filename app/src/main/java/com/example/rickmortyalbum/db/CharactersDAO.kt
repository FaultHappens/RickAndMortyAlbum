package com.example.rickmortyalbum.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.rickmortyalbum.data.CharacterData

@Dao
interface CharactersDAO {
    @Query("SELECT * FROM CharacterData")
    fun getAll(): List<CharacterData>

    @Query("SELECT * FROM CharacterData WHERE id = (:characterID)")
    fun getByID(characterID: Int): CharacterData

    @Insert
    fun insert(vararg users: CharacterData)

    @Delete
    fun delete(user: CharacterData)
}
