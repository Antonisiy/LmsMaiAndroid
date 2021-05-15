package com.mai.lms.domain.usecase.stt

import com.mai.lms.domain.datacontract.net.SpeechkitNetRepository

class VoiceRecognitionUseCase(private val speechkitRetrofitRepository: SpeechkitNetRepository) {
    suspend operator fun invoke(voiceData: ByteArray) : String
    {
        return speechkitRetrofitRepository.recognizeSpeech(voiceData);
    }
}