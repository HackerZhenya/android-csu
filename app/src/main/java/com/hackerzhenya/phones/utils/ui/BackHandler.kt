package com.hackerzhenya.phones.utils.ui

import me.dmdev.rxpm.navigation.NavigationMessage

interface BackHandler {
    fun handleBack(): Boolean
}

object Back : NavigationMessage