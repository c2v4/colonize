package com.c2v4.colonize.domain.map

sealed class MapFieldType {
    abstract fun isValid(tileType: TileType): Boolean
}

object NotValid:MapFieldType() {
    override fun isValid(tileType: TileType): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

object OceanPlace : MapFieldType() {
    override fun isValid(tileType: TileType): Boolean = tileType == Ocean
}

object GeneralPurpose : MapFieldType() {
    override fun isValid(tileType: TileType): Boolean = TODO()
}

data class Special(val name: String) : MapFieldType() {
    override fun isValid(tileType: TileType): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
