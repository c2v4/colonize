package com.c2v4.colonize.domain.action

import arrow.core.Either
import com.c2v4.colonize.domain.Player
import com.c2v4.colonize.domain.Resource
import com.c2v4.colonize.domain.State
import com.c2v4.colonize.domain.map.*

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
                hexCoordinate.neighbours().map { statePlacedLensFactory(it).get(state) }
                    .count { it.tileType == Ocean }
            ActionEffect(
                statePlacedLensFactory(hexCoordinate).set(state, tileType.getTile(player)),
                stateBonusLensFactory(hexCoordinate).get(state).causedActions(player) +
                    tileType.causedActions(player) +
                    if (numberOfAdjecentOceans == 0) emptyList() else listOf(
                        GiveResource(
                            player,
                            mapOf(Resource.MEGA_CREDITS to numberOfAdjecentOceans * 2)
                        )
                    )
            )
        })
}
