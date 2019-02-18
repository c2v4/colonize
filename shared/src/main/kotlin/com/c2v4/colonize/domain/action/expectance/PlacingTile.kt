package com.c2v4.colonize.domain.action.expectance

import com.c2v4.colonize.domain.Player
import com.c2v4.colonize.domain.State
import com.c2v4.colonize.domain.action.Action
import com.c2v4.colonize.domain.action.PlaceTile
import com.c2v4.colonize.domain.map.TileType

data class PlacingTile(val player: Player, val allowedTileTypes: Set<TileType>) : ExpectedAction {
    override fun isValid(action: Action, state: State): Boolean =
        action is PlaceTile &&
            action.player == player &&
            allowedTileTypes.contains(action.tileType) &&
            action.tileType.isValid(
                action.position,
                state
            )
}
