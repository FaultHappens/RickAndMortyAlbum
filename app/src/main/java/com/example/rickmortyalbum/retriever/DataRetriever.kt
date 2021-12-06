package com.example.rickmortyalbum.retriever

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava2.flowable
import com.example.rickmortyalbum.api.API
import com.example.rickmortyalbum.data.CharacterData
import com.example.rickmortyalbum.data.CharactersPagingSource
import com.example.rickmortyalbum.data.EpisodeData
import com.example.rickmortyalbum.data.EpisodesPagingSource
import kotlinx.coroutines.flow.Flow

class DataRetriever(private val service: API) {
    fun getEpisodes(): Pager<Int, EpisodeData>{
        return Pager(
            config = PagingConfig(
                pageSize = 30,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                EpisodesPagingSource(service = service)
            }
        )
    }



    fun getCharacters(): Pager<Int, CharacterData>{
        return Pager(
            config = PagingConfig(
                pageSize = 30,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                CharactersPagingSource(service = service)
            }
        )
    }

    suspend fun getCharacterWithID(id: String): CharacterData =
        service.getCharacterAPI("character/$id")

    suspend fun getEpisodeWithId(id: String): EpisodeData =
        service.getEpisodeAPI("episode/$id")
}