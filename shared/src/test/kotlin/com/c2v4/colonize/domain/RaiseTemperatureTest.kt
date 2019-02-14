package com.c2v4.colonize.domain

import com.c2v4.colonize.domain.action.ChangeTerraformRating
import com.c2v4.colonize.domain.action.RaiseTemperature
import com.c2v4.colonize.domain.action.TEMPERATURE_INCREMENT_VALUE
import com.c2v4.colonize.domain.action.TERRAFORM_RATING_INCREMENT_FOR_GLOBAL_PARAMETERS
import io.kotlintest.matchers.collections.shouldHaveSize
import io.kotlintest.shouldBe
import io.kotlintest.specs.AnnotationSpec

internal class RaiseTemperatureTest : AnnotationSpec() {

    @Test
    fun temperatureWasRaised() {
        val player = Player()
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
    fun temperatureWasNotRaisedWhenAlreadyMax() {
        val player = Player()
        val state = temperatureLens.set(State(), MAX_TEMPERATURE)
        val event = RaiseTemperature(player)
        val (newState, events) = event(state)
        temperatureLens.get(newState).shouldBe(temperatureLens.get(state))
        events.shouldHaveSize(0)
    }
}
