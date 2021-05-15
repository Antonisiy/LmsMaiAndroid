package com.mai.lms.ui.screens.sttBase

import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import android.os.Build
import android.os.Environment
import android.util.Log
import androidx.annotation.RequiresApi
import com.mai.lms.domain.ext.asyncIO
import com.mai.lms.domain.ext.launchUI
import com.mai.lms.domain.ext.withIO
import com.mai.lms.domain.usecase.stt.VoiceRecognitionUseCase
import com.mai.lms.ui.base.BasePresenter
import com.mai.lms.ui.exceptions.ExceptionInfo
import com.mai.lms.ui.exceptions.createErrorHandler
import com.mai.lms.ui.navigator.NavigationListener
import moxy.InjectViewState
import org.koin.core.inject
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.nio.ByteBuffer

@InjectViewState
class SpeechToTextBasePresenter : BasePresenter<SpeechToTextBaseView>() {

    private val navigationListener: NavigationListener by inject()
    private val voiceRecognitionUseCase: VoiceRecognitionUseCase by inject()
    private var bufferSize: Int = 0
    private lateinit var audioRecorder: AudioRecord

    fun onNextClick() {
        nextScreen()
    }

    fun startRecording()
    {
        bufferSize = AudioRecord.getMinBufferSize(
            48000,
            AudioFormat.CHANNEL_IN_MONO,
            AudioFormat.ENCODING_PCM_16BIT
        ) * 2

        audioRecorder = AudioRecord(
            MediaRecorder.AudioSource.MIC,
            48000,
            AudioFormat.CHANNEL_IN_MONO,
            AudioFormat.ENCODING_PCM_16BIT,
            bufferSize
        )
        Log.d("AudioRecorder", "record start")
        audioRecorder.startRecording()
        val recordingState: Int = audioRecorder.recordingState
        Log.d("AudioRecorder", "recordingState = $recordingState")
    }

    fun stopRecording()
    {

        Log.d("AudioRecorder", "record stop");
        audioRecorder.stop();
        recognize()
    }

    private fun recognize()
    {
        val handler = createErrorHandler(::showError)
        launchUI(handler) {
            var result = "Not result :("
            asyncIO {
                val audioData = ShortArray(audioRecorder.bufferSizeInFrames)
                var res = audioRecorder.read(audioData,0,bufferSize)
                audioRecorder.release()
                //result = voiceRecognitionUseCase(audioData)
            }.await()
            viewState.setRecognitionResult(result)
            //navigationListener(InfoScreenChecked())
        }
    }


    private fun nextScreen() {
        val handler = createErrorHandler(::showError)
        launchUI(handler) {
            //withIO { createWheelPairUseCase() }
            //navigationListener(InfoScreenChecked())
        }
    }

    private fun showError(info: ExceptionInfo) {
        viewState.showErrorAlert(info)
        //viewState.showContent()
    }
}