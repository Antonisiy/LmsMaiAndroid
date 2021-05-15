package com.mai.lms.ui.navigator


import com.mai.lms.ui.screens.home.HomeFragment
import com.mai.lms.ui.screens.sttBase.SpeechToTextBaseFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

sealed class AppScreen : SupportAppScreen()

class HomeScreen : AppScreen() {
    override fun getFragment() = HomeFragment()
}

class RecognizeScreen : AppScreen() {
    override fun getFragment() = SpeechToTextBaseFragment()
}


