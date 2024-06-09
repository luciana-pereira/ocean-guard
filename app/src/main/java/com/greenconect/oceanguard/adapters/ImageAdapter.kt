package com.greenconect.oceanguard.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.greenconect.oceanguard.R
import com.greenconect.oceanguard.utils.ImageUtils

class ImageAdapter(private val images: IntArray) : RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {

    class ImageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.imageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.image_card, parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val context = holder.itemView.context
        val resources = context.resources
        val bitmap = ImageUtils.decodeSampledBitmapFromResource(resources, images[position], 100, 100)
        holder.imageView.setImageBitmap(bitmap)
    }

    override fun getItemCount(): Int {
        return images.size
    }
}
