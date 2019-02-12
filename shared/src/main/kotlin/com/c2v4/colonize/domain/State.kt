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

fun endGameCheck(state: State) =
    temperatureLens.get(state) == 8 && oxygenLens.get(state) == 14 && oceansLens.get(state) == 9

val temperatureLens: Lens<State, Int> =
    Lens(
        get = { s -> s.globalParameters.temperature },
        set = { state, value -> state.copy(globalParameters = state.globalParameters.copy(temperature = value)) })

val oxygenLens: Lens<State, Int> =
    Lens(
        get = { s -> s.globalParameters.oxygen },
        set = { state, value -> state.copy(globalParameters = state.globalParameters.copy(oxygen = value)) })

val oceansLens: Getter<State, Int> = Getter(get = { s -> s.surfaceMap.numberOfOceans })
