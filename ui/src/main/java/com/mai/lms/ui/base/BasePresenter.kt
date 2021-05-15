package com.mai.lms.ui.base


import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import moxy.MvpPresenter
import org.koin.core.KoinComponent

abstract class BasePresenter<View : BaseView> : MvpPresenter<View>(), CoroutineScope, KoinComponent {

    private val job = SupervisorJob()
    override val coroutineContext = Dispatchers.Main + job
}
