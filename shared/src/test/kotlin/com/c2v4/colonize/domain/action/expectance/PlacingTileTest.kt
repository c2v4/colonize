package com.c2v4.colonize.domain.action.expectance

import arrow.core.Option
import arrow.core.toOption
import com.c2v4.colonize.domain.Player
import com.c2v4.colonize.domain.State
import com.c2v4.colonize.domain.action.PlaceTile
import com.c2v4.colonize.domain.action.RaiseOxygen
import com.c2v4.colonize.domain.map.*
import com.c2v4.colonize.domain.map.HexCoordinate.Companion.of
import com.c2v4.colonize.domain.map.MapTileType.GENERAL_PURPOSE
import com.c2v4.colonize.domain.map.MapTileType.OCEAN_PLACE
import io.kotlintest.matchers.boolean.shouldBeFalse
import io.kotlintest.matchers.boolean.shouldBeTrue
import io.kotlintest.matchers.collections.shouldContainExactly
import io.kotlintest.specs.FunSpec


internal class PlacingTileTest : FunSpec({

    context("Common properties") {

        test("actionShouldBePlaceTile") {
            val player = Player()
            val placingTile = PlacingTile(player, setOf())
            placingTile(RaiseOxygen(player), State()).shouldBeFalse()
        }

        test("playerShouldMatch") {
            val player = Player()
            val player2 = Player()
            val placingTile = PlacingTile(player, setOf(Ocean))
            val position = HexCoordinate.of(1, 2)

            placingTile(
                PlaceTile(player, Ocean, position),
                State(surfaceMap = SurfaceMap(map = mapOf(position to OCEAN_PLACE)))
            ).shouldBeTrue()

            placingTile(
                PlaceTile(player2, Ocean, position),
                State(surfaceMap = SurfaceMap(map = mapOf(position to OCEAN_PLACE)))
            ).shouldBeFalse()
        }

        test("shouldNotBePlacedOnOccupied") {
            val player = Player()
            val placingTile = PlacingTile(player, setOf(Ocean))
            val position = HexCoordinate.of(1, 2)

            placingTile(
                PlaceTile(player, Ocean, position),
                State(surfaceMap = SurfaceMap(map = mapOf(position to OCEAN_PLACE)))
            ).shouldBeTrue()

            placingTile(
                PlaceTile(player, Ocean, position),
                State(
                    surfaceMap = SurfaceMap(
                        map = mapOf(position to OCEAN_PLACE),
                        placed = mapOf(position to Tile(Ocean))
                    )
                )
            ).shouldBeFalse()
        }

    }

    context("Ocean") {

        test("oceanShouldBePlacedInACorrectSpot") {
            val player = Player()
            val placingTile = PlacingTile(player, setOf(Ocean))
            val position = HexCoordinate.of(1, 2)

            placingTile(
                PlaceTile(player, Ocean, position),
                State(surfaceMap = SurfaceMap(map = mapOf(position to OCEAN_PLACE)))
            ).shouldBeTrue()
            placingTile(
                PlaceTile(player, Ocean, position),
                State(
                    surfaceMap = SurfaceMap(
                        map = mapOf(
                            position.plus(of(1, 0)) to OCEAN_PLACE,
                            position to GENERAL_PURPOSE
                        )
                    )
                )
            ).shouldBeFalse()
        }


    }

    context("Greenery") {

        test("greeneryShouldBePlacedInACorrectSpot") {
            val player = Player()
            val placingTile = PlacingTile(player, setOf(Greenery))
            val position = HexCoordinate.of(1, 2)

            placingTile(
                PlaceTile(player, Greenery, position),
                State(surfaceMap = SurfaceMap(map = mapOf(position to GENERAL_PURPOSE)))
            ).shouldBeTrue()

            placingTile(
                PlaceTile(player, Greenery, position),
                State(
                    surfaceMap = SurfaceMap(
                        map = mapOf(
                            position.plus(
                                of(
                                    1,
                                    0
                                )
                            ) to GENERAL_PURPOSE
                        )
                    )
                )
            ).shouldBeFalse()
        }

        test("greeneryShouldBePlacedNearOwnSpot") {
            val player = Player()
            val placingTile = PlacingTile(player, setOf(Greenery))
            val state = State(
                surfaceMap = SurfaceMap(
                    map =
                    (of(0, 0).neighbours() + of(0, 0)).map { it to GENERAL_PURPOSE }.toMap(),
                    placed = mapOf(of(1, -1) to Tile(Greenery, Option.just(player)))
                )
            )
            placingTile(
                PlaceTile(player, Greenery, of(1, 0)),
                state
            ).shouldBeTrue()

            placingTile(
                PlaceTile(player, Greenery, of(-1, 0)),
                state
            ).shouldBeFalse()
        }

        test("greeneryCanBePlacedAnywhereWhenThereAreNoFreeSpotNearOwn") {
            val player = Player()
            val player2 = Player()
            val placingTile = PlacingTile(player, setOf(Greenery))
            val state = State(
                surfaceMap = SurfaceMap(
                    map =
                    of(0, 0).neighbours().map { it to GENERAL_PURPOSE }.toMap() + (of(
                        0,
                        0
                    ) to OCEAN_PLACE),
                    placed = (mapOf(of(1, -1) to Tile(Greenery, Option.just(player))) + (of(
                        1,
                        -1
                    ).neighbours() - of(0, 0)).map { it to Tile(Greenery, player2.toOption()) })
                )
            )
            placingTile(
                PlaceTile(player, Greenery, of(-1, 0)),
                state
            ).shouldBeTrue()

            placingTile(
                PlaceTile(player, Greenery, of(0, +1)),
                state
            ).shouldBeTrue()

            placingTile(
                PlaceTile(player, Greenery, of(-1, +1)),
                state
            ).shouldBeTrue()
        }


        test("greeneryCanBePlacedAnywhereWhenThereAreNoOwnSpots") {
            val player = Player()
            val placingTile = PlacingTile(player, setOf(Greenery))
            val state = State(
                surfaceMap = SurfaceMap(
                    map =
                    (of(0, 0).neighbours() + of(0, 0)).map { it to GENERAL_PURPOSE }.toMap()
                )
            )
            placingTile(
                PlaceTile(player, Greenery, of(1, 0)),
                state
            ).shouldBeTrue()

            placingTile(
                PlaceTile(player, Greenery, of(-1, 0)),
                state
            ).shouldBeTrue()
        }
    }

    context("City") {
        test("May not be placed next to another city") {
            val player = Player()
            val player2 = Player()
            val placingTile = PlacingTile(player, setOf(City))
            val firstCity = of(1, -1)
            val secondCity = of(-1, 0)
            val state = State(
                surfaceMap = SurfaceMap(
                    map = (of(0, 0).neighbours() + of(0, 0)).map { it to GENERAL_PURPOSE }.toMap(),
                    placed = mapOf(
                        firstCity to Tile(City, player.toOption()),
                        secondCity to Tile(City, player2.toOption()),
                        of(0, 0) to Tile(Greenery, player.toOption())
                    )
                )
            )

            setOf(firstCity, secondCity)
                .flatMap { it.neighbours() }
                .toSet()
                .map { coordinate -> placingTile(PlaceTile(player, City, coordinate), state) }
                .toSet()
                .shouldContainExactly(false)

            placingTile(PlaceTile(player,City,of(0,1)),state).shouldBeTrue()

        }
    }
})
