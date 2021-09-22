package com.hackerzhenya.phones.utils.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import com.hackerzhenya.phones.R
import com.hackerzhenya.phones.extensions.findScreen
import io.reactivex.functions.Consumer
import me.dmdev.rxpm.base.PmFragment
import me.dmdev.rxpm.passTo
import me.dmdev.rxpm.widget.bindTo

abstract class Screen<PM : ScreenPresentationModel> : PmFragment<PM>(), BackHandler {
    abstract val screenLayout: Int

    final override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(screenLayout, container, false)
    }

    override fun onBindPresentationModel(pm: PM) {
        pm.errorDialog bindTo { message, _ ->
            AlertDialog.Builder(requireContext())
                .setMessage(message)
                .setPositiveButton(R.string.ok, null)
                .create()
        }
    }

    override fun handleBack(): Boolean {
        Unit passTo presentationModel.backAction
        return true
    }

    val progressConsumer = Consumer<Boolean> {
        if (it) {
            ProgressDialog().show(childFragmentManager, null)
        } else {
            childFragmentManager
                .findScreen<ProgressDialog>()
                ?.dismiss()
        }
    }
}