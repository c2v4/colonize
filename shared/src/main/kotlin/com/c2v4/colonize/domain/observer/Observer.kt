package com.c2v4.colonize.domain.observer

import com.c2v4.colonize.domain.Action
import com.c2v4.colonize.domain.State

interface Observer {
    fun isApplicable(action: Action, state: State): Boolean
    fun react(action: Action, state: State): Action
}
