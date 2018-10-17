package com.c2v4.colonize.domain.action

import com.c2v4.colonize.domain.Player
import com.c2v4.colonize.domain.Resource
import com.c2v4.colonize.domain.State
import com.c2v4.colonize.domain.card.Card
import com.c2v4.colonize.domain.observer.Observer
import com.c2v4.colonize.util.checkArgument
import com.c2v4.colonize.util.minus
import com.c2v4.colonize.util.plus

sealed class Action {
    abstract fun isApplicable(state: State): Boolean
    abstract operator fun invoke(state: State): State
}

fun Action.combined(another: Action) = Combined(listOf(this, another))

fun Action.consequent(another: Action) = Consequent(listOf(this, another))

object None : Action() {
    override fun isApplicable(state: State): Boolean {
        return true
    }

    override operator fun invoke(state: State) = state
}

data class SpendResource(
    private val resources: Map<Resource, Int>,
    private val player: Player
) : Action() {
    override operator fun invoke(state: State): State {
        checkArgument(isApplicable(state))
        return state.copy(
            wallets = state.wallets.plus(
                player to (state.wallets[player]?.minus(
                    resources
                ))!!
            )
        )
    }

    override fun isApplicable(state: State): Boolean =
        state.wallets.getOrDefault(player, emptyMap()).let {
            resources.entries.all { (resource, amount) ->
                it[resource] ?: 0 >= amount
            }
        }
}

data class GiveResource(
    private val resources: Map<Resource, Int>,
    private val player: Player
) : Action() {
    override fun isApplicable(state: State) = true

    override fun invoke(state: State): State {
        return state.let {
            it.copy(
                wallets = it.wallets.plus(
                    player
                        to ((it.wallets[player] ?: emptyMap()).plus(resources))
                )
            )
        }
    }
}

data class Pass(private val player: Player) : Action() {

    override fun isApplicable(state: State) = state.players[state.currentPlayer] == player

    override fun invoke(state: State): State = state.copy(
        actionsPlayed = 1,
        consecutivePasses = state.consecutivePasses + 1
    )
}

data class AddObserver(private val observer: Observer) : Action() {

    override fun isApplicable(state: State): Boolean = true

    override fun invoke(state: State): State = state.copy(observers = state.observers.plus(observer))
}

data class PlayCard(
    private val player: Player,
    private val card: Card,
    private val action: Action
) : Action() {
    override fun isApplicable(state: State): Boolean = card.actionScheme.isValid(action)

    override fun invoke(state: State): State {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

data class Combined(val actions: List<Action>) : Action() {
    override fun isApplicable(state: State): Boolean = actions.all { it.isApplicable(state) }

    override fun invoke(state: State): State = actions.fold(state) {
            acc, action -> action.invoke(acc) }
}

data class Consequent(val actions: List<Action>) : Action() {

    override fun isApplicable(state: State): Boolean =
        actions.fold(state to true) { (newState, applicableSoFar), action ->
            if (applicableSoFar && action.isApplicable(newState)) {
                action(newState) to true
            } else {

                newState to false
            }
        }.second

    override fun invoke(state: State): State = actions.fold(
        state
    ) { acc, action -> action.invoke(acc) }
}
