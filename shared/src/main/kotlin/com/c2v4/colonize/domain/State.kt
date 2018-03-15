package com.c2v4.colonize.domain

import com.c2v4.colonize.domain.action.Action
import com.c2v4.colonize.domain.action.Combined
import com.c2v4.colonize.domain.action.Consequent
import com.c2v4.colonize.domain.action.Pass
import com.c2v4.colonize.domain.card.Card
import com.c2v4.colonize.domain.observer.Observer
import com.c2v4.colonize.util.checkArgument
import com.c2v4.colonize.util.plus

data class State(val players: List<Player> = emptyList(),
                 val wallets: Map<Player, Map<Resource, Int>> = emptyMap(),
                 val incomes: Map<Player, Map<Resource, Int>> = emptyMap(),
                 val hands: Map<Player,List<Card>> = emptyMap(),
                 val availableActions: Map<Player, Action> = emptyMap(),
                 val conversionRates: Map<Player, Map<Resource, Int>> = emptyMap(),
                 val points: Map<Player, Int> = emptyMap(),
                 val currentPlayer: Int = 0,
                 val nextStartingPlayer: Int = 1,
                 val actionsPlayed: Int = 0,
                 val consecutivePasses: Int = 0,
                 val observers: List<Observer> = emptyList(),
                 val temperature: Int = -30,
                 val oxygen: Int = 0,
                 val generation: Int = 1)

fun State.apply(action: Action): State =
    checkArgument(action.isApplicable(this)).let {
        updateTurnChecked(action)
    }.let {
            checkEndOfGeneration(action, it)
        }

private fun checkEndOfGeneration(action: Action,
                                 it: State): State {
    return if (action is Pass) {
        if (it.consecutivePasses == it.players.size) {
            endGeneration(it)
        } else {
            it
        }
    } else {
        it.copy(consecutivePasses = 0)
    }
}

private fun State.updateWithObservers(action: Action): State = action(this).let { state ->
    state.observers.filter {
        it.isApplicable(action, state)
    }.fold(state, { acc, observer ->
            acc.dispatch(observer.react(action, acc))
        })
}


private fun State.updateTurnChecked(action: Action): State {
    dispatch(action).let {
        return if (it.actionsPlayed > 0) {
            nextTurn(it)
        } else {
            it.copy(actionsPlayed = it.actionsPlayed + 1)
        }
    }
}

fun State.dispatch(action: Action): State {
    return when (action) {
        is Combined -> action.actions.fold(this, { acc, internal ->
            acc.dispatch(internal)
        })
        is Consequent -> action.actions.fold(this, { acc, internal ->
            acc.dispatch(internal)
        })
        else -> updateWithObservers(action)
    }

}

private fun nextTurn(state: State) =
    state.copy(actionsPlayed = 0,
        currentPlayer = (state.currentPlayer + 1) % state.players.size)

private fun endGeneration(state: State): State = state.copy(
    currentPlayer = state.nextStartingPlayer,
    nextStartingPlayer = (state.nextStartingPlayer + 1) % state.players.size,
    consecutivePasses = 0,
    wallets = state.wallets.plus(state.incomes).keys.map {
        val newWallet = (state.wallets[it]?: emptyMap()).plus(state.incomes[it] ?: emptyMap())
        it to newWallet.plus(Resource.GOLD to (newWallet.getOrDefault(Resource.GOLD,
            0) + state.points.getOrDefault(it,
            0)))
    }.toMap())
