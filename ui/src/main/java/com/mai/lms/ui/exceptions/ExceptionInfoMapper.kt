package com.mai.lms.ui.exceptions

import com.mai.lms.ui.R
import java.io.IOException


internal class ExceptionInfoMapper {

    fun map(exception: Throwable): ExceptionInfo {
        return if (exception is IOException) {
            ExceptionInfo(
                exception,
                R.string.internet_exception_description,
                R.string.internet_exception_title
            )
        } else {
            ExceptionInfo(
                exception,
                R.string.error_alert_default_description,
                R.string.unknown_exception_title
            )
        }
    }
}
