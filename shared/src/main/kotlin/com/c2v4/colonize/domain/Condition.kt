package com.c2v4.colonize.domain

sealed class Condition{
    abstract fun invoke(): Boolean
}

class AtMost:Condition(){
    override fun invoke(): Boolean = true

}