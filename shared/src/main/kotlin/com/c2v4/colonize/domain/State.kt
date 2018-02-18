package com.c2v4.colonize.domain

import com.c2v4.colonize.domain.observer.Observer
import com.c2v4.colonize.util.checkArgument

data class State(val players: List<Player> = emptyList(),
                 val currentPlayer: Int = 0,
                 val wallets: Map<Player, Map<Resource, Int>> = emptyMap(),
                 val availableActions: Map<Player, Action> = emptyMap(),
                 val actionsPlayed: Int = 0,
                 val consecutivePasses: Int = 0,
                 val observers: List<Observer> = emptyList(),
                 val temperature: Int = -30)

fun State.apply(action: Action): State =
        checkArgument(action.isApplicable(this)).let {
            updateWithObservers(action)
        }

private fun State.updateWithObservers(action: Action): State = update(action).let { state ->
    state.observers.filter {
        it.isAplicabe(action,
                state)
    }.fold(state, { acc, observer -> acc.updateWithObservers(observer.react(action, acc)) })
}

private fun State.update(action: Action): State {
    action(this).let {
        return if (it.actionsPlayed > 0) {
            nextTurn(it)
        } else {
            it.copy(actionsPlayed = it.actionsPlayed + 1)
        }
    }
}

private fun nextTurn(state: State) =
        state.copy(actionsPlayed = 0,
                currentPlayer = (state.currentPlayer + 1) % state.players.size)
