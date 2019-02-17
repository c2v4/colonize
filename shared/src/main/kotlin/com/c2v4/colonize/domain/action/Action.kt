package com.c2v4.colonize.domain.action

import com.c2v4.colonize.domain.State

interface Action {
    operator fun invoke(state: State): ActionEffect
}


