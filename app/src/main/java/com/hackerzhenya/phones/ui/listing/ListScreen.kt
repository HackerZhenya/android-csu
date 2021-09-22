package com.hackerzhenya.phones.ui.listing

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.hackerzhenya.phones.R
import com.hackerzhenya.phones.utils.Predicate
import com.hackerzhenya.phones.utils.ui.Screen
import me.dmdev.rxpm.bindTo
import me.dmdev.rxpm.passTo

abstract class ListScreen<T, PM : ListPm<T>> : Screen<PM>() {
    protected lateinit var toolbar: Toolbar
    protected lateinit var searchView: SearchView
    protected lateinit var recyclerView: RecyclerView
    protected lateinit var progressBar: ProgressBar
    protected abstract val recyclerViewAdapter: RecyclerView.Adapter<out RecyclerView.ViewHolder>

    override val screenLayout = R.layout.fragment_list

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbar = view.findViewById(R.id.toolbar)
        searchView = view.findViewById(R.id.search_view)
        recyclerView = view.findViewById(R.id.main_list)
        progressBar = view.findViewById(R.id.progress_bar)

        with(toolbar) {
            if (parentFragmentManager.backStackEntryCount > 0) {
                navigationIcon = ResourcesCompat
                    .getDrawable(resources, R.drawable.ic_action_back, null)
                setNavigationOnClickListener {
                    handleBack()
                }
            }
        }

        with(recyclerView) {
            adapter = recyclerViewAdapter
        }
    }

    override fun onBindPresentationModel(pm: PM) {
        super.onBindPresentationModel(pm)

        pm.items bindTo {
            updateItems(it)
        }

        pm.searchQuery bindTo {
            if (!pm.isLocalSearch) {
                Unit passTo pm.load
                return@bindTo
            }

            if (it.isNotBlank()) {
                updateItems(
                    pm.items.value.filter(
                        getItemsSearchPredicate(it)))
            } else if (recyclerViewAdapter.itemCount != pm.items.value.size) {
                updateItems(pm.items.value)
            }
        }

        pm.loading bindTo {
            progressBar.visibility = if (it) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                (query ?: "") passTo pm.onSearch
                return false
            }

            override fun onQueryTextChange(newText: String?) =
                onQueryTextSubmit(newText)
        })

        searchView.setOnCloseListener {
            "" passTo pm.onSearch
            false
        }

        if (pm.items.value.isEmpty()) {
            Unit passTo pm.load
        }
    }

    protected abstract fun updateItems(items: List<T>)
    protected abstract fun getItemsSearchPredicate(query: String): Predicate<T>
}