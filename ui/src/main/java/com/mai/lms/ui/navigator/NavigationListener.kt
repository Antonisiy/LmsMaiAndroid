package com.mai.lms.ui.navigator

 interface NavigationEvent
interface NavigationListener {
    operator fun invoke(event: NavigationEvent)
}