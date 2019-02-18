package com.c2v4.colonize.domain.map

import arrow.core.Either
import com.c2v4.colonize.domain.Player
import com.c2v4.colonize.domain.State
import com.c2v4.colonize.domain.action.Action
import com.c2v4.colonize.domain.action.ChangeTerraformRating
import com.c2v4.colonize.domain.action.TERRAFORM_RATING_INCREMENT_FOR_GLOBAL_PARAMETERS

sealed class TileType {
    abstract fun getTile(player: Player): Tile
    abstract fun causedActions(player: Player): List<Action>
    abstract fun isValid(
        position: Either<MapType, HexCoordinate>,
        state: State
    ): Boolean
}

const val OCEAN_TILE_MONEY_BONUS = 2

object Ocean : TileType() {
    override fun isValid(position: Either<MapType, HexCoordinate>, state: State)=
        position.fold({false},{ hexCoordinate -> stateMapGetter(hexCoordinate).get(state) == OceanPlace})

    override fun causedActions(player: Player) = listOf(
        ChangeTerraformRating(
            player,
            TERRAFORM_RATING_INCREMENT_FOR_GLOBAL_PARAMETERS
        )
    )

    override fun getTile(player: Player) = Tile(Ocean)
}

object EmptyTile : TileType() {
    override fun isValid(position: Either<MapType, HexCoordinate>, state: State) = false
    override fun causedActions(player: Player) = emptyList<Action>()
    override fun getTile(player: Player) = Tile(EmptyTile)
}
