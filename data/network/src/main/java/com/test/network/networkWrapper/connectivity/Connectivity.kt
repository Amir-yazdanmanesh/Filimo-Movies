package com.test.network.networkWrapper.connectivity

import com.test.network.networkWrapper.General

class Connectivity(val status: Int, val callback: (() -> Unit?)? = null) {
    companion object {
        val IDS = listOf(General.CONNECT, General.DISCONNECT, General.OFFLINE_MODE)
    }
}