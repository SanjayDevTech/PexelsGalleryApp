package com.sanjaydevtech.pexelsgallery.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sanjaydevtech.pexelsgallery.databinding.LayoutRetryBinding

class PhotoLoadStateAdapter(
    private val retry: () -> Unit
): LoadStateAdapter<PhotoLoadStateAdapter.PhotoLoadStateViewHolder>() {

    inner class PhotoLoadStateViewHolder(
        private val binding: LayoutRetryBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(loadState: LoadState) {
//            binding.root.isVisible = (loadState is LoadState.Error || loadState is
            binding.progressBar.isVisible = loadState is LoadState.Loading
            binding.button.isVisible = loadState !is LoadState.Loading
            binding.textView.isVisible = loadState !is LoadState.Loading
        }
    }

    override fun onBindViewHolder(holder: PhotoLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): PhotoLoadStateViewHolder {
        return PhotoLoadStateViewHolder(
            LayoutRetryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                .apply {
                    button.setOnClickListener {
                        retry()
                    }
                }
        )
    }
}