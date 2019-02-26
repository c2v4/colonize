package com.c2v4.colonize.domain.map

import arrow.optics.Getter
import arrow.optics.Lens
import arrow.syntax.function.memoize
import com.c2v4.colonize.domain.State

data class SurfaceMap(
    val map: Map<HexCoordinate, MapFieldType> = mapOf(),
    val additionalFields: Map<MapType,Tile> = mapOf(),
    val bonusMap: Map<HexCoordinate, Bonus> = mapOf(),
    val placed: Map<HexCoordinate, Tile> = mapOf()
) {
    val numberOfOceans: Int
        get() = placed.entries.count { it.value.tileType == Ocean }
}

const val MAX_NUMBER_OF_OCEANS = 9

val oceansLens: Getter<State, Int> =
    Getter(get = { s -> s.surfaceMap.numberOfOceans })

internal val surfaceLens: Lens<State, SurfaceMap> =
    Lens(get = { s -> s.surfaceMap }, set = { s, b -> s.copy(surfaceMap = b) })

internal val placedLens: (HexCoordinate) -> Lens<SurfaceMap, Tile> = { hexCoordinate ->
    Lens(
        get = { s -> s.placed.getOrDefault(hexCoordinate, EMPTY_TILE) },
        set = { s, b -> s.copy(placed = s.placed.plus(hexCoordinate to b)) })
}

internal val bonusLens: (HexCoordinate) -> Lens<SurfaceMap, Bonus> = { hexCoordinate ->
    Lens(
        get = { s ->
            s.bonusMap.getOrDefault(
                hexCoordinate,
                EmptyBonus
            )
        },
        set = { s, b -> s.copy(bonusMap = s.bonusMap.plus(hexCoordinate to b)) })
}

val statePlacedLens: (HexCoordinate) -> Lens<State, Tile> =
    { hexCoordinate: HexCoordinate ->
        surfaceLens.compose(
            placedLens(hexCoordinate)
        )
    }.memoize()

val stateBonusLens: (HexCoordinate) -> Lens<State, Bonus> =
    { hexCoordinate: HexCoordinate ->
        surfaceLens.compose(
            bonusLens(hexCoordinate)
        )
    }

val stateMapGetter: (HexCoordinate) -> Getter<State, MapFieldType> =
    { hexCoordinate ->
        surfaceLens.compose(Getter { s ->
            s.map.getOrDefault(
                hexCoordinate,
                MapFieldType.NOT_VALID
            )
        })
    }
