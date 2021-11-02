package com.example.rickmortyalbum.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.rickmortyalbum.R
import com.example.rickmortyalbum.data.EpisodeData
import com.example.rickmortyalbum.databinding.EpisodeCardViewBinding

class EpisodesPagingListAdapter(private val listener: (EpisodeData) -> Unit) :
    PagingDataAdapter<EpisodeData, EpisodesPagingListViewHolder>(TaskDiffCallBack()) {
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
    ): EpisodesPagingListViewHolder {
        binding = EpisodeCardViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return EpisodesPagingListViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: EpisodesPagingListViewHolder,
        position: Int
    ) {
        holder.itemView.setOnClickListener { getItem(position)?.let { it1 -> listener(it1) } }
        return holder.bind(getItem(position), listener)
    }


}

class EpisodesPagingListViewHolder(private val binding: EpisodeCardViewBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: EpisodeData?, listener: (EpisodeData) -> Unit) {
        item?.let {
            if (item.id % 2 == 1) binding.cardView.setCardBackgroundColor(ContextCompat.getColor(binding.root.context,
                R.color.card_background_color_1))
            else binding.cardView.setCardBackgroundColor(ContextCompat.getColor(binding.root.context,
                R.color.card_background_color_2))
        }
        binding.episodeAirDateTV.text = item?.air_date ?: "No Info"
        binding.episodeNameTV.text = item?.name ?: "No Info"
        binding.episodeCodeTV.text = item?.episode ?: "No Info"
        binding.cardView.setOnClickListener{
            if (item != null) {
                listener(item)
            }
        }

    }
}