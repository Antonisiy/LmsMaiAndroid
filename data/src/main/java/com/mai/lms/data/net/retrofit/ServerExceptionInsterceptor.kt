package com.mai.lms.data.net.retrofit

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class NoInternetException : IOException("No internet connection")

class ServerException(code: Int, message: String?) :
    IOException("An unknown server exception occurred, code $code\nmessage = $message")

class ServerExceptionInterceptor(private val netChecker: NetConnectionStatusChecker) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        if (!netChecker.isActive()) throw NoInternetException()
        val response = chain.proceed(chain.request())
        if (response.code != 200){
            val message = response.message
            throw ServerException(
                response.code,
                if (message.isNotBlank()) message else "Empty"
            )
        }
        return response
    }
}