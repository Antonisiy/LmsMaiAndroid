package com.mai.lms.ui.base

import android.content.pm.ActivityInfo
import android.os.Bundle
import moxy.MvpAppCompatActivity
import org.koin.android.ext.android.inject
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.android.support.SupportAppNavigator


abstract class BaseActivity : MvpAppCompatActivity() {

    protected abstract fun layoutId(): Int

    protected abstract fun fragmentContainerId(): Int

    private val navigatorHolder by inject<NavigatorHolder>()

    private val navigator by lazy {
        SupportAppNavigator(this, fragmentContainerId())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId())
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }
}