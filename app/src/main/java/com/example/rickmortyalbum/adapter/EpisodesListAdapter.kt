package com.example.rickmortyalbum.adapter

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.rickmortyalbum.R
import com.example.rickmortyalbum.data.EpisodeData
import com.example.rickmortyalbum.databinding.EpisodeCardViewBinding
import okhttp3.internal.toHexString

class EpisodesListAdapter(private val listener: (EpisodeData) -> Unit) :
    ListAdapter<EpisodeData, EpisodesListViewHolder>(TaskDiffCallBack()) {
    var isPosEven: Boolean = false
    private lateinit var binding: EpisodeCardViewBinding

    class TaskDiffCallBack : DiffUtil.ItemCallback<EpisodeData>() {
        override fun areItemsTheSame(oldItem: EpisodeData, newItem: EpisodeData): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: EpisodeData, newItem: EpisodeData): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): com.example.rickmortyalbum.adapter.EpisodesListViewHolder {
        binding = EpisodeCardViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EpisodesListViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: com.example.rickmortyalbum.adapter.EpisodesListViewHolder,
        position: Int
    ) {
        holder.itemView.setOnClickListener { listener(getItem(position)) }
        return holder.bind(getItem(position))
    }


}

class EpisodesListViewHolder(private val binding: EpisodeCardViewBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: EpisodeData) {
        if (item.id % 2 == 1) binding.cardView.setCardBackgroundColor(Color.parseColor("#FF9787"))

        binding.episodeAirDateTV.text = item.air_date ?: "No Info"
        binding.episodeNameTV.text = item.name ?: "No Info"
        binding.episodeCodeTV.text = item.episode ?: "No Info"
    }
}
