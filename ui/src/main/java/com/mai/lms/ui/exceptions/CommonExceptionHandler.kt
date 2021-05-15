package com.mai.lms.ui.exceptions

import kotlinx.coroutines.CoroutineExceptionHandler

class CommonExceptionHandler(private val onError: ((ExceptionInfo) -> Unit)? = null) {

    private val exceptionInfoMapper by lazy { ExceptionInfoMapper() }

    val coroutineHandler = CoroutineExceptionHandler { _, throwable ->
        //FirebaseCrashlytics.getInstance().recordException(throwable)
        throwable.printStackTrace()
        //crashlytics.recordException(throwable)
        onError?.let {
            val exceptionInfo = exceptionInfoMapper.map(throwable)
            it.invoke(exceptionInfo)
        }
    }
}


fun createErrorHandler(onError: (ExceptionInfo) -> Unit) =
    CommonExceptionHandler(onError).coroutineHandler

fun createEmptyErrorHandler() = createErrorHandler { /* pass */ }