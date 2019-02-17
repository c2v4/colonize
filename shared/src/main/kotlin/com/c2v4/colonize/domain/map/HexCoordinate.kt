package com.c2v4.colonize.domain.map

data class HexCoordinate constructor(val x: Int, val z: Int) {
    val y
        get() = z - x

    companion object {
        val NEIGHBOUR_COORDINATES =
            setOf(of(0, -1), of(1, -1), of(-1, 0), of(1, -0), of(-1, 1), of(0, 1))

        fun of(q: Int, r: Int): HexCoordinate =
            HexCoordinate(q, r)

        fun of(x: Int, y: Int, z: Int): HexCoordinate =
            if (x + y + z == 0) HexCoordinate(x, y) else throw IllegalArgumentException()
    }

    fun neighbours(): Set<HexCoordinate> = NEIGHBOUR_COORDINATES.map { it + this }.toSet()

    operator fun plus(other: HexCoordinate) = of(x + other.x, z + other.z)
}
