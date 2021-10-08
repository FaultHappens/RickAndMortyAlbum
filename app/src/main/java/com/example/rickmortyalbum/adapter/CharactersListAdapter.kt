package com.example.rickmortyalbum.adapter

import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Build
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.rickmortyalbum.R
import com.example.rickmortyalbum.constant.Constant
import com.example.rickmortyalbum.data.CharacterData
import com.example.rickmortyalbum.databinding.CharacterCardViewBinding

class CharactersListAdapter(
    private val listener: (CharacterData) -> Unit
) :
    ListAdapter<CharacterData, RecyclerView.ViewHolder>(CharactersListAdapter.TaskDiffCallBack()) {

    private lateinit var binding: CharacterCardViewBinding
    private lateinit var characters: ArrayList<CharacterData>

    class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    class CharactersListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)


    class TaskDiffCallBack : DiffUtil.ItemCallback<CharacterData>() {
        override fun areItemsTheSame(oldItem: CharacterData, newItem: CharacterData): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CharacterData, newItem: CharacterData): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        Log.d("TEST", "Creating ViewHolder")

        binding = CharacterCardViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//        return if (viewType == Constant.VIEW_TYPE_ITEM) {
//            CharactersListViewHolder()
//        }else {
//            val view = LayoutInflater.from(parent.context).inflate(R.layout.progress_loading, parent, false)
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//                view.findViewById<ProgressBar>(R.id.progressbar).indeterminateDrawable.colorFilter = BlendModeColorFilter(Color.WHITE, BlendMode.SRC_ATOP)
//            } else {
//                view.findViewById<ProgressBar>(R.id.progressbar).indeterminateDrawable.setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY)
//            }
//            LoadingViewHolder(view)
//        }
    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder.itemViewType == Constant.VIEW_TYPE_ITEM) {
            if (position % 2 == 1) binding.cardView.setCardBackgroundColor(Color.parseColor("#FF9787"))
            Glide.with(holder.itemView.context)
                .load(getItem(position).image)
                .into(binding.characterImageIV)
            holder.itemView.findViewById<TextView>(R.id.characterNameTV).text = getItem(position).name
            holder.itemView.findViewById<TextView>(R.id.characterStatusTV).text = getItem(position).status
            holder.itemView.setOnClickListener { listener(getItem(position)) }
        }
    }
}
