package com.mai.lms.ui.navigator.events

import com.mai.lms.ui.navigator.NavigationEvent

sealed class OutsideNavigationEvent : NavigationEvent {

    object ExitAccountEvent : OutsideNavigationEvent()

    object RestartWithSameAccountEvent : OutsideNavigationEvent()

    object CloseCurrentScreenEvent : OutsideNavigationEvent()
}