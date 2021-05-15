package com.mai.lms.ui.animator

import android.animation.LayoutTransition
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import com.mai.lms.ui.R
import com.mai.lms.ui.exceptions.ExceptionInfo
import kotlinx.android.synthetic.main.progress_with_error.view.*

class CommonViewAnimator(context: Context, attrs: AttributeSet) : BetterViewAnimator(context, attrs) {

    init {
        LayoutInflater.from(getContext()).inflate(R.layout.progress_with_error, this)
        enableOnVisibilityChangesTransitionAnimation()
    }

    fun showProgress() {
        visibleChildId = R.id.progress_bar
    }

    private fun enableOnVisibilityChangesTransitionAnimation() {
        val layoutTransition = LayoutTransition()
        layoutTransition.enableOnlyVisibilityChangesType()
        setLayoutTransition(layoutTransition)
    }

    private fun LayoutTransition.enableOnlyVisibilityChangesType() {
        disableTransitionType(LayoutTransition.CHANGE_APPEARING)
        disableTransitionType(LayoutTransition.CHANGE_DISAPPEARING)
    }

    fun showErrorMessage(
            exceptionInfo: ExceptionInfo,
            callback: (() -> Unit)
    ) {
        full_screen_error.showError(exceptionInfo, callback)
        visibleChildId = R.id.full_screen_error
    }
}
