package com.c2v4.colonize.domain.map

import arrow.core.Option
import com.c2v4.colonize.domain.Player

data class Tile(val tileType: TileType = EmptyTile, val player: Option<Player> = Option.empty())
