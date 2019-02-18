package com.c2v4.colonize.domain.action.expectance

import com.c2v4.colonize.domain.State
import com.c2v4.colonize.domain.action.Action

interface ExpectedAction {
    fun isValid(action: Action, state: State): Boolean
    operator fun invoke(action: Action, state: State) = isValid(action, state)
}

