package com.c2v4.colonize.domain.map

class HexagonalMap

class HexCoordinate private constructor(val x: Int, val z: Int) {
    val y
        get() = z - x

    companion object {
        val NEIGHBOUR_COORDINATES =
            setOf(of(0, -1), of(1, -1), of(-1, 0), of(1, -0), of(-1, 1), of(0, 1))

        fun of(q: Int, r: Int): HexCoordinate = HexCoordinate(q, r)
        fun of(x: Int, y: Int, z: Int): HexCoordinate =
            if (x + y + z == 0) HexCoordinate(x, y) else throw IllegalArgumentException()
    }

    fun neighbours(): Set<HexCoordinate> = NEIGHBOUR_COORDINATES.map { it + this }.toSet()

    operator fun plus(other: HexCoordinate) = of(x + other.x, z + other.z)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as HexCoordinate

        if (x != other.x) return false
        if (y != other.y) return false

        return true
    }

    override fun hashCode(): Int {
        var result = x
        result = 31 * result + y
        return result
    }

    override fun toString(): String {
        return "HexCoordinate(x=$x, y=$y,z = $z)"
    }
}
