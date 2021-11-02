package com.example.rickmortyalbum.db

import androidx.annotation.WorkerThread
import com.example.rickmortyalbum.data.EpisodeData

class EpisodesDBRepository(private val episodesDAO: EpisodesDAO) {
    @WorkerThread
    suspend fun insert(episode: EpisodeData){
        episodesDAO.insert(episode)
    }
    @WorkerThread
    suspend fun delete(episode: EpisodeData){
        episodesDAO.delete(episode)
    }

    fun getAll(): List<EpisodeData> = episodesDAO.getAll()

    fun getById(episodeID: Int): EpisodeData = episodesDAO.getByID(episodeID)

}