package com.mai.lms.domain.datacontract.net

interface SpeechkitNetRepository {

    suspend fun recognizeSpeech(data: ByteArray): String

}