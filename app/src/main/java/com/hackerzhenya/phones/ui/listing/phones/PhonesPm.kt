package com.hackerzhenya.phones.ui.listing.phones

import com.hackerzhenya.phones.datasource.Datasource
import com.hackerzhenya.phones.models.Phone
import com.hackerzhenya.phones.models.PhonePagination
import com.hackerzhenya.phones.ui.listing.ListPm
import com.hackerzhenya.phones.ui.listing.phones.PhonesSource.*
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import me.dmdev.rxpm.action
import me.dmdev.rxpm.bindProgress
import me.dmdev.rxpm.skipWhileInProgress
import org.koin.java.KoinJavaComponent.inject

class PhonesPm(private val source: PhonesSource) : ListPm<Phone>() {
    private val api: Datasource by inject(Datasource::class.java)

    private var page = 0
    private var maxPage = 1

    private var supportPagination = true
    val isPaginationSupported: Boolean
        get() = supportPagination

    override val isLocalSearch: Boolean
        get() = source !is FromSearch

    override val load = action<Unit> {
        skipWhileInProgress(loading)
            .filter {
                (searchQuery.value.isNotBlank() || source !is FromSearch) &&
                        (isPaginationSupported || source is FromSearch)
            }
            .switchMapSingle {
                loadPhonesFromSource()
                    .observeOn(AndroidSchedulers.mainThread())
                    .bindProgress(loading)
                    .doOnSuccess { response ->
                        page = response.currentPage ?: 0
                        maxPage = response.lastPage ?: 1
                        supportPagination = response.supportPagination && page < maxPage

                        if (response.supportPagination) {
                            items.accept(items.value + response.phones)
                        } else {
                            items.accept(response.phones)
                        }

                    }
                    .doOnError(errorConsumer)
            }
            .retry()
    }

    private fun loadPhonesFromSource(): Single<PhonePagination> = when (source) {
        is FromBrand -> api.listPhones(source.brand.slug, ++page)
        is FromLatest -> api.latest()
        is FromSearch -> api.search(searchQuery.value)
        else -> throw Exception("Unexpected phone source")
    }
}