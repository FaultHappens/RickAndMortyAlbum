package com.example.rickmortyalbum.viewmodel

import android.util.Log
import android.widget.ProgressBar
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickmortyalbum.data.CharacterData
import com.example.rickmortyalbum.data.CharactersPageData
import com.example.rickmortyalbum.retriever.DataRetriever
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CharactersViewModel : ViewModel() {
    val charactersData: MutableLiveData<MutableList<CharacterData>> by lazy {
        MutableLiveData<MutableList<CharacterData>>()
    }
    val charactersPageData: MutableLiveData<CharactersPageData> by lazy {
        MutableLiveData<CharactersPageData>()
    }

    init {
        viewModelScope.launch {
            retrieveCharacters(1)
        }
    }

    private lateinit var resultList: CharactersPageData
    lateinit var progressBar: ProgressBar
    val characters = mutableListOf<CharacterData>()

    fun getCharacters(): MutableLiveData<CharactersPageData> {
        return charactersPageData
    }

    fun retrieveCharacters(page: Int) {
        viewModelScope.launch {
            resultList = DataRetriever().getCharacters(page)
            characters += resultList.characters
            Log.d("TEST", "changing liveData")
            if (charactersPageData.value != null) {
                val tempData: CharactersPageData? = charactersPageData.value
                tempData?.characters = characters
                charactersPageData.value = tempData
            } else {
                charactersPageData.value = resultList
            }
        }
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