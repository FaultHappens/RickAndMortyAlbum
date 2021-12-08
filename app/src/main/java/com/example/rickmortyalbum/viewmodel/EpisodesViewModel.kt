package com.example.rickmortyalbum.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.rxjava2.flowable
import com.example.rickmortyalbum.data.EpisodeData
import com.example.rickmortyalbum.db.*
import com.example.rickmortyalbum.retriever.DataRetriever
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EpisodesViewModel(application: Application,
                        private val repository: EpisodesDBRepository,
                        private val dataRetriever: DataRetriever) : AndroidViewModel(application) {

    val episodesData: MutableLiveData<MutableList<EpisodeData>> by lazy {
        MutableLiveData<MutableList<EpisodeData>>()
    }

    val progressLiveData: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }


    fun getEpisodes(): Flowable<PagingData<EpisodeData>> {
        return dataRetriever.getEpisodes().flowable
    }


    fun getEpisodesDataWithID(id: String): Observable<EpisodeData> {
        return dataRetriever.getEpisodeWithID(id)
    }


}