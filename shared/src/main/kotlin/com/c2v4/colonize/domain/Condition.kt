package com.c2v4.colonize.domain

sealed class Condition{
    abstract fun invoke(): Boolean
}

class AtMost:Condition(){
    override fun invoke(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}