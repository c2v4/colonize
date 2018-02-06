package com.c2v4.colonize.domain

data class State(val players: List<Player> = emptyList(),
                 val currentPlayer: Int = 0,
                 val wallets: Map<Player, Map<Resource, Int>> = emptyMap(),
                 val availableActions: Map<Player,Action> = emptyMap(),
                 val actionsPlayed:Int = 0,
                 val consecutivePasses:Int = 0,
                 val temperature: Int)
