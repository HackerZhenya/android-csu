package com.hackerzhenya.phones.ui.listing.brands

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.hackerzhenya.phones.R
import com.hackerzhenya.phones.models.Brand
import com.hackerzhenya.phones.ui.AppNavigationMessage.Phones
import com.hackerzhenya.phones.ui.listing.ListScreen
import com.hackerzhenya.phones.utils.Predicate

class BrandsScreen : ListScreen<Brand, BrandsPm>() {
    override val recyclerViewAdapter = BrandRecyclerViewAdapter {
        presentationModel.navigate(Phones(it))
    }

    override val screenLayout = R.layout.fragment_list

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(toolbar) {
            title = resources.getString(R.string.title_brands)
        }

        with(recyclerView) {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(
                DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
            )
        }
    }

    override fun providePresentationModel() = BrandsPm()

    override fun updateItems(items: List<Brand>) = recyclerViewAdapter.update(items)

    override fun getItemsSearchPredicate(query: String): Predicate<Brand> = {
        it.name.lowercase().contains(query)
    }
}