package com.mai.lms.ui.screens.home


import com.mai.lms.domain.ext.launchUI
import com.mai.lms.ui.base.BasePresenter
import com.mai.lms.ui.exceptions.ExceptionInfo
import com.mai.lms.ui.exceptions.createErrorHandler
import com.mai.lms.ui.navigator.NavigationListener
import com.mai.lms.ui.navigator.events.STTestEvent
import moxy.InjectViewState
import org.koin.core.inject

@InjectViewState
internal class HomePresenter : BasePresenter<HomeView>() {

    private val navigationListener: NavigationListener by inject()

    fun onNextClick() {
        nextScreen()
    }

    private fun nextScreen() {
        val handler = createErrorHandler(::showError)
        launchUI(handler) {
           navigationListener(STTestEvent)
        }
    }

    private fun showError(info: ExceptionInfo) {
        viewState.showErrorAlert(info)
        //viewState.showContent()
    }
}
