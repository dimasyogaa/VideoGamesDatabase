package com.yogadimas.videogamesdatabaseyogadimaspambudi.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.yogadimas.videogamesdatabaseyogadimaspambudi.R
import com.yogadimas.videogamesdatabaseyogadimaspambudi.data.model.ResultsItem
import com.yogadimas.videogamesdatabaseyogadimaspambudi.databinding.ItemGameBinding

class GamesListPagingAdapter(private val itemClickCallback: OnItemClickCallback<ResultsItem>) :
    PagingDataAdapter<ResultsItem, GamesListPagingAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemGameBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding, itemClickCallback)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            holder.bind(data)
        }
    }

    class MyViewHolder(
        private val binding: ItemGameBinding,
        private val itemClickCallback: OnItemClickCallback<ResultsItem>,
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ResultsItem) {
            binding.apply {
                ivGame.load(data.backgroundImage) {
                    crossfade(true)
                    placeholder(R.drawable.placholder_image)
                    error(R.drawable.error_image)
                }
                tvName.text = data.name
                tvDateRelease.text = data.released
                tvRate.text = data.rating.toString().toDouble().toString()
                itemView.setOnClickListener { itemClickCallback.onItemClicked(data) }
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ResultsItem>() {
            override fun areItemsTheSame(oldItem: ResultsItem, newItem: ResultsItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: ResultsItem, newItem: ResultsItem): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}