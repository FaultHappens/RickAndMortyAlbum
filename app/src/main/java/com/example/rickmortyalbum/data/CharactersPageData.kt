package com.example.rickmortyalbum.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class CharactersPageData(
    val info: Info,
    @SerializedName("results")
    var characters: List<CharacterData>
)

data class Info(
    val count: Int,
    val next: String,
    val pages: Int,
    val prev: Any
)

data class CharacterData(
    val created: String,
    val episode: List<String>,
    val gender: String,
    val id: Int,
    val image: String,
    val location: Location,
    val name: String,
    val origin: Origin,
    val species: String,
    val status: String,
    val type: String,
    val url: String
): Serializable

data class Location(
    val name: String,
    val url: String
)

data class Origin(
    val name: String,
    val url: String
)