package com.c2v4.colonize.domain

sealed class Condition<in T : Comparable<T>> {
    abstract fun invoke(value: T): Boolean
}

class AtMost<in T : Comparable<T>>(private val threshold: T) : Condition<T>() {
    override fun invoke(value: T): Boolean = (value <= threshold)
}