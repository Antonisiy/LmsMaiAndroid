package com.mai.lms.app.di

import com.mai.lms.domain.usecase.stt.VoiceRecognitionUseCase
import org.koin.core.module.Module
import org.koin.dsl.module


fun provideDomain() = module {
    provideUseCases()
}

private fun Module.provideUseCases() {
    provideSpeechkitUseCase()
}

private fun Module.provideSpeechkitUseCase() {
    single { VoiceRecognitionUseCase(get()) }
}