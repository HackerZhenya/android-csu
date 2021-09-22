package com.hackerzhenya.phones.datasource

import android.annotation.SuppressLint
import com.hackerzhenya.phones.api.MobileSpecsApi
import com.hackerzhenya.phones.api.ResponseWrapper
import com.hackerzhenya.phones.models.Brand
import io.reactivex.Single
import org.koin.java.KoinJavaComponent.inject

class ApiDatasource : Datasource {
    private val api: MobileSpecsApi by inject(MobileSpecsApi::class.java)

    @SuppressLint("CheckResult")
    private fun <T> proxy(source: Single<ResponseWrapper<T>>): Single<T> =
        Single.create { single ->
            source.subscribe({
                if (it.status) {
                    single.onSuccess(it.data!!)
                } else {
                    single.onError(Exception(it.error!!))
                }
            }, {
                single.onError(it)
            })
        }

    override fun listBrands(): Single<List<Brand>> =
        proxy(api.listBrands())

    override fun listPhones(brandSlug: String, page: Int) =
        proxy(api.listPhones(brandSlug, page))

    override fun getPhoneSpecs(phoneSlug: String) =
        proxy(api.getPhoneSpecs(phoneSlug))

    override fun search(query: String) =
        proxy(api.search(query))

    override fun latest() =
        proxy(api.latest())
}