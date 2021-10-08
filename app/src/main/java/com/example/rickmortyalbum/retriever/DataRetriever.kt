package com.example.rickmortyalbum.retriever

import com.example.rickmortyalbum.api.API
import com.example.rickmortyalbum.data.CharacterData
import com.example.rickmortyalbum.data.CharactersPageData
import com.example.rickmortyalbum.data.EpisodeData
import com.example.rickmortyalbum.data.EpisodesPageData
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DataRetriever {
    private val service: API
    companion object {
        const val BASE_URL = "https://rickandmortyapi.com/api/"
    }

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        service = retrofit.create(API::class.java)
    }

    suspend fun getEpisodes(page: Int): EpisodesPageData =
        service.getEpisodesListAPI(page)

    suspend fun getCharacters(page: Int): CharactersPageData =
        service.getCharactersAPI(page)

    suspend fun getCharacterWithID(id: String): CharacterData =
        service.getCharacterAPI("character/$id")

    suspend fun getEpisodeWithId(id: String): EpisodeData =
        service.getEpisodeAPI("episode/$id")
}