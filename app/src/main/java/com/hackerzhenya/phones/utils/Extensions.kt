package com.hackerzhenya.phones.utils

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.hackerzhenya.phones.extensions.openScreen

typealias Predicate<T> = (T) -> Boolean
typealias Consumer<T> = (T) -> Unit

inline fun <reified T> FragmentManager.findScreen(
    tag: String = T::class.java.name
): T? =
    findFragmentByTag(tag) as? T

inline fun <reified T : Fragment> FragmentManager.findOrOpenScreen(
    tag: String = T::class.java.name,
    addToBackStack: Boolean = true,
    createScreen: () -> T
) {
    val screen = findScreen<T>(tag) as Fragment?
    openScreen(screen ?: createScreen(), tag, addToBackStack)
}
