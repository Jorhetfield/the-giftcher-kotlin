package com.example.thegiftcherk.features.ui.carousel

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.thegiftcherk.R
import com.example.thegiftcherk.features.ui.search.models.Item
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.tutorial_item_row.view.*


class ImagesAdapter : ListAdapter<Pair<Drawable, String>, ImagesAdapter.ImageViewHolder>(Diff()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val viewHolder = inflater.inflate(R.layout.tutorial_item_row, parent, false)
        return ImageViewHolder(viewHolder)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    inner class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(image: Pair<Drawable, String>) = with(itemView) {

            itemImage?.setImageDrawable(image.first)
            message?.text = image.second


        }
    }

    class Diff : DiffUtil.ItemCallback<Pair<Drawable, String>>() {
        override fun areItemsTheSame(old: Pair<Drawable, String>, new: Pair<Drawable, String>): Boolean =
            old == new

        override fun areContentsTheSame(old: Pair<Drawable, String>, new: Pair<Drawable, String>): Boolean =
            old == new
    }
}
