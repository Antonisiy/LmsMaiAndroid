package com.mai.lms.ui.exceptions

import android.content.Context

interface ErrorAlertShower {

    fun attach(context: Context)

    fun detach()

    fun show(info: ExceptionInfo)

    fun show(titleRes: Int, msgRes: Int)
}
