package com.example.rickmortyalbum.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.rickmortyalbum.R
import com.example.rickmortyalbum.data.CharacterData
import com.example.rickmortyalbum.databinding.CharacterCardViewBinding

class CharactersListAdapter(
    private val listener: (CharacterData) -> Unit
) :
    RecyclerView.Adapter<CharactersListViewHolder>() {

    private lateinit var binding: CharacterCardViewBinding
    private var list: MutableList<CharacterData> = mutableListOf()

    fun updateList(character: CharacterData){
        list.add(character)
        notifyItemInserted(list.count()-1)
    }

    override fun onBindViewHolder(holder: CharactersListViewHolder, position: Int) {
        holder.itemView.setOnClickListener { listener(list[position]) }
        holder.bind(list[position])
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharactersListViewHolder {
        binding = CharacterCardViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CharactersListViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.count()
    }


}

class CharactersListViewHolder(private val binding: CharacterCardViewBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: CharacterData) {
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

    }
}