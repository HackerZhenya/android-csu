package com.hackerzhenya.phones.utils.ui

import io.reactivex.functions.Consumer
import me.dmdev.rxpm.PresentationModel
import me.dmdev.rxpm.action
import me.dmdev.rxpm.command
import me.dmdev.rxpm.navigation.NavigationMessage
import me.dmdev.rxpm.navigation.NavigationalPm
import me.dmdev.rxpm.widget.dialogControl

abstract class ScreenPresentationModel : PresentationModel(), NavigationalPm {

    override val navigationMessages = command<NavigationMessage>()

    val errorDialog = dialogControl<String, Unit>()

    protected val errorConsumer = Consumer<Throwable?> {
        errorDialog.show(it?.message ?: "Unknown error")
    }

    open val backAction = action<Unit> {
        this.map { Back }
            .doOnNext(navigationMessages.consumer)
    }

    fun navigate(message: NavigationMessage) {
        navigationMessages.accept(message)
    }

    protected fun showError(errorMessage: String?) {
        errorDialog.show(errorMessage ?: "Unknown error")
    }
}