package networkWrapper.connectivity

import networkWrapper.connectivity.Connectivity

internal interface ConnectivityPublisherDelegate {
    fun receiveConnectivity(connectivity: Connectivity)
}