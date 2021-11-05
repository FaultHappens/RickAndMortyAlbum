package com.example.rickmortyalbum.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.rickmortyalbum.R
import com.example.rickmortyalbum.data.CharacterData
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

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CharactersPagingListViewHolder {
        binding = CharacterCardViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CharactersPagingListViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: CharactersPagingListViewHolder,
        position: Int
    ) {
        holder.bind(getItem(position), listener)
    }
}

class CharactersPagingListViewHolder(private val binding: CharacterCardViewBinding)
    : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: CharacterData?, listener: (CharacterData) -> Unit) {
        Log.d("LOL", "binding + ${item.toString()}")
        item?.let {
            if (item.id % 2 == 1) binding.cardView.setCardBackgroundColor(
                ContextCompat.getColor(
                    binding.root.context,
                    R.color.card_background_color_1
                )
            )
            else binding.cardView.setCardBackgroundColor(
                ContextCompat.getColor(
                    binding.root.context,
                    R.color.card_background_color_2
                )
            )

            Glide.with(binding.root)
                .load(item.image)
                .into(binding.characterImageIV)
            binding.characterNameTV.text = item.name
            binding.characterStatusTV.text = item.status
            binding.cardView.setOnClickListener { listener(item) }
        }
    }
}