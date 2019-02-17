package com.c2v4.colonize.domain.action

import com.c2v4.colonize.domain.Player
import com.c2v4.colonize.domain.Resource
import com.c2v4.colonize.domain.State

data class GiveResource(val player: Player,val resourceMap: Map<Resource, Int>) : Action {
    override fun invoke(state: State): ActionEffect {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
