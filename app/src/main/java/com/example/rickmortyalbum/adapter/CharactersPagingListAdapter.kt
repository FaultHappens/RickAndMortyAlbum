package com.example.rickmortyalbum.adapter

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.rickmortyalbum.data.CharacterData
import com.example.rickmortyalbum.data.EpisodeData
import com.example.rickmortyalbum.databinding.CharacterCardViewBinding

class CharactersPagingListAdapter(private val listener: (CharacterData) -> Unit) :
    PagingDataAdapter<CharacterData, CharactersPagingListViewHolder>(MovieDiffCallBack()) {

    private lateinit var binding: CharacterCardViewBinding

    class MovieDiffCallBack : DiffUtil.ItemCallback<CharacterData>() {
        override fun areItemsTheSame(oldItem: CharacterData, newItem: CharacterData): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CharacterData, newItem: CharacterData): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharactersPagingListViewHolder {
        binding =
            CharacterCardViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CharactersPagingListViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: com.example.rickmortyalbum.adapter.CharactersPagingListViewHolder,
        position: Int
    ) {
        holder.bind(getItem(position), listener)
    }
}

class CharactersPagingListViewHolder(
    val binding: CharacterCardViewBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: CharacterData?, listener: (CharacterData) -> Unit) {
        Log.d("LOL", "binding + ${item.toString()}")
        item?.let {
            if (item.id % 2 == 1) binding.cardView.setCardBackgroundColor(Color.parseColor("#FF9787"))
            // Todo იგივე რაც EpisodesPagingListAdapter ში, კოდი მოსაწესრიგებელია

            Glide.with(binding.root)
                .load(item.image)
                .into(binding.characterImageIV)
            binding.characterNameTV.text = item.name
            binding.characterStatusTV.text = item.status
            binding.cardView.setOnClickListener { listener(item) }
        }
    }
}