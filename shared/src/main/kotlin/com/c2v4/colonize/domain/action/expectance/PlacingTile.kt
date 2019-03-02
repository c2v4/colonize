package com.c2v4.colonize.domain.action.expectance

import arrow.core.Either
import com.c2v4.colonize.domain.Player
import com.c2v4.colonize.domain.State
import com.c2v4.colonize.domain.action.Action
import com.c2v4.colonize.domain.action.PlaceTile
import com.c2v4.colonize.domain.map.*

data class PlacingTile(val player: Player, val allowedTileTypes: Set<TileType>) : ExpectedAction {
    override fun isValid(action: Action, state: State): Boolean =
        action is PlaceTile &&
            action.player == player &&
            allowedTileTypes.contains(action.tileType) &&
            placingSpotIsEmpty(action.position, state) &&
            action.tileType.isValid(
                action.position,
                player,
                state
            )

    private fun placingSpotIsEmpty(
        position: Either<MapType, HexCoordinate>,
        state: State
    ): Boolean = position.fold(
        { mapType -> stateAdditionalFieldsLens(mapType).get(state) == EMPTY_MAP_TILE },
        { hexCoordinate -> statePlacedLensFactory(hexCoordinate).get(state) == EMPTY_MAP_TILE })
}
