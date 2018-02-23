package com.c2v4.colonize.domain.card.requirement

sealed class Condition<in T : Comparable<T>> {
    abstract operator fun invoke(value: T): Boolean
}

class AtMost<in T : Comparable<T>>(private val value: T) : Condition<T>() {
    override operator fun invoke(value: T): Boolean = (value <= this.value)
}
