package com.c2v4.colonize.domain

data class State(val players: List<Player> = emptyList(),
                 val currentPlayer: Int = 0,
                 val wallets: Map<Player, Map<Resource, Int>> = emptyMap())

data class Player(val name:String)
