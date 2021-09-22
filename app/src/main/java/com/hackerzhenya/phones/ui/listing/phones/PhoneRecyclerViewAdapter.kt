package com.hackerzhenya.phones.ui.listing.phones

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.hackerzhenya.phones.R
import com.hackerzhenya.phones.models.Phone
import com.hackerzhenya.phones.utils.Consumer
import com.hackerzhenya.phones.utils.DiffCallback

class PhoneRecyclerViewAdapter(
    initialData: List<Phone>,
    private val clickListener: Consumer<Phone>
) : RecyclerView.Adapter<PhoneRecyclerViewAdapter.ViewHolder>() {

    private val dataSet: MutableList<Phone> = initialData.toMutableList()

    constructor(clickListener: Consumer<Phone>) : this(emptyList<Phone>(), clickListener)

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView = view.findViewById(R.id.item_entity_image)
        val primary: TextView = view.findViewById(R.id.item_entity_primary)
        val secondary: TextView = view.findViewById(R.id.item_entity_secondary)
    }

    fun update(data: List<Phone>) {
        val diff = DiffCallback(dataSet, data)
        val result = DiffUtil.calculateDiff(diff)

        dataSet.clear()
        dataSet.addAll(data)
        result.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_phone, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val phone = dataSet[position]

        viewHolder.primary.text = phone.name
        viewHolder.secondary.text = phone.brand

        if (phone.brand == null) {
            viewHolder.secondary.visibility = View.GONE
        }

        Glide.with(viewHolder.image.context)
            .applyDefaultRequestOptions(
                RequestOptions()
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .priority(Priority.HIGH)
            )
            .load(phone.image)
            .into(viewHolder.image)

        viewHolder.view.setOnClickListener {
            clickListener(phone)
        }
    }

    override fun getItemCount() = dataSet.size
}