 package com.brainjug.kotlinmvvmretrofitstateflowcoroutines.feature.user.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.brainjug.kotlinmvvmretrofitstateflowcoroutines.databinding.RowUserItemBinding
import com.brainjug.kotlinmvvmretrofitstateflowcoroutines.feature.user.model.Characters
import com.bumptech.glide.Glide

class CharactersAdapter() :
    PagingDataAdapter<Characters, CharactersAdapter.MyViewHolder>(DiffUtils) {

    class MyViewHolder(private val binding: RowUserItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            character: Characters
        ) {
            binding.character = character
            binding.imageUrl = character.image
            binding.executePendingBindings()
        }
    }

    object DiffUtils : DiffUtil.ItemCallback<Characters>() {
        override fun areItemsTheSame(
            oldItem: Characters,
            newItem: Characters
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: Characters,
            newItem: Characters
        ): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val binding = RowUserItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: MyViewHolder,
        position: Int
    ) {
        val item = getItem(position)
        if (item != null) {
            holder.bind(item)
        }
    }
}
