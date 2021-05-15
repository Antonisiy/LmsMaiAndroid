package com.mai.lms.ui.exceptions

import com.mai.lms.ui.R


data class ExceptionInfo(
    val throwable: Throwable?,
    val msgResId: Int,
    val titleResId: Int = R.string.error_alert_default_title
)

