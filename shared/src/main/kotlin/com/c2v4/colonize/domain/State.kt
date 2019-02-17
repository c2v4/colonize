package com.c2v4.colonize.domain

import arrow.optics.Getter
import arrow.optics.Lens
import arrow.syntax.function.memoize
import com.c2v4.colonize.domain.map.*

data class State(
    val globalParameters: GlobalParameters = GlobalParameters(),
    val surfaceMap: SurfaceMap = SurfaceMap(),
    val milestones: Milestones = Milestones(),
    val awards: Awards = Awards()
)

const val MAX_TEMPERATURE = 8
const val MAX_OXYGEN = 14
const val MAX_NUMBER_OF_OCEANS = 9

fun endGameCheck(state: State): Boolean {
    return temperatureLens.get(state) == MAX_TEMPERATURE && oxygenLens.get(state) == MAX_OXYGEN && oceansLens.get(
        state
    ) == MAX_NUMBER_OF_OCEANS
}

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

val oceansLens: Getter<State, Int> = Getter(get = { s -> s.surfaceMap.numberOfOceans })

internal val surfaceLens: Lens<State, SurfaceMap> =
    Lens(get = { s -> s.surfaceMap }, set = { s, b -> s.copy(surfaceMap = b) })

internal val placedLens: (HexCoordinate) -> Lens<SurfaceMap, Tile> = { hexCoordinate ->
    Lens(
        get = { s -> s.placed.getOrDefault(hexCoordinate, Tile()) },
        set = { s, b -> s.copy(placed = s.placed.plus(hexCoordinate to b)) })
}

internal val bonusLens: (HexCoordinate) -> Lens<SurfaceMap, Bonus> = { hexCoordinate ->
    Lens(
        get = { s -> s.bonusMap.getOrDefault(hexCoordinate, EmptyBonus) },
        set = { s, b -> s.copy(bonusMap = s.bonusMap.plus(hexCoordinate to b)) })
}


val stateTileLens: (HexCoordinate) -> Lens<State, Tile> =
    { hexCoordinate: HexCoordinate -> surfaceLens.compose(placedLens(hexCoordinate)) }.memoize()

val stateBonusLens: (HexCoordinate) -> Lens<State, Bonus> =
    { hexCoordinate: HexCoordinate -> surfaceLens.compose(bonusLens(hexCoordinate)) }
