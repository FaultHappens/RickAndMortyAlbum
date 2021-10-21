package com.example.rickmortyalbum.viewmodel

import android.util.Log
import android.widget.ProgressBar
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.rickmortyalbum.data.CharacterData
import com.example.rickmortyalbum.data.CharactersPageData
import com.example.rickmortyalbum.retriever.DataRetriever
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CharactersViewModel : ViewModel() {
    val charactersData: MutableLiveData<MutableList<CharacterData>> by lazy {
        MutableLiveData<MutableList<CharacterData>>()
    }
    val charactersPageData: MutableLiveData<CharactersPageData> by lazy {
        MutableLiveData<CharactersPageData>()
    }

    private lateinit var resultList: CharactersPageData
    lateinit var progressBar: ProgressBar
    val characters = mutableListOf<CharacterData>()

    fun getCharacters(): Flow<PagingData<CharacterData>> {
        return DataRetriever().getCharacters().cachedIn(viewModelScope)
    }

    fun getCharactersWithID(characterUrls: List<String>) {

        viewModelScope.launch(Dispatchers.IO) {
            for (i in characterUrls) {
                characters.add(DataRetriever().getCharacterWithID(i.substring(41)))
                progressBar.progress += 100 / characterUrls.count()
            }
            withContext(Dispatchers.Main) {
                charactersData.value = characters as ArrayList<CharacterData>
            }
        }
    }

}