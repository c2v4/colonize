package com.c2v4.colonize.domain

import com.google.common.base.Preconditions

sealed class Prerequisite {
    abstract fun isApplicable(player: Player, state: State): Boolean
    abstract operator fun invoke(player: Player, state: State): State
}

object None : Prerequisite() {
    override fun isApplicable(player: Player, state: State): Boolean {
        return true
    }

    override operator fun invoke(player: Player, state: State): State {
        return state.copy()
    }
}

class SpendResource(private val resources: Map<Resource, Int>) : Prerequisite() {
    override operator fun invoke(player: Player, state: State): State {
        Preconditions.checkArgument(isApplicable(player, state))
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