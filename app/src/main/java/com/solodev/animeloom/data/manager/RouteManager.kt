package com.solodev.animeloom.data.manager

import android.content.Context
import com.solodev.animeloom.presentation.navgraph.Route
import com.solodev.animeloom.utils.SharedPreferenceDelegate
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class RouteManager @Inject constructor(@ApplicationContext context: Context) {
    var lastRoute: String by SharedPreferenceDelegate(
        context,
        "last_route",
        Route.AnimesNavigation.route
    )
}