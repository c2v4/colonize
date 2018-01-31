package com.c2v4.colonize.domain

import com.c2v4.colonize.util.checkArgument

sealed class Action {
    abstract fun isApplicable(state: State): Boolean
    abstract operator fun invoke(state: State): State
}

fun Action.combined(another: Action) = Combined(listOf(this, another))

fun Action.consequent(another: Action) = Consequent(listOf(this, another))

fun Action.withTurnCheck() = TurnChecked(this)


object None : Action() {
    override fun isApplicable(state: State): Boolean {
        return true
    }

    override operator fun invoke(state: State) = state.copy()
}

class SpendResource(private val resources: Map<Resource, Int>,
                    private val player: Player) : Action() {
    override operator fun invoke(state: State): State {
        checkArgument(isApplicable(state))
        return state.copy(wallets = state.wallets.plus(player to removeResources(state.wallets[player]!!,
                resources)))
    }

    private fun removeResources(wallet: Map<Resource, Int>,
                                resources: Map<Resource, Int>): Map<Resource, Int> =
            wallet.map { (resource, amount) ->
                resource to amount - resources.getOrDefault(resource, 0)
            }.filter { (_, amount) -> amount > 0 }.toMap()

    override fun isApplicable(state: State): Boolean =
            state.wallets[player]?.let {
                resources.entries.all { (resource, amount) ->
                    it[resource] ?: 0 >= amount
                }
            } ?: false

}

class GiveResource(private val resources: Map<Resource, Int>,
                   private val player: Player) : Action() {
    override fun isApplicable(state: State) = true

    override fun invoke(state: State): State {
        return state.let {
            it.copy(wallets = it.wallets.plus(player
                    to (it.wallets[player]?.map {
                it.key to it.value + resources.getOrDefault(it.key,
                        0)
            }?.toMap() ?: emptyMap())))
        }
    }
}

class TurnChecked(private val action: Action) : Action() {

    override fun isApplicable(state: State): Boolean = action.isApplicable(state)

    override fun invoke(state: State): State {
        action.invoke(state).let {
            return if (it.actionsPlayed > 1) {
                nextTurn(it)
            } else {
                it
            }
        }
    }

}

data class Pass(private val player: Player) : Action() {

    override fun isApplicable(state: State) = state.players[state.currentPlayer]==player

    override fun invoke(state: State): State {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}

data class Combined(val actions: List<Action>) : Action() {
    override fun isApplicable(state: State): Boolean {
        TODO("not implemented" ) //To change body of created functions use File | Settings | File Templates.
    }

    override fun invoke(state: State): State {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

fun Combined.combined(next: Action) = Combined(actions.plus(next))

data class Consequent(val actions: List<Action>) : Action() {
    override fun invoke(state: State): State {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun isApplicable(state: State): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}

fun Consequent.consequent(next: Action) = Consequent(actions.plus(next))

private fun nextTurn(state: State) =
        state.copy(actionsPlayed = 0, currentPlayer = (state.currentPlayer + 1) % state.players.size)
