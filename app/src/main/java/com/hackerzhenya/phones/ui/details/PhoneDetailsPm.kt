package com.hackerzhenya.phones.ui.details

import com.hackerzhenya.phones.datasource.Datasource
import com.hackerzhenya.phones.models.DetailedPhone
import com.hackerzhenya.phones.utils.ui.ScreenPresentationModel
import me.dmdev.rxpm.action
import me.dmdev.rxpm.bindProgress
import me.dmdev.rxpm.skipWhileInProgress
import me.dmdev.rxpm.state
import org.koin.java.KoinJavaComponent.inject

class PhoneDetailsPm(private val phoneSlug: String) : ScreenPresentationModel() {
    private val api: Datasource by inject(Datasource::class.java)

    val loading = state(false)
    val phone = state<DetailedPhone>()

    val load = action<Unit> {
        skipWhileInProgress(loading)
            .switchMapSingle {
                api.getPhoneSpecs(phoneSlug)
                    .bindProgress(loading)
                    .doOnSuccess(phone.consumer)
                    .doOnError(errorConsumer)
            }
            .retry()
    }
}