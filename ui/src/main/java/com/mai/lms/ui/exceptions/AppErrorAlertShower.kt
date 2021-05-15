package com.mai.lms.ui.exceptions

import android.content.Context
import com.mai.lms.ui.extensions.showErrorAlertDialog
import java.lang.ref.WeakReference

class AppErrorAlertShower : ErrorAlertShower {

    private var contextRef: WeakReference<Context>? = null

    override fun attach(context: Context) {
        contextRef = WeakReference(context)
    }

    override fun detach() {
        contextRef?.clear()
        contextRef = null
    }

    override fun show(info: ExceptionInfo) {
        contextRef?.get()?.showErrorAlertDialog(info)
    }

    override fun show(titleRes: Int, msgRes: Int) {
        contextRef?.get()?.showErrorAlertDialog(titleRes, msgRes)
    }
}
