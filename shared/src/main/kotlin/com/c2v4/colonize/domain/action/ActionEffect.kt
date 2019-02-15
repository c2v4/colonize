package com.c2v4.colonize.domain.action

import com.c2v4.colonize.domain.ExpectedAction
import com.c2v4.colonize.domain.State

data class ActionEffect(val newState: State, val causedActions:List<Action> = emptyList(), val expectedActions:Set<ExpectedAction> = emptySet())
