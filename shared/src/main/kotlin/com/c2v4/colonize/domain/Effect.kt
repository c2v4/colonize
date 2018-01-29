package com.c2v4.colonize.domain

import com.c2v4.colonize.util.checkArgument

sealed class Effect {
    abstract fun isApplicable(player: Player, state: State): Boolean
    abstract operator fun invoke(player: Player, state: State): State
}

object None : Effect() {
    override fun isApplicable(player: Player, state: State): Boolean {
        return true
    }

    override operator fun invoke(player: Player, state: State) = state.copy()
}

class SpendResource(private val resources: Map<Resource, Int>) : Effect() {
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

class GiveResource(private val resources: Map<Resource, Int>) : Effect() {
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