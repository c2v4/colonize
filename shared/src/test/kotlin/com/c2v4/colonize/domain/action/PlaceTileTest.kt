package com.c2v4.colonize.domain.action

import com.c2v4.colonize.domain.Player
import com.c2v4.colonize.domain.Resource
import com.c2v4.colonize.domain.State
import com.c2v4.colonize.domain.map.*
import io.kotlintest.matchers.collections.shouldContainAll
import io.kotlintest.matchers.collections.shouldContainExactly
import io.kotlintest.matchers.maps.shouldContain
import io.kotlintest.matchers.maps.shouldContainExactly
import io.kotlintest.shouldBe
import io.kotlintest.specs.AnnotationSpec

internal class PlaceTileTest : AnnotationSpec() {

    @Test
    fun placingAnOcean() {
        val player = Player()
        val state = State()
        val position = HexCoordinate.of(1, 2)
        val event = PlaceTile(player, Ocean, position)
        val (newState, causedActions) = event(state)
        newState.surfaceMap.numberOfOceans.shouldBe(1)
        newState.surfaceMap.placed.shouldContainExactly(mapOf(position to Tile(Ocean)))
        causedActions.shouldContainExactly(
            ChangeTerraformRating(
                player,
                TERRAFORM_RATING_INCREMENT_FOR_GLOBAL_PARAMETERS
            )
        )
    }

    @Test
    fun placingATileGivesABonus() {
        val player = Player()
        val position = HexCoordinate.of(1, 2)
        val resourceMap = mapOf(Resource.IRON to 2)
        val state = State(
            surfaceMap = SurfaceMap(
                bonusMap = mapOf(
                    position to ResourceBonus(
                        resourceMap
                    )
                )
            )
        )
        val event = PlaceTile(player, Ocean, position)
        val (newState, causedActions) = event(state)
        newState.surfaceMap.numberOfOceans.shouldBe(1)
        newState.surfaceMap.placed.shouldContainExactly(mapOf(position to Tile(Ocean)))
        causedActions.shouldContainAll(
            ChangeTerraformRating(
                player,
                TERRAFORM_RATING_INCREMENT_FOR_GLOBAL_PARAMETERS
            ),
            GiveResource(player, resourceMap)
        )
    }

    @Test
    fun placingCloseToAnOceanGivesMoney() {
        val player = Player()
        val position = HexCoordinate.of(1, 2)
        val state = State(
            surfaceMap = SurfaceMap(
                placed = mapOf(position.neighbours().first() to Tile(Ocean))
            )
        )
        val event = PlaceTile(player, Ocean, position)
        val (newState, causedActions) = event(state)
        newState.surfaceMap.numberOfOceans.shouldBe(2)
        newState.surfaceMap.placed.shouldContain(position, Tile(Ocean))
        causedActions.shouldContainAll(
            ChangeTerraformRating(
                player,
                TERRAFORM_RATING_INCREMENT_FOR_GLOBAL_PARAMETERS
            ),
            GiveResource(player, mapOf(Resource.MONEY to OCEAN_TILE_MONEY_BONUS))
        )
    }

    @Test
    fun placingCloseToMultipleOceansGivesMoreMoney() {
        val player = Player()
        val position = HexCoordinate.of(1, 2)
        val state = State(
            surfaceMap = SurfaceMap(
                placed = position.neighbours().take(2).associateWith { Tile(Ocean) }
            )
        )
        val event = PlaceTile(player, Ocean, position)
        val (newState, causedActions) = event(state)
        newState.surfaceMap.numberOfOceans.shouldBe(3)
        newState.surfaceMap.placed.shouldContain(position, Tile(Ocean))
        causedActions.shouldContainAll(
            ChangeTerraformRating(
                player,
                TERRAFORM_RATING_INCREMENT_FOR_GLOBAL_PARAMETERS
            ),
            GiveResource(player, mapOf(Resource.MONEY to (OCEAN_TILE_MONEY_BONUS * 2)))
        )
    }
}
