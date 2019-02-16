package com.c2v4.colonize.domain.action.expectance

import com.c2v4.colonize.domain.Player
import com.c2v4.colonize.domain.action.Action

data class PlaceOcean(val player: Player) : ExpectedAction {
    override fun isValid(action: Action): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
