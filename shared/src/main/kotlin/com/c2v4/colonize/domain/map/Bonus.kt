package com.c2v4.colonize.domain.map

import com.c2v4.colonize.domain.Player
import com.c2v4.colonize.domain.Resource
import com.c2v4.colonize.domain.action.Action
import com.c2v4.colonize.domain.action.GiveResource

sealed class Bonus {
    abstract fun causedActions(player: Player): List<Action>
}

object EmptyBonus : Bonus() {
    override fun causedActions(player: Player) = emptyList<Action>()
}

data class ResourceBonus(val resourceMap: Map<Resource, Int>) : Bonus() {
    override fun causedActions(player: Player): List<Action> = listOf(GiveResource(player,resourceMap))
}

object CardBonus : Bonus() {
    override fun causedActions(player: Player): List<Action> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
