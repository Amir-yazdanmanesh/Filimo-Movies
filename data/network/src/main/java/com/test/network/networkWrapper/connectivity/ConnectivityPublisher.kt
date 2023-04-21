package com.test.network.networkWrapper.connectivity

internal object ConnectivityPublisher {
    private val subscribers = Connectivity.IDS.map { mutableListOf<ConnectivityPublisherDelegate>() }
    fun notifySubscribers(connectivity: Connectivity) = subscribers[connectivity.status].forEach { it.receiveConnectivity(connectivity) }
}
