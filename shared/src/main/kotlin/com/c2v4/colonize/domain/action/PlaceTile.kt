package com.c2v4.colonize.domain.action

import arrow.core.Either
import com.c2v4.colonize.domain.*
import com.c2v4.colonize.domain.map.HexCoordinate
import com.c2v4.colonize.domain.map.MapType
import com.c2v4.colonize.domain.map.Ocean
import com.c2v4.colonize.domain.map.TileType

data class PlaceTile(
    val player: Player,
    val tileType: TileType,
    val position: Either<MapType, HexCoordinate>
) : Action {

    constructor(player: Player, tileType: TileType, position: HexCoordinate) : this(
        player,
        tileType,
        Either.right(position)
    )

    override fun invoke(state: State): ActionEffect =
        position.fold({ mapType -> TODO() }, { hexCoordinate ->
            val numberOfAdjecentOceans =
                hexCoordinate.neighbours().map { stateTileLens(it).get(state) }
                    .count { it.tileType == Ocean }
            ActionEffect(
                stateTileLens(hexCoordinate).set(state, tileType.getTile(player)),
                stateBonusLens(hexCoordinate).get(state).causedActions(player) +
                    tileType.causedActions(player) +
                    if (numberOfAdjecentOceans == 0) emptyList() else listOf(
                        GiveResource(
                            player,
                            mapOf(Resource.MONEY to numberOfAdjecentOceans * 2)
                        )
                    )
            )
        })
}
