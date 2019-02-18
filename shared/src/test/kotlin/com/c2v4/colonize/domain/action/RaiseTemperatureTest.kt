package com.c2v4.colonize.domain.action

import com.c2v4.colonize.domain.*
import com.c2v4.colonize.domain.action.expectance.PlacingTile
import com.c2v4.colonize.domain.map.Ocean
import io.kotlintest.matchers.collections.shouldContainExactly
import io.kotlintest.matchers.collections.shouldHaveSize
import io.kotlintest.shouldBe
import io.kotlintest.specs.AnnotationSpec

internal class RaiseTemperatureTest : AnnotationSpec() {

    private val player = Player()

    @Test
    fun temperatureWasRaised() {
        val state = State()
        val event = RaiseTemperature(player)
        val (newState, events) = event(state)
        (temperatureLens.get(newState) - temperatureLens.get(state)).shouldBe(
            TEMPERATURE_INCREMENT_VALUE
        )
        events.shouldHaveSize(1)
        events.first().shouldBe(
            ChangeTerraformRating(
                player,
                TERRAFORM_RATING_INCREMENT_FOR_GLOBAL_PARAMETERS
            )
        )
    }

    @Test
    fun temperatureWasRaisedFirstHeatBonus() {
        val state = temperatureLens.set(State(), TEMPERATURE_PRECEDING_FIRST_HEAT_BONUS)
        val event = RaiseTemperature(player)
        val (newState, events) = event(state)
        (temperatureLens.get(newState) - temperatureLens.get(state)).shouldBe(
            TEMPERATURE_INCREMENT_VALUE
        )
        events.shouldContainExactly(
            ChangeTerraformRating(
                player,
                TERRAFORM_RATING_INCREMENT_FOR_GLOBAL_PARAMETERS
            ),
            IncreaseProduction(player, Resource.HEAT, 1)
        )
    }


    @Test
    fun temperatureWasRaisedSecondHeatBonus() {
        val state = temperatureLens.set(State(), TEMPERATURE_PRECEDING_SECOND_HEAT_BONUS)
        val event = RaiseTemperature(player)
        val (newState, events) = event(state)
        (temperatureLens.get(newState) - temperatureLens.get(state)).shouldBe(
            TEMPERATURE_INCREMENT_VALUE
        )
        events.shouldContainExactly(
            ChangeTerraformRating(
                player,
                TERRAFORM_RATING_INCREMENT_FOR_GLOBAL_PARAMETERS
            ),
            IncreaseProduction(player, Resource.HEAT, 1)
        )
    }


    @Test
    fun temperatureWasRaisedOceanBonus() {
        val state = temperatureLens.set(State(), TEMPERATURE_PRECEDING_OCEAN_BONUS)
        val event = RaiseTemperature(player)
        val (newState, events, expectedActions) = event(state)
        (temperatureLens.get(newState) - temperatureLens.get(state)).shouldBe(
            TEMPERATURE_INCREMENT_VALUE
        )
        events.shouldContainExactly(
            ChangeTerraformRating(
                player,
                TERRAFORM_RATING_INCREMENT_FOR_GLOBAL_PARAMETERS
            )
        )
        expectedActions.shouldContainExactly(PlacingTile(player, setOf(Ocean)))

    }

    @Test
    fun temperatureWasNotRaisedWhenAlreadyMax() {
        val state = temperatureLens.set(
            State(),
            MAX_TEMPERATURE
        )
        val event = RaiseTemperature(player)
        val (newState, events) = event(state)
        temperatureLens.get(newState).shouldBe(temperatureLens.get(state))
        events.shouldHaveSize(0)
    }
}
