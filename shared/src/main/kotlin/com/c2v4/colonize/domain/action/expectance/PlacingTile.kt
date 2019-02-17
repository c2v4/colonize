package com.c2v4.colonize.domain.action.expectance

import com.c2v4.colonize.domain.Player
import com.c2v4.colonize.domain.action.Action
import com.c2v4.colonize.domain.map.TileType

data class PlacingTile(val player: Player, val tileType: TileType) : ExpectedAction {
    override fun isValid(action: Action): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
