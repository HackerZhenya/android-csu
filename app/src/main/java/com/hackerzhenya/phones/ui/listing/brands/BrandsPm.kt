package com.hackerzhenya.phones.ui.listing.brands

import com.hackerzhenya.phones.datasource.Datasource
import com.hackerzhenya.phones.models.Brand
import com.hackerzhenya.phones.ui.listing.ListPm
import io.reactivex.android.schedulers.AndroidSchedulers
import me.dmdev.rxpm.action
import me.dmdev.rxpm.bindProgress
import me.dmdev.rxpm.skipWhileInProgress
import org.koin.java.KoinJavaComponent.inject

class BrandsPm : ListPm<Brand>() {
    private val api: Datasource by inject(Datasource::class.java)

    override val load = action<Unit> {
        skipWhileInProgress(loading)
            .skipWhile {
                searchQuery.value.isNotBlank()
            }
            .switchMapSingle {
                api.listBrands()
                    .observeOn(AndroidSchedulers.mainThread())
                    .bindProgress(loading)
                    .doOnSuccess(items.consumer)
                    .doOnError(errorConsumer)
            }
            .retry()
    }
}