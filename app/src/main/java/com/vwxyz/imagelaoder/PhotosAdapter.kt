package com.vwxyz.imagelaoder

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.vwxyz.imgloaderlib.ImageLoader


class PhotosAdapter(private val ctx: Context, private val items: List<Photo>) :
    RecyclerView.Adapter<PhotosAdapter.ViewHolder>() {
    private var imageLoader : ImageLoader? = null
    init {
        imageLoader = ImageLoader(ctx)
    }


    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        var image: ImageView

        init {
            image = v.findViewById(R.id.image) as ImageView
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotosAdapter.ViewHolder {
        val vh: RecyclerView.ViewHolder
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.photo_item, parent, false)
        vh = ViewHolder(v)
        return vh
    }

    override fun onBindViewHolder(holder: PhotosAdapter.ViewHolder, position: Int) {
        val item = items[position]

        var imageLoader = ImageLoader(ctx)

        imageLoader?.showImage(item.thumbnailUrl, holder.image)

    }

    override fun getItemCount(): Int {
        return items.size
    }


}