package com.mai.lms.app.di

import com.mai.lms.data.net.repository.SpeechkitRetrofitRepository
import com.mai.lms.data.net.retrofit.*
import com.mai.lms.domain.datacontract.net.SpeechkitNetRepository
import org.koin.core.module.Module
import org.koin.dsl.module

fun provideNet() = module{
    single { NetConnectionStatusChecker(get()) }
    provideRetrofit()
    provideRepositories()
}

private fun Module.provideRetrofit() {
    single {
        val token = AuthorizationInterceptor()
        val logs = LoggingInterceptorFactory.create()
        val netConnection = get<NetConnectionStatusChecker>()
        val exception = ServerExceptionInterceptor(netConnection)
        RetrofitFactory.create(get(), token, logs, exception)
    }
}

private fun Module.provideRepositories() {
    single<SpeechkitNetRepository> { SpeechkitRetrofitRepository(get()) }
}
