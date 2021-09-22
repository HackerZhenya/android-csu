package com.hackerzhenya.phones.ui.listing.phones

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hackerzhenya.phones.R
import com.hackerzhenya.phones.models.Phone
import com.hackerzhenya.phones.ui.AppNavigationMessage.PhoneDetails
import com.hackerzhenya.phones.ui.listing.ListScreen
import com.hackerzhenya.phones.utils.Predicate
import me.dmdev.rxpm.passTo

class PhonesScreen(private val source: PhonesSource) : ListScreen<Phone, PhonesPm>() {
    override val recyclerViewAdapter = PhoneRecyclerViewAdapter {
        presentationModel.navigate(PhoneDetails(it))
    }

    override val screenLayout = R.layout.fragment_list

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(toolbar) {
            title = when (source) {
                is PhonesSource.FromBrand -> source.brand.name
                is PhonesSource.FromLatest -> resources.getString(R.string.title_latest)
                is PhonesSource.FromSearch -> resources.getString(R.string.title_search)
                else -> resources.getString(R.string.title_phones)
            }
        }

        with(recyclerView) {
            layoutManager = GridLayoutManager(context, 3)
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)

                    if (!recyclerView.canScrollVertically(1) &&
                        presentationModel.isPaginationSupported &&
                        presentationModel.searchQuery.value.isBlank()
                    ) {
                        Unit passTo presentationModel.load
                    }
                }
            })
        }
    }

    override fun providePresentationModel() = PhonesPm(source)

    override fun updateItems(items: List<Phone>) = recyclerViewAdapter.update(items)

    override fun getItemsSearchPredicate(query: String): Predicate<Phone> = {
        it.name.lowercase().contains(query) || it.brand?.lowercase()?.contains(query) ?: false
    }
}