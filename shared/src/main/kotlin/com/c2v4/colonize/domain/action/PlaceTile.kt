package com.c2v4.colonize.domain.action

import arrow.core.Either
import arrow.data.Ior
import com.c2v4.colonize.domain.Player
import com.c2v4.colonize.domain.State
import com.c2v4.colonize.domain.map.HexCoordinate
import com.c2v4.colonize.domain.map.MapType
import com.c2v4.colonize.domain.map.TileType

data class PlaceTile(
    val player: Player,
    val tileType: TileType,
    val position: Either<MapType,HexCoordinate>
    ) : Action {
    override fun invoke(state: State): ActionEffect {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
