package com.test.network.networkWrapper.connectivity

import com.test.network.networkWrapper.connectivity.Connectivity

internal interface ConnectivityPublisherDelegate {
    fun receiveConnectivity(connectivity: Connectivity)
}