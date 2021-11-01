package com.example.rickmortyalbum.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
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
    ): com.example.rickmortyalbum.adapter.EpisodesPagingListViewHolder {
        binding = EpisodeCardViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return EpisodesPagingListViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: com.example.rickmortyalbum.adapter.EpisodesPagingListViewHolder,
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
            if (item.id % 2 == 1) binding.cardView.setCardBackgroundColor(Color.parseColor("#FF9787"))
                //Todo თუ არ შესრულდა პირობა რა ფერი უნდა ჰქონდეს გაუწერე, ამ ითემის რესაიკლი თუ მოხდა შეიძლება ისეთზეც ეს ფერი დახატოს სადაც არ უნდა ყოფილიყო
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