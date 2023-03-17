package com.example.prettydogs.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.prettydogs.databinding.ImageListItemBinding
import com.example.prettydogs.model.DogImage


class DogImageListAdapter :
    ListAdapter<DogImage, DogImageListAdapter.DogImageListViewHolder>(DOG_IMAGE_COMPARATOR) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): DogImageListAdapter.DogImageListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ImageListItemBinding.inflate(inflater, parent, false)
        return DogImageListViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: DogImageListAdapter.DogImageListViewHolder,
        position: Int,
    ) {
        val dogImageItem = getItem(position)
        dogImageItem.let { holder.bind(it) }
    }

    inner class DogImageListViewHolder(
        private val binding: ImageListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(dogImage: DogImage) {
            Glide.with(binding.dogImage.context)
                .load(dogImage.imageUrl)
                .into(binding.dogImage)
        }
    }

    companion object {
        private val DOG_IMAGE_COMPARATOR = object : DiffUtil.ItemCallback<DogImage>() {
            override fun areItemsTheSame(oldItem: DogImage, newItem: DogImage): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: DogImage, newItem: DogImage): Boolean =
                oldItem == newItem
        }
    }
}

