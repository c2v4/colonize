package com.c2v4.colonize.domain

sealed class Prerequisite {
    abstract fun applicable(player: Player,state: State): Boolean
    abstract fun apply(player: Player,state: State): State
}

object None : Prerequisite() {
    override fun applicable(player: Player, state: State): Boolean {
        return true
    }

    override fun apply(player: Player, state: State): State {
        return state.copy()
    }
}

class SpendResource(private val resources:Map<Resource,Int>):Prerequisite(){
    override fun apply(player: Player, state: State): State {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun applicable(player: Player, state: State): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}