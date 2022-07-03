package com.brainjug.kotlinmvvmretrofitstateflowcoroutines.util

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.brainjug.kotlinmvvmretrofitstateflowcoroutines.R
import com.brainjug.kotlinmvvmretrofitstateflowcoroutines.databinding.RowLoadStateBinding

class LoadStateViewHolder(
    private val binding: RowLoadStateBinding,
    retry: () -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.retryButton.setOnClickListener { retry.invoke() }
    }

    fun bind(loadState: LoadState) {
        if (loadState is LoadState.Error) {
            binding.errorMsg.text = loadState.error.localizedMessage
        }
        binding.progressBar.isVisible = loadState is LoadState.Loading
        binding.retryButton.isVisible = loadState !is LoadState.Loading
    }

    companion object {
        fun create(parent: ViewGroup, retry: () -> Unit): LoadStateViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.row_load_state, parent, false)
            val binding = RowLoadStateBinding.bind(view)
            return LoadStateViewHolder(binding, retry)
        }
    }
}