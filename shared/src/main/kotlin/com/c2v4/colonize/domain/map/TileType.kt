package com.c2v4.colonize.domain.map

import arrow.core.Either
import arrow.core.Option
import com.c2v4.colonize.domain.Player
import com.c2v4.colonize.domain.State
import com.c2v4.colonize.domain.action.Action
import com.c2v4.colonize.domain.action.ChangeTerraformRating
import com.c2v4.colonize.domain.action.RaiseOxygen
import com.c2v4.colonize.domain.action.TERRAFORM_RATING_INCREMENT_FOR_GLOBAL_PARAMETERS

sealed class TileType {
    abstract fun getTile(player: Player): Tile
    abstract fun causedActions(player: Player): List<Action>
    abstract fun isValid(
        position: Either<MapType, HexCoordinate>,
        player: Player,
        state: State
    ): Boolean
}

const val OCEAN_TILE_MONEY_BONUS = 2

object Ocean : TileType() {
    override fun isValid(
        position: Either<MapType, HexCoordinate>,
        player: Player,
        state: State
    ) =
        position.fold({ false },
            { hexCoordinate -> stateMapGetterFactory(hexCoordinate).get(state) == MapFieldType.OCEAN_PLACE })

    override fun causedActions(player: Player) = listOf(
        ChangeTerraformRating(
            player,
            TERRAFORM_RATING_INCREMENT_FOR_GLOBAL_PARAMETERS
        )
    )

    override fun getTile(player: Player) = Tile(Ocean)
}

object Greenery : TileType() {
    override fun isValid(position: Either<MapType, HexCoordinate>, player: Player, state: State) =
        position.fold({ false },
            { hexCoordinate ->
                stateMapGetterFactory(hexCoordinate).get(state) == MapFieldType.GENERAL_PURPOSE
                    && neighbourhoodCondition(state, player, hexCoordinate)
            })


    private fun neighbourhoodCondition(
        state: State,
        player: Player,
        hexCoordinate: HexCoordinate
    ): Boolean {
        val ownedTiles =
            placedLens.get(state).filter { it.value.player.exists { owner -> owner == player } }
                .keys
        val neighboursOfOwned = ownedTiles.flatMap { it.neighbours() }
        return ownedTiles.isEmpty()
            || neighboursOfOwned.contains(hexCoordinate)
            || neighboursOfOwned.none {
            statePlacedLensFactory(it).get(state) == EMPTY_MAP_TILE
                && stateMapGetterFactory(it).get(state) == MapFieldType.GENERAL_PURPOSE
        }
    }


    override fun causedActions(player: Player) = listOf(
        RaiseOxygen(player)
    )

    override fun getTile(player: Player) = Tile(Greenery, Option.just(player))
}


object EmptyTile : TileType() {
    override fun isValid(
        position: Either<MapType, HexCoordinate>,
        player: Player,
        state: State
    ) = false

    override fun causedActions(player: Player) = emptyList<Action>()
    override fun getTile(player: Player) = Tile(EmptyTile)
}
