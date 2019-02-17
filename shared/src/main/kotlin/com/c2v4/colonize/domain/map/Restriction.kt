package com.c2v4.colonize.domain.map

sealed class Restriction {
    abstract fun isValid(tileType: TileType): Boolean
}

object OceanPlace : Restriction() {
    override fun isValid(tileType: TileType): Boolean = tileType == Ocean
}

object GeneralPurpose : Restriction() {
    override fun isValid(tileType: TileType): Boolean = TODO()
}

data class Special(val name: String) : Restriction() {
    override fun isValid(tileType: TileType): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
