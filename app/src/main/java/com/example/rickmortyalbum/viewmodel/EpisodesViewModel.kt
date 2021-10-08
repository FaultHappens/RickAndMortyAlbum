package com.example.rickmortyalbum.viewmodel

import android.widget.ProgressBar
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickmortyalbum.data.EpisodeData
import com.example.rickmortyalbum.data.EpisodesPageData
import com.example.rickmortyalbum.retriever.DataRetriever
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EpisodesViewModel : ViewModel() {
    private lateinit var resultList: EpisodesPageData
    lateinit var progressBar: ProgressBar
    val episodes = mutableListOf<EpisodeData>()

    private val episodesPageData: MutableLiveData<EpisodesPageData> by lazy {
        MutableLiveData<EpisodesPageData>()
    }

    init {
        viewModelScope.launch {
            retrieveEpisodes(1)
        }
    }

    val episodesData: MutableLiveData<MutableList<EpisodeData>> by lazy {
        MutableLiveData<MutableList<EpisodeData>>()
    }

    fun getEpisodes(): LiveData<EpisodesPageData> {
        return episodesPageData
    }

    fun retrieveEpisodes(page: Int) {
        viewModelScope.launch {
            resultList = DataRetriever().getEpisodes(page)
            episodes += resultList.episodes
            if (episodesPageData.value != null) {
                val tempData: EpisodesPageData? = episodesPageData.value
                tempData?.episodes = episodes
                episodesPageData.value = tempData
            } else {
                episodesPageData.value = resultList
            }
        }
    }

    fun getEpisodesDataWithID(characterUrls: List<String>) {

        viewModelScope.launch(Dispatchers.IO) {
            val episodes = mutableListOf<EpisodeData>()
            for (i in characterUrls) {

                episodes.add(DataRetriever().getEpisodeWithId(i.substring(40)))
                progressBar.progress += 100 / characterUrls.count()
            }
            withContext(Dispatchers.Main) {
                episodesData.value = episodes as ArrayList<EpisodeData>
            }
        }
    }
}