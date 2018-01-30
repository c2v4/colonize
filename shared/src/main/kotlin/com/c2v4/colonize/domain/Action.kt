package com.c2v4.colonize.domain

import com.c2v4.colonize.util.checkArgument

sealed class Action {
    abstract fun isApplicable(player: Player, state: State): Boolean
    abstract operator fun invoke(player: Player, state: State): State
}

fun Action.combined(another:Action) = Combined(this,another)

fun Action.consequent(another:Action) = Consequent(this,another)

object None : Action() {
    override fun isApplicable(player: Player, state: State): Boolean {
        return true
    }

    override operator fun invoke(player: Player, state: State) = state.copy()
}

class SpendResource(private val resources: Map<Resource, Int>) : Action() {
    override operator fun invoke(player: Player, state: State): State {
        checkArgument(isApplicable(player, state))
        return state.copy(wallets = state.wallets.plus(player to removeResources(state.wallets[player]!!,
                resources)))
    }

    private fun removeResources(wallet: Map<Resource, Int>,
                                resources: Map<Resource, Int>): Map<Resource, Int> =
            wallet.map { (resource, amount) ->
                resource to amount - resources.getOrDefault(resource, 0)
            }.filter { (_, amount) -> amount > 0 }.toMap()

    override fun isApplicable(player: Player, state: State): Boolean =
            state.wallets[player]?.let {
                resources.entries.all { (resource, amount) ->
                    it[resource] ?: 0 >= amount
                }
            } ?: false

}

class GiveResource(private val resources: Map<Resource, Int>) : Action() {
    override fun isApplicable(player: Player, state: State) = true

    override fun invoke(player: Player, state: State): State {
        return state.let {
            it.copy(wallets = it.wallets.plus(player
                    to (it.wallets[player]?.map {
                it.key to it.value + resources.getOrDefault(it.key,
                        0)
            }?.toMap() ?: emptyMap())))
        }
    }
}

data class Combined(val first: Action, val second: Action) : Action() {
    override fun isApplicable(player: Player, state: State): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun invoke(player: Player, state: State): State {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

data class Consequent(val prerequisite: Action, val effect: Action) : Action() {
    override fun invoke(player: Player, state: State): State {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun isApplicable(player: Player, state: State): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}