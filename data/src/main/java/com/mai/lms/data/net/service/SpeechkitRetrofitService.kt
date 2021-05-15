package com.mai.lms.data.net.service

import com.mai.lms.domain.models.RecognitionResult
import okhttp3.RequestBody
import retrofit2.http.*

interface SpeechkitRetrofitService {

    @Headers("Transfer-Encoding: chunked")
    @POST("speech/v1/stt:recognize")
    suspend fun recognize(
        @Query("format") format: String,
        @Query("sampleRateHertz") sampleRateHertz: String,
        @Body voice: RequestBody
    ) : RecognitionResult
}