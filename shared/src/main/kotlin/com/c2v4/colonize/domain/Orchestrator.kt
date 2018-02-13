package com.c2v4.colonize.domain

import com.c2v4.colonize.util.checkArgument
import java.util.*

class Orchestrator(private var state: State = State(),private val actions:Deque<Action> = LinkedList()){

    fun processAction(action: Action){
        checkArgument(action.isApplicable(state))
        state = action.invoke(state)
        state.observers.filter { it.isAplicabe(action,state) }.forEach { processAction(it.react(action, state)) }
    }
}