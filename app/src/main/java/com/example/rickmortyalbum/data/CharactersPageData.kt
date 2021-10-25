package com.example.rickmortyalbum.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
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
@Entity
data class CharacterData(
    val created: String,
    val episode: List<String>,
    val gender: String,
    @PrimaryKey val id: Int,
    val image: String,
    val name: String,
    val species: String,
    val status: String,
    val type: String,
    val url: String
): Serializable