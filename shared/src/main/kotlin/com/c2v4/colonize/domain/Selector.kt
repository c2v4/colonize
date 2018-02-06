package com.c2v4.colonize.domain


sealed class Selector<T : Comparable<T>> {
    abstract fun invoke(state: State): T
}

object Temperature : Selector<Int>() {
    override fun invoke(state: State) = state.temperature
}
