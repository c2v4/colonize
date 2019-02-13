package com.c2v4.colonize.domain

data class ActionEffect(val newState: State, val causedActions:List<Action> = emptyList(), val expectedActions:Set<ExpectedAction> = emptySet())
