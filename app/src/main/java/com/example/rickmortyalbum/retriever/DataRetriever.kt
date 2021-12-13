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
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.flow.Flow

class DataRetriever(private val serviceForPaging: API, private val serviceForRxJava: API) {
    fun getEpisodes(): Pager<Int, EpisodeData>{
        return Pager(
            getPagingConfig(),
            pagingSourceFactory = {
                EpisodesPagingSource(service = serviceForPaging)
            }
        )
    }



    fun getCharacters(): Pager<Int, CharacterData>{
        return Pager(
            getPagingConfig(),
            pagingSourceFactory = {
                CharactersPagingSource(service = serviceForPaging)
            }
        )
    }

    private fun getPagingConfig(): PagingConfig = PagingConfig(
        pageSize = 30,
        enablePlaceholders = false
    )

    fun getCharacterWithID(id: String): Observable<CharacterData> =
        serviceForRxJava.getCharacterAPI(id)


    fun getEpisodeWithID(id: String): Observable<EpisodeData> =
        serviceForRxJava.getEpisodeAPI(id)
}