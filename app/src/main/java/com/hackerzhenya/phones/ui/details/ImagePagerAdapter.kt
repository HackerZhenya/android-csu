package com.hackerzhenya.phones.ui.details

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.hackerzhenya.phones.R
import com.hackerzhenya.phones.utils.DiffCallback

class ImagePagerAdapter(initialData: List<String>) :
    RecyclerView.Adapter<ImagePagerAdapter.ViewHolder>() {
    constructor() : this(emptyList())

    private val dataSet = initialData.toMutableList()

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView = view.findViewById(R.id.item_image)
    }

    fun update(data: List<String>) {
        val diff = DiffCallback(dataSet, data)
        val result = DiffUtil.calculateDiff(diff)

        dataSet.clear()
        dataSet.addAll(data)
        result.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_image, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        Glide.with(viewHolder.view.context)
            .applyDefaultRequestOptions(
                RequestOptions()
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            )
            .load(dataSet[position])
            .into(viewHolder.image)
    }

    override fun getItemCount() = dataSet.size
}