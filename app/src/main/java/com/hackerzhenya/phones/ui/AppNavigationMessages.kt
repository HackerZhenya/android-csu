package com.hackerzhenya.phones.ui

import com.hackerzhenya.phones.models.Brand
import com.hackerzhenya.phones.models.Phone
import me.dmdev.rxpm.navigation.NavigationMessage

sealed class AppNavigationMessage : NavigationMessage {
    object Brands : AppNavigationMessage()
    class Phones(val brands: Brand) : AppNavigationMessage()
    class PhoneDetails(val phone: Phone) : AppNavigationMessage()
    object Latest : AppNavigationMessage()
    object Search : AppNavigationMessage()
}