package com.mai.lms.ui.navigator.events

import com.mai.lms.ui.navigator.NavigationEvent

sealed class PreparationNavigationEvent : NavigationEvent {

    object AppInitializedEvent : PreparationNavigationEvent()

    object ConfigUpdatedEvent : PreparationNavigationEvent()

    object AuthenticationCompleted : PreparationNavigationEvent()
}
