package com.example.rickmortyalbum.viewmodel

import android.widget.ProgressBar
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.rickmortyalbum.data.CharacterData
import com.example.rickmortyalbum.data.EpisodeData
import com.example.rickmortyalbum.db.CharactersDB
import com.example.rickmortyalbum.retriever.DataRetriever
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EpisodesViewModel : ViewModel() {

    val episodesData: MutableLiveData<MutableList<EpisodeData>> by lazy {
        MutableLiveData<MutableList<EpisodeData>>()
    }
    lateinit var progressBar: ProgressBar
    fun getEpisodes(): Flow<PagingData<EpisodeData>> {
        return DataRetriever().getEpisodes().cachedIn(viewModelScope)
    }


    fun getEpisodesDataWithID(episodeUrls: List<String>) {

        viewModelScope.launch(Dispatchers.IO) {
            val episodes = mutableListOf<EpisodeData>()
            for (i in episodeUrls) {
                progressBar.progress += 100 / episodeUrls.count()
                episodes.add(DataRetriever().getEpisodeWithId(i.substring(40)))
            }
            withContext(Dispatchers.Main) {
                episodesData.value = episodes as ArrayList<EpisodeData>
            }
        }
    }
}