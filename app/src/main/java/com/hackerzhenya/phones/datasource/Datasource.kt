package com.hackerzhenya.phones.datasource

import com.hackerzhenya.phones.models.Brand
import com.hackerzhenya.phones.models.DetailedPhone
import com.hackerzhenya.phones.models.PhonePagination
import io.reactivex.Single

interface Datasource {
    fun listBrands(): Single<List<Brand>>
    fun listPhones(brandSlug: String, page: Int = 1): Single<PhonePagination>
    fun getPhoneSpecs(phoneSlug: String): Single<DetailedPhone>
    fun search(query: String): Single<PhonePagination>
    fun latest(): Single<PhonePagination>
}