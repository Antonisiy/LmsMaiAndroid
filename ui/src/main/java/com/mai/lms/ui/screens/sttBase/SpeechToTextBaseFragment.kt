package com.mai.lms.ui.screens.sttBase

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.mai.lms.ui.R
import com.mai.lms.ui.base.BaseFragment
import com.mai.lms.ui.exceptions.ExceptionInfo
import com.mai.lms.ui.extensions.hideKeyboard
import com.mai.lms.ui.extensions.showErrorAlertDialog
import com.mai.lms.ui.screens.home.HomePresenter
import com.mai.lms.ui.screens.home.HomeView
import kotlinx.android.synthetic.main.fragment_base_stt.*
import moxy.presenter.InjectPresenter


class SpeechToTextBaseFragment: BaseFragment(), SpeechToTextBaseView {

    @InjectPresenter
    internal lateinit var presenter: SpeechToTextBasePresenter

    override fun layoutId(): Int = R.layout.fragment_base_stt

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.hideKeyboard()
        init()
    }

    private fun init() {
        stt_button_with_progress.setTitle("Начать запись")
        stt_button_with_progress.setOnClickCallback {
                if (ContextCompat.checkSelfPermission(activity!!,
                        Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(activity!!,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    val permissions = arrayOf(android.Manifest.permission.RECORD_AUDIO, android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    ActivityCompat.requestPermissions(activity!!, permissions,0)
                } else {
                    startRecording()
                    stt_button_with_progress.toggleProgress(true)
                }
            }
        stt_button_with_progress.setOnClickProgressCallback{
                stopRecording()
                stt_button_with_progress.toggleProgress(false)
            }
    }

    private fun startRecording()
    {
        presenter.startRecording()
    }


    private fun stopRecording()
    {
        presenter.stopRecording()
    }

    override fun setRecognitionResult(result: String) {
        textview_sound_recorder_result.text = result;
    }


    override fun showErrorAlert(info: ExceptionInfo) {
        context?.showErrorAlertDialog(info)
    }

}