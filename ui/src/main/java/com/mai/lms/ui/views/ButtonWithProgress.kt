package com.mai.lms.ui.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.mai.lms.ui.R
import kotlinx.android.synthetic.main.button_with_progress.view.*


class ButtonWithProgress(context: Context, attrs: AttributeSet) :
    BetterViewAnimator(context, attrs) {

    init {
        View.inflate(context, R.layout.button_with_progress, this)
    }

    fun setTitle(textRes: Int) {
        button.setText(textRes)
    }

    fun setTitle(text: String) {
        button.text = text
    }

    fun setOnClickCallback(callback: () -> Unit) {
        button.setOnClickListener { callback() }
    }

    fun setOnClickProgressCallback(callback: () -> Unit) {
        button_progress.setOnClickListener { callback() }
    }

    fun toggleProgress(isProgress: Boolean) {
        visibleChildId = if (isProgress) R.id.button_progress else R.id.button
    }

    fun isProgress() : Boolean{
        return visibleChildId == R.id.button_progress
    }

    fun toggleEnabled(isEnabled: Boolean) {
        button.isEnabled = isEnabled
    }

    override fun setEnabled(enabled: Boolean) {
        button.isEnabled = enabled
    }

    override fun isEnabled() = button.isEnabled
}