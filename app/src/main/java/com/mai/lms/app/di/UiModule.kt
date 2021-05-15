package com.mai.lms.app.di

import com.mai.lms.domain.ext.NotificationUtil
import com.mai.lms.ui.exceptions.AppErrorAlertShower
import com.mai.lms.ui.exceptions.ErrorAlertShower
import com.mai.lms.ui.navigator.MultiModeCachedNavigator
import com.mai.lms.ui.navigator.NavigationListener
import org.koin.core.module.Module
import org.koin.dsl.module
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router


fun provideUi() = module {
    single<ErrorAlertShower> { AppErrorAlertShower() }
    single { NotificationUtil(get()) }
    provideNavigation()
}

private fun Module.provideNavigation() {
    val router = Cicerone.create(Router())
    single { router.navigatorHolder }
    single { router.router }
    single<NavigationListener> {
        MultiModeCachedNavigator(
            get(),
            get(),
        )
    }
}


