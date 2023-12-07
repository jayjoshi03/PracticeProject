package com.example.localdatabase.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.localdatabase.Image
import com.example.localdatabase.databinding.ImageAdapterBinding

class ImageAdapter(var context: Context, private var imageList :MutableList<Image>):RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {
    lateinit var binding:ImageAdapterBinding
    class ImageViewHolder(var bind:ImageAdapterBinding) : RecyclerView.ViewHolder(bind.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        binding = ImageAdapterBinding.inflate(LayoutInflater.from(context),parent,false)
        return ImageViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        var image = imageList[position]
        holder.bind.tvImage.setImageResource(image.image)
    }
}