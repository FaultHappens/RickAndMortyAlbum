package com.example.rickmortyalbum.retriever

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.example.rickmortyalbum.api.API
import com.example.rickmortyalbum.data.*
import kotlinx.coroutines.flow.Flow
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DataRetriever {
    private val service: API
    val BASE_URL = "https://rickandmortyapi.com/api/"

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        service = retrofit.create(API::class.java)
    }

    fun getEpisodes(): Flow<PagingData<EpisodeData>>{
        return Pager(
            config = PagingConfig(
                pageSize = 30,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                EpisodesPagingSource(service = service)
            }
        ).flow
    }


    fun getCharacters(): Flow<PagingData<CharacterData>> {
        return Pager(
            config = PagingConfig(
                pageSize = 30,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                CharactersPagingSource(service = service)
            }
        ).flow
    }

    suspend fun getCharacterWithID(id: String): CharacterData =
        service.getCharacterAPI("character/$id")

    suspend fun getEpisodeWithId(id: String): EpisodeData =
        service.getEpisodeAPI("episode/$id")
}