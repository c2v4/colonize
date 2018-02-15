package com.c2v4.colonize.domain

import com.c2v4.colonize.util.checkArgument
import com.c2v4.colonize.util.minus
import com.c2v4.colonize.util.plus

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

    override operator fun invoke(state: State) = state
}

class SpendResource(private val resources: Map<Resource, Int>,
                    private val player: Player) : Action() {
    override operator fun invoke(state: State): State {
        checkArgument(isApplicable(state))
        return state.copy(wallets = state.wallets.plus(player to (state.wallets[player]?.minus(
                resources))!!))
    }

    override fun isApplicable(state: State): Boolean =
            state.wallets.getOrDefault(player, emptyMap()).let {
                resources.entries.all { (resource, amount) ->
                    it[resource] ?: 0 >= amount
                }
            }

}

class GiveResource(private val resources: Map<Resource, Int>,
                   private val player: Player) : Action() {
    override fun isApplicable(state: State) = true

    override fun invoke(state: State): State {
        return state.let {
            it.copy(wallets = it.wallets.plus(player
                    to (it.wallets[player]?.plus(resources) ?: emptyMap())))
        }
    }
}

class TurnChecked(private val action: Action) : Action() {

    override fun isApplicable(state: State): Boolean = action.isApplicable(state)

    override fun invoke(state: State): State {
        action.invoke(state).let {
            return if (it.actionsPlayed > 0) {
                nextTurn(it)
            } else {
                it.copy(actionsPlayed = it.actionsPlayed + 1)
            }
        }
    }

}

class Pass(private val player: Player) : Action() {

    override fun isApplicable(state: State) = state.players[state.currentPlayer] == player

    override fun invoke(state: State): State = nextTurn(state).copy(consecutivePasses = state.consecutivePasses + 1)

}

class Combined(private val actions: List<Action>) : Action() {
    override fun isApplicable(state: State): Boolean = actions.all { it.isApplicable(state) }

    override fun invoke(state: State): State = actions.fold(state,
            { acc, action -> action.invoke(acc) })
}

class Consequent(private val actions: List<Action>) : Action() {

    override fun isApplicable(state: State): Boolean =
            actions.fold(state to true, { (newState, applicableSoFar), action ->
                if (applicableSoFar && action.isApplicable(newState)) {
                    action(newState) to true
                } else {

                    newState to false
                }
            }).second

    override fun invoke(state: State): State = actions.fold(state,
            { acc, action -> action.invoke(acc) })


}

private fun nextTurn(state: State) =
        state.copy(actionsPlayed = 0,
                currentPlayer = (state.currentPlayer + 1) % state.players.size)
