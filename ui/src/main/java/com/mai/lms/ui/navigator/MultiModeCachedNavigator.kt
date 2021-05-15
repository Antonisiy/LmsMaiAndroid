package com.mai.lms.ui.navigator


import com.mai.lms.domain.ext.launchUI
import com.mai.lms.domain.ext.withIO
import com.mai.lms.ui.exceptions.ErrorAlertShower
import com.mai.lms.ui.exceptions.createErrorHandler
import com.mai.lms.ui.navigator.events.OutsideNavigationEvent
import com.mai.lms.ui.navigator.events.PreparationNavigationEvent
import com.mai.lms.ui.navigator.events.STTestEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import ru.terrakok.cicerone.Router

// TODO: write tests
class MultiModeCachedNavigator(
    private val router: Router,
    private val errorAlertShower: ErrorAlertShower,
) : NavigationListener, CoroutineScope {

    override val coroutineContext = Dispatchers.Main + SupervisorJob()

    override fun invoke(event: NavigationEvent) {
        val handler = createErrorHandler(errorAlertShower::show)
        launchUI(handler) {
            val action = withIO { chooseAction(event) }
            executeAction(action) ?: throw UnknownNavigationEventException(event)
        }
    }

    private suspend fun chooseAction(event: NavigationEvent) =
        when (event) {
            is PreparationNavigationEvent -> getPreparationAction(event)
            is OutsideNavigationEvent -> getOutsideAction(event)
            else -> getMainAction(event)
        }

    // actions before the shift starts
    private fun getPreparationAction(event: PreparationNavigationEvent) =
        when (event) {
            is PreparationNavigationEvent.AppInitializedEvent -> {
                NavigationType.MAKE_ROOT screenSkipCache
                        HomeScreen()
            }
            is PreparationNavigationEvent.ConfigUpdatedEvent -> {
                NavigationType.MAKE_ROOT screenSkipCache
                        HomeScreen()
               // NavigationType.MAKE_ROOT screenSkipCache
                  //  ChooseOperatorScreen() withToolbar ToolbarMode.CARGO_DATA_NEW
            }
            is PreparationNavigationEvent.AuthenticationCompleted -> {
                NavigationType.MAKE_ROOT screenSkipCache
                        HomeScreen()
               // NavigationType.MAKE_ROOT screenSkipCache
                  // SelectTypeSessionScreen() withToolbar ToolbarMode.CARGO_DATA_NEW
            }
        }

    // secondary actions that are always available during the shift
    private suspend fun getOutsideAction(event: OutsideNavigationEvent) = when (event) {
        OutsideNavigationEvent.ExitAccountEvent -> {
            NavigationType.MAKE_ROOT screenSkipCache
                    HomeScreen()
            //restoreNavigationToStart()
            //NavigationType.MAKE_ROOT screenSkipCache
               //ChooseOperatorScreen()
        }
        OutsideNavigationEvent.RestartWithSameAccountEvent -> {
            NavigationType.MAKE_ROOT screenSkipCache
                    HomeScreen()
            //NavigationType.MAKE_ROOT screenSkipCache
                //SelectTypeSessionScreen()
        }
        OutsideNavigationEvent.CloseCurrentScreenEvent -> {
            ExitScreenNavigationAction
        }
    }

    private fun getMainAction(event: NavigationEvent) : NavigationAction{
        return when(event)
        {
            STTestEvent -> {
                NavigationType.MAKE_ROOT screenSkipCache RecognizeScreen()
            }
            else -> NavigationType.MAKE_ROOT screenSkipCache HomeScreen()
        }
    }






    private fun executeAction(action: NavigationAction) {
        when (action) {
            is OpenScreenNavigationAction -> {
                openScreen(action)
            }
            is ExitScreenNavigationAction -> {
                router.exit()
            }
        }
    }


    private fun openScreen(action: OpenScreenNavigationAction) {
        when (action.type) {
            NavigationType.MAKE_ROOT -> router.newRootScreen(action.screen)
            NavigationType.ADD_ON_TOP -> router.navigateTo(action.screen)
        }
    }
}

class UnknownNavigationEventException(event: NavigationEvent) :
    IllegalStateException("Navigation event could not be handled. Details:\n$event")
