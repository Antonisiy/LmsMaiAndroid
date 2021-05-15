package com.mai.lms.ui.navigator



// NOTE!
// Actions must be kept very simple and small - it is cached in the DB.
// Only the vital screen and navigation params should be here
sealed class NavigationAction

data class OpenScreenNavigationAction(
    val screen: AppScreen,
    val type: NavigationType,
    val isSkipCache: Boolean = false,
    val toolbarMode: ToolbarMode = ToolbarMode.WHEEL_PAIR
) : NavigationAction()

object ExitScreenNavigationAction : NavigationAction()

enum class NavigationType {
    MAKE_ROOT,
    ADD_ON_TOP
}

enum class ToolbarMode(val isExitVisible: Boolean) {
    WHEEL_PAIR(false),
    CARGO_DATA_NEW(false),
    SHIPMENT(true),
    CARGO_DATA_SAME_ACCOUNT(true)
}

infix fun NavigationType.screen(screen: AppScreen) =
    OpenScreenNavigationAction(screen, this)

infix fun NavigationType.screenSkipCache(screen: AppScreen) =
    OpenScreenNavigationAction(screen, this, true)

infix fun OpenScreenNavigationAction.withToolbar(mode: ToolbarMode) =
    copy(toolbarMode = mode)
