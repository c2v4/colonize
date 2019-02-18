package com.c2v4.colonize.domain.action.expectance

import com.c2v4.colonize.domain.Player
import com.c2v4.colonize.domain.State
import com.c2v4.colonize.domain.action.PlaceTile
import com.c2v4.colonize.domain.action.RaiseOxygen
import com.c2v4.colonize.domain.map.HexCoordinate
import com.c2v4.colonize.domain.map.HexCoordinate.Companion.of
import com.c2v4.colonize.domain.map.Ocean
import com.c2v4.colonize.domain.map.OceanPlace
import com.c2v4.colonize.domain.map.SurfaceMap
import io.kotlintest.matchers.boolean.shouldBeFalse
import io.kotlintest.matchers.boolean.shouldBeTrue
import io.kotlintest.specs.AnnotationSpec


internal class PlacingTileTest : AnnotationSpec() {

    @Test
    fun actionShoulbePlaceTile() {
        val player = Player()
        val placingTile = PlacingTile(player, setOf())
        placingTile(RaiseOxygen(player), State()).shouldBeFalse()
    }


    @Test
    fun playerShouldMatch() {
        val player = Player()
        val player2 = Player()
        val placingTile = PlacingTile(player, setOf(Ocean))
        val position = HexCoordinate.of(1, 2)

        placingTile(
            PlaceTile(player, Ocean, position),
            State(surfaceMap = SurfaceMap(map = mapOf(position to OceanPlace)))
        ).shouldBeTrue()

        placingTile(
            PlaceTile(player2, Ocean, position),
            State(surfaceMap = SurfaceMap(map = mapOf(position to OceanPlace)))
        ).shouldBeFalse()
    }


    @Test
    fun tileShouldBePlacedInACorrectSpot() {
        val player = Player()
        val placingTile = PlacingTile(player, setOf(Ocean))
        val position = HexCoordinate.of(1, 2)

        placingTile(
            PlaceTile(player, Ocean, position),
            State(surfaceMap = SurfaceMap(map = mapOf(position to OceanPlace)))
        ).shouldBeTrue()

        placingTile(
            PlaceTile(player, Ocean, position),
            State(surfaceMap = SurfaceMap(map = mapOf(position.plus(of(1,0)) to OceanPlace)))
        ).shouldBeFalse()
    }
}
