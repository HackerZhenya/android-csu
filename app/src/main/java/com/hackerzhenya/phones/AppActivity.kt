package com.hackerzhenya.phones

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.hackerzhenya.phones.extensions.currentScreen
import com.hackerzhenya.phones.extensions.openScreen
import com.hackerzhenya.phones.ui.AppNavigationMessage.*
import com.hackerzhenya.phones.ui.details.PhoneDetailsScreen
import com.hackerzhenya.phones.ui.listing.brands.BrandsScreen
import com.hackerzhenya.phones.ui.listing.phones.PhonesScreen
import com.hackerzhenya.phones.ui.listing.phones.PhonesSource.*
import com.hackerzhenya.phones.utils.findOrOpenScreen
import com.hackerzhenya.phones.utils.ui.Back
import com.hackerzhenya.phones.utils.ui.BackHandler
import me.dmdev.rxpm.navigation.NavigationMessage
import me.dmdev.rxpm.navigation.NavigationMessageHandler

class AppActivity : AppCompatActivity(), NavigationMessageHandler {
    private lateinit var bottomNav: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottomNav = findViewById(R.id.bottom_nav)
        bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_brands -> handleNavigationMessage(Brands)
                R.id.navigation_latest -> handleNavigationMessage(Latest)
                R.id.navigation_search -> handleNavigationMessage(Search)
            }

            true
        }

        if (savedInstanceState == null) {
            supportFragmentManager.openScreen(BrandsScreen(), addToBackStack = false)
        }
    }

    override fun onBackPressed() {
        supportFragmentManager.currentScreen?.let {
            if (it is BackHandler && it.handleBack()) {
                return
            }
        }

        if (supportFragmentManager.backStackEntryCount == 0) {
            super.onBackPressed()
        }
    }

    override fun handleNavigationMessage(message: NavigationMessage): Boolean {
        val sfm = supportFragmentManager

        when (message) {
            is Back -> super.onBackPressed()

            is Brands -> {
                sfm.findOrOpenScreen { BrandsScreen() }
            }

            is Phones -> {
                sfm.findOrOpenScreen(
                    tag = "${PhonesScreen::class.java.name}$${FromBrand::class.java.name}$${message.brands.slug}"
                ) { PhonesScreen(FromBrand(message.brands)) }
            }

            is Latest -> {
                sfm.findOrOpenScreen(
                    tag = "${PhonesScreen::class.java.name}$${FromLatest::class.java.name}"
                ) { PhonesScreen(FromLatest) }
            }

            is Search -> {
                sfm.findOrOpenScreen(
                    tag = "${PhonesScreen::class.java.name}$${FromSearch::class.java.name}"
                ) { PhonesScreen(FromSearch) }
            }

            is PhoneDetails -> {
                sfm.findOrOpenScreen(
                    tag = "${PhoneDetailsScreen::class.java.name}$${FromBrand::class.java.name}$${message.phone.slug}"
                ) { PhoneDetailsScreen(message.phone) }
            }
        }

        return true
    }
}