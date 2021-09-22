package com.hackerzhenya.phones.ui.listing

import com.hackerzhenya.phones.utils.ui.ScreenPresentationModel
import me.dmdev.rxpm.Action
import me.dmdev.rxpm.action
import me.dmdev.rxpm.state
import java.util.concurrent.TimeUnit

abstract class ListPm<T> : ScreenPresentationModel() {
    open val isLocalSearch: Boolean
        get() = true

    val loading = state(false)
    val items = state(emptyList<T>())
    val searchQuery = state("")

    abstract val load: Action<Unit>

    val onSearch = action<String> {
        debounce(300, TimeUnit.MILLISECONDS)
            .map { it.trim() }
            .doOnNext(searchQuery.consumer)
    }

}