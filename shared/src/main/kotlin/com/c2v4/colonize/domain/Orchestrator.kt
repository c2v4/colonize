package com.c2v4.colonize.domain

class Orchestrator(private var state: State = State()) {

    fun processAction(action: Action) {
        state = action.invoke(state)
        state.observers.filter { it.isAplicabe(action, state) }.forEach { processAction(it.react(action, state)) }
    }
}