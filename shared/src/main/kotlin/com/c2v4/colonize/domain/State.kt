package com.c2v4.colonize.domain

import arrow.optics.Getter
import arrow.optics.Lens

data class State(
    val numberOfPlayers: Int = 0,
    val globalParameters: GlobalParameters = GlobalParameters(),
    val surfaceMap: SurfaceMap = SurfaceMap(),
    val milestones: Milestones = Milestones(),
    val awards: Awards = Awards()
)

const val MAX_TEMPERATURE = 8
const val MAX_OXYGEN = 14
const val MAX_NUMBER_OF_OCEANS = 9

fun endGameCheck(state: State): Boolean {
    return temperatureLens.get(state) == MAX_TEMPERATURE && oxygenLens.get(state) == MAX_OXYGEN && oceansLens.get(state) == MAX_NUMBER_OF_OCEANS
}

val temperatureLens: Lens<State, Int> =
    Lens(
        get = { s -> s.globalParameters.temperature },
        set = { state, value -> state.copy(globalParameters = state.globalParameters.copy(temperature = value)) })

val oxygenLens: Lens<State, Int> =
    Lens(
        get = { s -> s.globalParameters.oxygen },
        set = { state, value -> state.copy(globalParameters = state.globalParameters.copy(oxygen = value)) })

val oceansLens: Getter<State, Int> = Getter(get = { s -> s.surfaceMap.numberOfOceans })
