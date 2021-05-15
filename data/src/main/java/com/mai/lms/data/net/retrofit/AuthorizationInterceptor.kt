package com.mai.lms.data.net.retrofit

import okhttp3.Interceptor
import okhttp3.Response

class AuthorizationInterceptor(
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain
            .request()
            .newBuilder()
            .addHeader("Authorization", "Api-Key AQVN3bZ1mZtH92P_Z5GoMiqxk21DLE12_aG9wpEz")
            .build()
        return chain.proceed(request)
    }
}
