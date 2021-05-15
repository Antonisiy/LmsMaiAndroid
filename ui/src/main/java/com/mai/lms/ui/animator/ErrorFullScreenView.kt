package com.mai.lms.ui.animator

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.mai.lms.ui.R
import com.mai.lms.ui.exceptions.ExceptionInfo
import com.mai.lms.ui.extensions.setOnClickCallback
import kotlinx.android.synthetic.main.error_full_screen_view_layout.view.*

class ErrorFullScreenView(context: Context, attrs: AttributeSet?) :
    ConstraintLayout(context, attrs) {

    init {
        View.inflate(context, R.layout.error_full_screen_view_layout, this)
    }

    fun showError(info: ExceptionInfo, retryCallback: () -> Unit) {
        error_title_text_view.setText(info.titleResId)
        error_description_text_view.setText(info.msgResId)
        error_retry_button.setOnClickCallback(retryCallback)
    }
}
