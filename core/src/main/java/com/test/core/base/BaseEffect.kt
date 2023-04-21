package com.test.core.base

import utils.ToastyMode

sealed interface BaseEffect {
    // single event/single action to action happened once
    data class ShowToast(
        val message: String,
        @ToastyMode val mode: Int = ToastyMode.MODE_TOAST_DEFAULT
    ) : BaseEffect
}
