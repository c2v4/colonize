package com.c2v4.colonize.domain.card

import com.c2v4.colonize.domain.Tag
import com.c2v4.colonize.domain.action.Action
import com.c2v4.colonize.domain.action.expectance.ExpectedAction

data class Card(
    val tags: List<Tag> = emptyList(),
    val followUpActions: Iterable<ExpectedAction> = emptyList(),
    val effects: Iterable<Effect> = emptyList(),
    val actions: Iterable<PlayableAction> = emptyList(),
    val cost: Int = 0,
    val requirement: Requirement = NONE,
    val immediateActions: Iterable<Action>,
    val vp: Int = 0
)
