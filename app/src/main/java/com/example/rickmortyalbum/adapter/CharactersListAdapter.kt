package com.example.rickmortyalbum.adapter

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.rickmortyalbum.R
import com.example.rickmortyalbum.data.CharacterData
import com.example.rickmortyalbum.databinding.CharacterCardViewBinding
import com.example.rickmortyalbum.databinding.LoadMoreCardViewBinding

class CharactersListAdapter(
    private val listener: (CharacterData) -> Unit
) :
    ListAdapter<CharacterData, CharactersListViewHolder>(CharactersListAdapter.TaskDiffCallBack()) {

    private lateinit var binding: CharacterCardViewBinding
    private lateinit var loadMoreCardViewBinding: LoadMoreCardViewBinding

    class TaskDiffCallBack : DiffUtil.ItemCallback<CharacterData>() {
        override fun areItemsTheSame(oldItem: CharacterData, newItem: CharacterData): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CharacterData, newItem: CharacterData): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharactersListViewHolder {

        binding = CharacterCardViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CharactersListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CharactersListViewHolder, position: Int) {
        val item = getItem(position)
        holder.itemView.setOnClickListener { listener(item) }

        return holder.bind(getItem(position), position)
    }
}

class CharactersListViewHolder(private val binding: CharacterCardViewBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(character: CharacterData, position: Int) {
        if (position % 2 == 1) binding.cardView.setCardBackgroundColor(Color.parseColor("#FF9787"))
        Glide.with(itemView.context)
            .load(character.image)
            .into(binding.characterImageIV)
        binding.characterNameTV.text = character.name
        binding.characterStatusTV.text = character.status

    }
}