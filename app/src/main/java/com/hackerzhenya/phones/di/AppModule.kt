package com.hackerzhenya.phones.di

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.hackerzhenya.phones.api.MobileSpecsApi
import com.hackerzhenya.phones.datasource.ApiDatasource
import com.hackerzhenya.phones.datasource.Datasource
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.jackson.JacksonConverterFactory

val apiModule = module {
    single {
        ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .registerKotlinModule()
    }

    single {
        OkHttpClient.Builder()
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl(
                getProperty("api.host"))
            .client(
                get(OkHttpClient::class))
            .addConverterFactory(
                JacksonConverterFactory.create(
                    get(ObjectMapper::class)))
            .addCallAdapterFactory(
                RxJava2CallAdapterFactory.createWithScheduler(
                    Schedulers.io()))
            .build()
            .create(MobileSpecsApi::class.java)
    }
}

val datasourceModule = module {
    single { ApiDatasource() } bind Datasource::class
}

val appModules = apiModule + datasourceModule