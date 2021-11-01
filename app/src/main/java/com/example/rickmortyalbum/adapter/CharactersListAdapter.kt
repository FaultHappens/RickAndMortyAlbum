package com.example.rickmortyalbum.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.rickmortyalbum.data.CharacterData
import com.example.rickmortyalbum.databinding.CharacterCardViewBinding

class CharactersListAdapter(
    private val listener: (CharacterData) -> Unit
) :
    ListAdapter<CharacterData, CharactersListViewHolder>(CharactersListAdapter.TaskDiffCallBack()) {

    private lateinit var binding: CharacterCardViewBinding
    private lateinit var characters: ArrayList<CharacterData>


    class TaskDiffCallBack : DiffUtil.ItemCallback<CharacterData>() {
        override fun areItemsTheSame(oldItem: CharacterData, newItem: CharacterData): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CharacterData, newItem: CharacterData): Boolean {
            return oldItem == newItem
        }
    }

    override fun onBindViewHolder(holder: CharactersListViewHolder, position: Int) {
        holder.itemView.setOnClickListener { listener(getItem(position)) }
        return holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharactersListViewHolder {
        binding =
            CharacterCardViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CharactersListViewHolder(binding)
    }


}

class CharactersListViewHolder(private val binding: CharacterCardViewBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: CharacterData) {
        if (item.id % 2 == 1) binding.cardView.setCardBackgroundColor(Color.parseColor("#FF9787"))
        // Todo იგივე რაც EpisodesPagingListAdapter ში, კოდი მოსაწესრიგებელია

        Glide.with(binding.root)
            .load(item.image)
            .into(binding.characterImageIV)
        binding.characterNameTV.text = item.name
        binding.characterStatusTV.text = item.status

    }
}