package com.example.rickmortyalbum.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class EpisodesPageData(
    val info: EpisodeInfo,
    @SerializedName("results")
    var episodes: List<EpisodeData>
)

data class EpisodeInfo(
    val count: Int,
    val next: String,
    val pages: Int,
    val prev: String
)
@Entity
data class EpisodeData(
    val air_date: String?,
    val characters: List<String>,
    val created: String,
    val episode: String?,
    @PrimaryKey val id: Int,
    val name: String?,
    val url: String
) : Serializable