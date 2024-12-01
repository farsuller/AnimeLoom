package com.solodev.animeloom.presentation

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.play.core.appupdate.AppUpdateInfo
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.appupdate.AppUpdateOptions
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability
import com.solodev.animeloom.data.manager.RouteManager
import com.solodev.animeloom.domain.usecase.AppEntryUseCases
import com.solodev.animeloom.presentation.navgraph.Route
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val appEntryUseCases: AppEntryUseCases,
    private val routeManager: RouteManager,
    @ApplicationContext private val context: Context
) : ViewModel() {

    var splashCondition by mutableStateOf(true)
        private set

    var startDestination by mutableStateOf(Route.AppStartNavigation.route)
        private set

    private val _appUpdateInfo = MutableStateFlow<AppUpdateInfo?>(null)
    val appUpdateInfo: StateFlow<AppUpdateInfo?> = _appUpdateInfo

    init {
        viewModelScope.launch {
            checkAppUpdate()
            delay(500)
            checkAppEntry()
        }
    }
    private fun checkAppUpdate() {
        val appUpdateManager = AppUpdateManagerFactory.create(context)
        val appUpdateInfoTask = appUpdateManager.appUpdateInfo

        appUpdateInfoTask.addOnSuccessListener { appUpdateInfo ->
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE &&
                appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)
            ) {
                _appUpdateInfo.value = appUpdateInfo
            }
        }
    }

    private fun checkAppEntry() {
        appEntryUseCases.readAppEntry().onEach { startHomeScreen ->
            startDestination = if (startHomeScreen) Route.AnimesNavigation.route else Route.AppStartNavigation.route

            delay(500)
            splashCondition = false
        }.launchIn(viewModelScope)
    }

    fun saveRoute(route: String) {
        routeManager.lastRoute = route
    }

    fun getLastRoute(): String {
        return routeManager.lastRoute
    }
}
