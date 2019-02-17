package com.c2v4.colonize.domain.map

data class SurfaceMap(
    val radius: Int = 0,
    val restrictionMap: Map<HexCoordinate, Restriction> = mapOf(),
    val bonusMap: Map<HexCoordinate, Bonus> = mapOf(),
    val placed: Map<HexCoordinate, Tile> = mapOf()
) {
    val numberOfOceans: Int
        get() = placed.entries.count { it.value.tileType == Ocean }
}
