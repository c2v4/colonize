package com.c2v4.colonize.domain.card.requirement

import com.c2v4.colonize.domain.State

class Requirement<in T : Comparable<T>>(private val selector: Selector<T>,
                                        private val condition: Condition<T>) {
    operator fun invoke(state: State) = condition(selector(state))
}
