package com.c2v4.colonize.domain

data class State(val players:List<Player>,val currentPlayer:Int)

data class Player(val name:String)
