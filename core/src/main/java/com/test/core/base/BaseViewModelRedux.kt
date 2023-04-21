package com.test.core.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*

/** events are normal fun and states are stream */
abstract class BaseViewModelRedux<STATE : State, ACTION : Action> constructor(
    store: Store<STATE, ACTION>,
) : ViewModel() {
    protected abstract val initialState: STATE

    val viewState: StateFlow<STATE> = store.state.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), initialState)
    val currentState: STATE
        get() = viewState.value

    val uiEffect = store.effect.receiveAsFlow()

}