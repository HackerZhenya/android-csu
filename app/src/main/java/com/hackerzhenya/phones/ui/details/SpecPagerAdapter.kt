package com.hackerzhenya.phones.ui.details

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hackerzhenya.phones.R
import com.hackerzhenya.phones.models.SpecificationCategory
import com.hackerzhenya.phones.utils.DiffCallback

class SpecPagerAdapter(initialData: List<SpecificationCategory>) :
    RecyclerView.Adapter<SpecPagerAdapter.ViewHolder>() {
    constructor() : this(emptyList())

    private val dataSet = initialData.toMutableList()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val recyclerView: RecyclerView = view.findViewById(R.id.item_list)
    }

    fun update(data: List<SpecificationCategory>) {
        val diff = DiffCallback(dataSet, data)
        val result = DiffUtil.calculateDiff(diff)

        dataSet.clear()
        dataSet.addAll(data)
        result.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_list, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        with(viewHolder.recyclerView) {
            adapter = SpecRecyclerViewAdapter(dataSet[position].specs)
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(
                DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
            )
        }
    }

    override fun getItemCount() = dataSet.size
}