package com.example.rickmortyalbum.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.rickmortyalbum.data.CharacterData
import com.example.rickmortyalbum.data.EpisodeData

@Dao
interface EpisodesDAO {
    @Query("SELECT * FROM EpisodeData")
    fun getAll(): List<EpisodeData>

    @Query("SELECT * FROM EpisodeData WHERE id = (:episodeID)")
    fun getByID(episodeID: Int): EpisodeData

    @Insert
    fun insert(vararg user: EpisodeData)

    @Delete
    fun delete(user: EpisodeData)
}
