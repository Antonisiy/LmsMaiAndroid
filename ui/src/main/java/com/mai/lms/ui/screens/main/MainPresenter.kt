package com.mai.lms.ui.screens.main

import android.content.pm.PackageManager
import com.mai.lms.domain.ext.launchUI
import com.mai.lms.ui.base.BasePresenter
import com.mai.lms.ui.exceptions.ExceptionInfo
import com.mai.lms.ui.exceptions.createErrorHandler
import com.mai.lms.ui.navigator.NavigationListener
import com.mai.lms.ui.navigator.events.OutsideNavigationEvent
import com.mai.lms.ui.navigator.events.PreparationNavigationEvent
import moxy.InjectViewState
import org.koin.core.inject

@InjectViewState
class MainPresenter : BasePresenter<MainView>() {

    private val navigationListener: NavigationListener by inject()
    //private val clearCurrentUserShiftUseCase: ClearCurrentUserShiftUseCase by inject()
    //private val initAppUseCase: InitAppUseCase by inject()

    override fun onFirstViewAttach() {
        viewState.requestExternalStoragePermission()
    }

    fun externalStoragePermissionRequestCallback(grantResults: IntArray) {
        val arePermissionsGranted =
            grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED
                && grantResults[1] == PackageManager.PERMISSION_GRANTED
        if (arePermissionsGranted) initApp()
    }

    private fun initApp() {
        val handler = createErrorHandler(this::onInitFailed)
        launchUI(handler) {
            viewState.showLoading()
            //withIO { initAppUseCase(this) }
            viewState.showContent()
            navigationListener(PreparationNavigationEvent.AppInitializedEvent)
        }
    }

    private fun onInitFailed(info: ExceptionInfo) {
        viewState.showError(info, this::initApp)
    }

    fun onClickExitAccount() {
        viewState.showConfirmationDialogExitAccount()
    }

    fun onConfirmedExitAccount() {
        launchUI {
            //ithIO { clearCurrentUserShiftUseCase(isSaveToken = false) }
            navigationListener.invoke(OutsideNavigationEvent.ExitAccountEvent)
        }
    }

    fun onClickExitApp() {
        viewState.showConfirmationDialogExitApp()
    }

    fun onConfirmedExitApp() {
        navigationListener(OutsideNavigationEvent.CloseCurrentScreenEvent)
    }

    fun onRestartWithSameAccount() {
        launchUI {
            //clearCurrentUserShiftUseCase(isSaveToken = true)
            navigationListener(OutsideNavigationEvent.RestartWithSameAccountEvent)
        }
    }
}
