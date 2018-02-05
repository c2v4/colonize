package com.c2v4.colonize.domain

sealed class Condition {
    abstract fun invoke(value: Int): Boolean
}

class AtMost(private val threshold:Int) : Condition() {
    override fun invoke(value: Int): Boolean = (value <= threshold)
}