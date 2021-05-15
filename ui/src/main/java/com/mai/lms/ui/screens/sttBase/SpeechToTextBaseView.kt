package com.mai.lms.ui.screens.sttBase

import com.mai.lms.ui.base.BaseView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface SpeechToTextBaseView : BaseView {
    fun setRecognitionResult(result: String)
}