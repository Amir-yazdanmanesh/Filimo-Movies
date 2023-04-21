package com.test.core.base

import android.util.Log
import com.test.common_jvm.ResultWrapper
import com.test.common_jvm.succeeded

abstract class BaseMiddleware<STATE : State, ACTION : Action> {

    abstract suspend fun process(
        action: ACTION,
        currentState: STATE,
        store: Store<STATE, ACTION>,
    )


    suspend fun <T> ResultWrapper<T>.manageResult(store: Store<STATE, ACTION>): T? {
        if (!succeeded) {
            store.setEffect(BaseEffect.ShowToast(error?.message ?: "unKnown error"))
            Log.e("Error Happened", error?.message ?: "unKnown error")
            return null
        }
        return data
    }

}
