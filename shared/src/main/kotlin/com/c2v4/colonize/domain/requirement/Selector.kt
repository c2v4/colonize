package com.c2v4.colonize.domain.requirement

import com.c2v4.colonize.domain.State


sealed class Selector<T : Comparable<T>> {
    abstract operator fun invoke(state: State): T
}

object Temperature : Selector<Int>() {
    override fun invoke(state: State) = state.temperature
}
