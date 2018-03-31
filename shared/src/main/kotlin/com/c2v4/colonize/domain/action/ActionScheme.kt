package com.c2v4.colonize.domain.action

sealed class ActionScheme {
    abstract fun isValid(action: Action): Boolean
}

object EmptyScheme : ActionScheme() {
    override fun isValid(action: Action) = true
}
