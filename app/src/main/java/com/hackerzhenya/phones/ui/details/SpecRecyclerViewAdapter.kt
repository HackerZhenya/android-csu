package com.hackerzhenya.phones.ui.details

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hackerzhenya.phones.R
import com.hackerzhenya.phones.models.Specification

class SpecRecyclerViewAdapter(private val dataSet: List<Specification>) :
    RecyclerView.Adapter<SpecRecyclerViewAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val primary: TextView = view.findViewById(R.id.item_text_primary)
        val secondary: TextView = view.findViewById(R.id.item_text_secondary)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_text, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val spec = dataSet[position]

        viewHolder.primary.text = spec.title
        viewHolder.secondary.text = spec.values
            .joinToString("\n\n")
            .trim()

        if (viewHolder.secondary.text.isBlank()) {
            viewHolder.secondary.text = viewHolder.secondary
                .resources
                .getString(R.string.no_data)
        }
    }

    override fun getItemCount() = dataSet.size
}