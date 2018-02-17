package com.c2v4.colonize.domain

import com.c2v4.colonize.domain.requirement.Requirement

data class Card(val cost: Int = 0,
                val requirements: Set<Requirement<*>> = emptySet(),
                val symbols: Set<Symbol> = emptySet())
