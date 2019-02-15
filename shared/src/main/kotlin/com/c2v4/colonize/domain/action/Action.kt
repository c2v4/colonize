package com.c2v4.colonize.domain.action

import com.c2v4.colonize.domain.*

interface Action {
    operator fun invoke(state: State): ActionEffect
}


