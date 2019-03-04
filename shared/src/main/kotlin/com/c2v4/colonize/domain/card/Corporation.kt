package com.c2v4.colonize.domain.card

import com.c2v4.colonize.domain.Resource
import com.c2v4.colonize.domain.Tag
import com.c2v4.colonize.domain.action.Action
import com.c2v4.colonize.domain.action.expectance.ExpectedAction

data class Corporation(
    val tags: List<Tag> = emptyList(),
    val startingResources: Iterable<Pair<Resource, Int>> = emptyList(),
    val followUpActions: Iterable<ExpectedAction> = emptyList(),
    val effects:Iterable<Effect> = emptyList(),
    val playableActions:Iterable<PlayableAction> = emptyList(),
    val immediateActions:Iterable<Action> = emptyList()
)
