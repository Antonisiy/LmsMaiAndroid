package com.mai.lms.ui.screens.main

import android.Manifest
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.mai.lms.domain.ext.NotificationUtil
import com.mai.lms.ui.R
import com.mai.lms.ui.base.BaseActivity
import com.mai.lms.ui.callbacks.OnBackPressed
import com.mai.lms.ui.callbacks.OnRequestPermissionCallback
import com.mai.lms.ui.callbacks.OnTriggerDown
import com.mai.lms.ui.exceptions.ErrorAlertShower
import com.mai.lms.ui.exceptions.ExceptionInfo
import com.mai.lms.ui.extensions.showErrorAlertDialog
import kotlinx.android.synthetic.main.activity_main.*
import moxy.presenter.InjectPresenter
import org.koin.core.KoinComponent
import org.koin.core.inject

class MainActivity : BaseActivity(), MainView, KoinComponent {

    private val notificationUtil: NotificationUtil by inject()

    companion object {
        const val FIRE_BUTTON_KEY_CODE = 293
        const val INTERVAL_BETWEEN_KEY_PRESS_IN_MILLIS = 1000
        const val KEY_PRESS_MAX_REPEAT_COUNT = 0
        const val REQUEST_EXTERNAL_STORAGE_PERMISSION = 201
    }

    @InjectPresenter
    lateinit var presenter: MainPresenter

    private val errorAlertShower: ErrorAlertShower by inject()

    private var time = System.currentTimeMillis()

    override fun fragmentContainerId() = R.id.main_fragment_container

    override fun layoutId() = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        main_title_text.setText(R.string.title)
    }

    override fun showLoading() {
        main_content_animator.showProgress()
    }

    override fun showContent() {
        main_content_animator.visibleChildId = R.id.main_fragment_container
    }

    override fun showError(info: ExceptionInfo, callback: () -> Unit) {
        main_content_animator.showErrorMessage(info, callback)
    }

    override fun showConfirmationDialogExitAccount() {
        /*showAlertDialog(
            titleRes = R.string.dialog_title_exit_account,
            msgRes = R.string.dialog_message_exit_account,
            okCallback = presenter::onConfirmedExitAccount
        )*/
    }


    fun onRestartWithSameAccount() {
        presenter.onRestartWithSameAccount()
    }

    override fun onBackPressed() {
        val isNotHandled = forwardEventToFragments().not()
        if (isNotHandled) {
            val isEndOfBackStack = supportFragmentManager.backStackEntryCount == 0
            if (isEndOfBackStack) {
                presenter.onClickExitApp()
            } else {
                super.onBackPressed()
            }
        }
    }

    private fun forwardEventToFragments(): Boolean {
        val currentFragment =
            supportFragmentManager.findFragmentById(R.id.main_fragment_container) ?: return false
        return if (currentFragment is OnBackPressed) {
            currentFragment.onBackPressed()
        } else {
            false
        }
    }

    override fun showConfirmationDialogExitApp() {
       /* showAlertDialog(
            titleRes = R.string.dialog_title_exit_app,
            msgRes = R.string.dialog_message_exit_app,
            okCallback = presenter::onConfirmedExitApp
        )*/
    }


    override fun requestExternalStoragePermission() = requestPermissions(
        arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        ),
        REQUEST_EXTERNAL_STORAGE_PERMISSION
    )

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_EXTERNAL_STORAGE_PERMISSION) {
            presenter.externalStoragePermissionRequestCallback(grantResults)
        } else {
            val fragment = supportFragmentManager.findFragmentById(R.id.main_fragment_container)
            if (fragment is OnRequestPermissionCallback) {
                fragment.onRequestPermissionCallback(requestCode, grantResults)
            }
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        val interval = System.currentTimeMillis() - time
        time = System.currentTimeMillis()
        Log.println(
            Log.DEBUG,
            "MULTIPLE",
            event?.repeatCount.toString() + " $interval "
        )
        val fragment = supportFragmentManager.findFragmentById(R.id.main_fragment_container)
        val isSinglePressed =
            event?.repeatCount == KEY_PRESS_MAX_REPEAT_COUNT && interval > INTERVAL_BETWEEN_KEY_PRESS_IN_MILLIS
        return if (keyCode == FIRE_BUTTON_KEY_CODE && fragment is OnTriggerDown && isSinglePressed) {
            fragment.onTriggerDown()
            true
        } else {
            super.onKeyDown(keyCode, event)
        }
    }

    override fun showErrorAlert(info: ExceptionInfo) {
        showErrorAlertDialog(info)
    }

    override fun showErrorAlert(titleRes: Int, msgRes: Int) {
        showErrorAlertDialog(titleRes, msgRes)
    }

    override fun onStart() {
        super.onStart()
        errorAlertShower.attach(this)
    }

    override fun onStop() {
        errorAlertShower.detach()
        super.onStop()
    }
}


fun Fragment.changeTitle(@StringRes res: Int) {
    getMainActivity().main_title_text.setText(res)
}

fun Fragment.changeTitle(text: String) {
    getMainActivity().main_title_text.text = text
}

fun Fragment.getMainActivity() = activity as MainActivity


