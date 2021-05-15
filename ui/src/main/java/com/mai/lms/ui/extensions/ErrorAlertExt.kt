package com.mai.lms.ui.extensions

import android.content.Context
import android.content.DialogInterface
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.mai.lms.ui.R
import com.mai.lms.ui.exceptions.ExceptionInfo

fun Context.showErrorAlertDialog(exceptionInfo: ExceptionInfo) {
    showErrorAlertDialog(exceptionInfo.msgResId, exceptionInfo.titleResId)
}

fun Context.showErrorAlertDialog(msgRes: Int, titleRes: Int) {
    val msg = getString(msgRes)
    val title = getString(titleRes)
    showErrorAlertDialog(msg, title)
}

fun Context.showErrorAlertDialog(msg: String, title: String) {
    val show = MaterialAlertDialogBuilder(this, R.style.WhiteDialogStyle)
        .setTitle(title)
        .setMessage(msg)
        .setPositiveButton(android.R.string.ok) { dialog, _ -> dialog.dismiss() }
        .setOnKeyListener { dialog, keyCode, _ -> onKeyPressed(dialog, keyCode) }
        .show()
}

private fun onKeyPressed(dialog: DialogInterface, keyCode: Int): Boolean {
    //todo
    if (keyCode == 1) dialog.dismiss()
    return true
}
