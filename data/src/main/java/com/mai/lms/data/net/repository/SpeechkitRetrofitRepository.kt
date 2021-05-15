package com.mai.lms.data.net.repository

import com.mai.lms.data.net.service.SpeechkitRetrofitService
import com.mai.lms.domain.datacontract.net.SpeechkitNetRepository
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Retrofit

class SpeechkitRetrofitRepository(
    private val retrofit: Retrofit
) : SpeechkitNetRepository {

    private val service by lazy { retrofit.create(SpeechkitRetrofitService::class.java) }

    override suspend fun recognizeSpeech(data: ByteArray): String {
        val requestBody = data.toRequestBody("binary".toMediaTypeOrNull())
        return service.recognize("lpcm", "48000", requestBody).result
    }
}