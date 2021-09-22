package com.hackerzhenya.phones.ui.listing.brands

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.hackerzhenya.phones.R
import com.hackerzhenya.phones.models.Brand
import com.hackerzhenya.phones.utils.Consumer
import com.hackerzhenya.phones.utils.DiffCallback

class BrandRecyclerViewAdapter(
    initialData: List<Brand>,
    private val clickListener: Consumer<Brand>
) : RecyclerView.Adapter<BrandRecyclerViewAdapter.ViewHolder>() {
    private val dataSet: MutableList<Brand> = initialData.toMutableList()
    constructor(clickListener: Consumer<Brand>) : this(emptyList<Brand>(), clickListener)

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val primary: TextView = view.findViewById(R.id.item_text_primary)
        val secondary: TextView = view.findViewById(R.id.item_text_secondary)
    }

    fun update(data: List<Brand>) {
        val diff = DiffCallback(dataSet, data)
        val result = DiffUtil.calculateDiff(diff)

        dataSet.clear()
        dataSet.addAll(data)
        result.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_text, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val brand = dataSet[position]

        viewHolder.primary.text = brand.name
        viewHolder.secondary.text = viewHolder.view
            .resources
            .getString(R.string.n_devices)
            .format(brand.count)

        viewHolder.view.setOnClickListener {
            clickListener(brand)
        }
    }

    override fun getItemCount() = dataSet.size
}