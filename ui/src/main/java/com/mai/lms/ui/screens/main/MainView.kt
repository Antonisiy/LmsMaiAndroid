package com.mai.lms.ui.screens.main

import com.mai.lms.ui.base.BaseView
import com.mai.lms.ui.base.ChangeViewStateStrategy
import com.mai.lms.ui.exceptions.ExceptionInfo
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

interface MainView : BaseView {

    @StateStrategyType(ChangeViewStateStrategy::class, tag = "content")
    fun showLoading()

    @StateStrategyType(ChangeViewStateStrategy::class, tag = "content")
    fun showContent()

    @StateStrategyType(ChangeViewStateStrategy::class, tag = "content")
    fun showError(info: ExceptionInfo, callback: () -> Unit)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showConfirmationDialogExitApp()

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showConfirmationDialogExitAccount()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun requestExternalStoragePermission()
}