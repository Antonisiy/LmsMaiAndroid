package com.mai.lms.data.net.retrofit

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest

class NetConnectionStatusChecker(application: Application) : ConnectivityManager.NetworkCallback() {

    private val connectivityManager: ConnectivityManager =
        application.baseContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    private var isInternetAvailable = false

    init {
        registerInternetConnectionMonitor()
    }

    private fun registerInternetConnectionMonitor() {
        val request = NetworkRequest.Builder().build()
        connectivityManager.registerNetworkCallback(request, this)
    }

    override fun onAvailable(network: Network) {
        super.onAvailable(network)
        isInternetAvailable = true
    }

    override fun onLost(network: Network) {
        super.onLost(network)
        isInternetAvailable = false
    }

    fun isActive(): Boolean {
        return isInternetAvailable
    }
}
