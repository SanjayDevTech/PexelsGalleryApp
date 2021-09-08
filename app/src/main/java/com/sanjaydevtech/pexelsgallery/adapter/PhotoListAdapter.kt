package com.sanjaydevtech.pexelsgallery.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.sanjaydevtech.pexelsgallery.R
import com.sanjaydevtech.pexelsgallery.databinding.LayoutPhotoBinding
import com.sanjaydevtech.pexelsgallery.model.Photo

class PhotoListAdapter(
    diffCallback: DiffUtil.ItemCallback<Photo>
): PagingDataAdapter<Photo, PhotoListAdapter.PhotoViewHolder>(diffCallback) {

    inner class PhotoViewHolder(
        private val binding: LayoutPhotoBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(photo: Photo?) {
            if (photo == null) {
                binding.imageView.load(R.drawable.placeholder)
                binding.imageView.contentDescription = null
                binding.cardView.setOnClickListener {  }
            } else {
                binding.imageView.load(photo.src.medium) {
                    crossfade(true)
                }
                binding.imageView.contentDescription = photo.photographer
                binding.cardView.setOnClickListener {
                    println(photo.photographer)
                }
            }
        }
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        return PhotoViewHolder(
            LayoutPhotoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            ),
        )
    }
}