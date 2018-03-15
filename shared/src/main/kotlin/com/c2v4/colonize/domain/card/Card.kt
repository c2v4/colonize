package com.c2v4.colonize.domain.card

import com.c2v4.colonize.domain.action.ActionScheme
import com.c2v4.colonize.domain.action.EmptyScheme
import com.c2v4.colonize.domain.card.requirement.Requirement

data class Card(val cost: Int = 0,
                val requirements: Set<Requirement<*>> = emptySet(),
                val symbols: Set<Symbol> = emptySet(),
                val action: ActionScheme = EmptyScheme)
