package com.hackerzhenya.phones.api

import com.hackerzhenya.phones.models.Brand
import com.hackerzhenya.phones.models.DetailedPhone
import com.hackerzhenya.phones.models.PhonePagination
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

sealed interface MobileSpecsApi {
    @GET("brands")
    fun listBrands(): Single<ResponseWrapper<List<Brand>>>

    @GET("brands/{slug}")
    fun listPhones(
        @Path("slug") brandSlug: String,
        @Query("page") page: Int = 1
    ): Single<ResponseWrapper<PhonePagination>>

    @GET("{slug}")
    fun getPhoneSpecs(@Path("slug") phoneSlug: String): Single<ResponseWrapper<DetailedPhone>>

    @GET("search")
    fun search(@Query("query") query: String): Single<ResponseWrapper<PhonePagination>>

    @GET("latest")
    fun latest(): Single<ResponseWrapper<PhonePagination>>
}