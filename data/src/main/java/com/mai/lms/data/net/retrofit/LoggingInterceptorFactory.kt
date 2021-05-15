package com.mai.lms.data.net.retrofit

import android.util.Log
import okhttp3.logging.HttpLoggingInterceptor

class LoggingInterceptorFactory {
    companion object {

        fun create(): HttpLoggingInterceptor {
            return HttpLoggingInterceptor {
                Log.i("OKHTTP", it)
            }.apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
        }
    }
}