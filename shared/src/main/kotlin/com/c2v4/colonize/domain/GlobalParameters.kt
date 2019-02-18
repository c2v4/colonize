package com.c2v4.colonize.domain

import arrow.optics.Lens
import arrow.optics.PLens

data class GlobalParameters(val generation: Int = 0, val temperature: Int = -30, val oxygen: Int = 0)

val temperatureLens: Lens<State, Int> =
    Lens(
        get = { s -> s.globalParameters.temperature },
        set = { state, value ->
            state.copy(
                globalParameters = state.globalParameters.copy(
                    temperature = value
                )
            )
        })

val oxygenLens: Lens<State, Int> =
    Lens(
        get = { s -> s.globalParameters.oxygen },
        set = { state, value -> state.copy(globalParameters = state.globalParameters.copy(oxygen = value)) })
const val MAX_TEMPERATURE = 8
const val MAX_OXYGEN = 14
