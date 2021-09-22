package com.hackerzhenya.phones.ui.details


import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.widget.Toolbar
import androidx.core.content.res.ResourcesCompat
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.hackerzhenya.phones.R
import com.hackerzhenya.phones.models.DetailedPhone
import com.hackerzhenya.phones.models.Phone
import com.hackerzhenya.phones.utils.ui.Screen
import me.dmdev.rxpm.bindTo
import me.dmdev.rxpm.passTo


class PhoneDetailsScreen(private val phoneSlug: String) : Screen<PhoneDetailsPm>() {
    constructor(phone: Phone) : this(phone.slug)

    private lateinit var toolbar: Toolbar
    private lateinit var progressBar: ProgressBar
    private lateinit var imagePager: ViewPager2
    private lateinit var tabs: TabLayout
    private lateinit var specPager: ViewPager2
    private lateinit var fab: FloatingActionButton
    private val imagePagerAdapter = ImagePagerAdapter()
    private val specPagerAdapter = SpecPagerAdapter()

    override val screenLayout = R.layout.fragment_phone_details

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbar = view.findViewById(R.id.toolbar)
        progressBar = view.findViewById(R.id.progress)
        imagePager = view.findViewById(R.id.details_images_pager)
        tabs = view.findViewById(R.id.details_tabs)
        fab = view.findViewById(R.id.details_fab)
        specPager = view.findViewById(R.id.details_spec_pager)

        with(toolbar) {
            if (parentFragmentManager.backStackEntryCount > 0) {
                navigationIcon = ResourcesCompat
                    .getDrawable(resources, R.drawable.ic_action_back, null)
                setNavigationOnClickListener {
                    handleBack()
                }
            }
        }

        with(imagePager) {
            adapter = imagePagerAdapter
        }

        with(specPager) {
            adapter = specPagerAdapter
        }
    }

    override fun providePresentationModel() = PhoneDetailsPm(phoneSlug)

    override fun onBindPresentationModel(pm: PhoneDetailsPm) {
        super.onBindPresentationModel(pm)

        pm.loading bindTo {
            progressBar.visibility = if (it) {
                View.VISIBLE
            } else {
                View.INVISIBLE
            }
        }

        pm.phone bindTo { phone ->
            toolbar.title = phone.name
            toolbar.subtitle = phone.brand

            val spec = phone.fullSpecifications
            imagePagerAdapter.update(phone.images)
            specPagerAdapter.update(spec)

            TabLayoutMediator(tabs, specPager) { tab, position ->
                tab.text = spec[position].title
            }.attach()

            fab.setOnClickListener {
                sharePhone(phone)
            }
        }

        Unit passTo pm.load
    }

    private fun sharePhone(phone: DetailedPhone) {
        val intent = Intent().apply {
            action = Intent.ACTION_SEND
            type = "text/plain"
            putExtra(
                Intent.EXTRA_TEXT, requireContext()
                    .resources
                    .getString(R.string.share_message, "${phone.brand} ${phone.name}")
            )
        }

        startActivity(
            Intent.createChooser(intent, "Share phone", null)
        )
    }
}



