package com.c2v4.colonize.domain

import com.c2v4.colonize.domain.action.*
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
        val state = temperatureLens.set(State(), TEMPERATURE_PRECEEDING_FIRST_HEAT_BONUS)
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
            IncreaseProduction(player,Resource.HEAT,1)
        )
    }


    @Test
    fun temperatureWasRaisedSecondHeatBonus() {
        val state = temperatureLens.set(State(), TEMPERATURE_PRECEEDING_SECOND_HEAT_BONUS)
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
            IncreaseProduction(player,Resource.HEAT,1)
        )
    }


    @Test
    @Ignore
    fun temperatureWasRaisedOceanBonus() {
        val state = temperatureLens.set(State(), TEMPERATURE_PRECEEDING_OCEAN_BONUS)
        val event = RaiseTemperature(player)
        val (newState, events) = event(state)
        (temperatureLens.get(newState) - temperatureLens.get(state)).shouldBe(
            TEMPERATURE_INCREMENT_VALUE
        )
        events.first().shouldBe(
            ChangeTerraformRating(
                player,
                TERRAFORM_RATING_INCREMENT_FOR_GLOBAL_PARAMETERS
            )
        )
        TODO()
    }

    @Test
    fun temperatureWasNotRaisedWhenAlreadyMax() {
        val state = temperatureLens.set(State(), MAX_TEMPERATURE)
        val event = RaiseTemperature(player)
        val (newState, events) = event(state)
        temperatureLens.get(newState).shouldBe(temperatureLens.get(state))
        events.shouldHaveSize(0)
    }
}
