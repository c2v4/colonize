package com.c2v4.colonize.domain

import com.c2v4.colonize.domain.action.*
import io.kotlintest.matchers.collections.shouldContainAll
import io.kotlintest.matchers.collections.shouldContainExactly
import io.kotlintest.matchers.collections.shouldHaveSize
import io.kotlintest.shouldBe
import io.kotlintest.specs.AnnotationSpec

internal class RaiseOxygenTest : AnnotationSpec() {

    @Test
    fun oxygenWasRaised() {
        val player = Player()
        val state = State()
        val event = RaiseOxygen(player)
        val (newState, events) = event(state)
        (oxygenLens.get(newState) - oxygenLens.get(state)).shouldBe(OXYGEN_INCREMENT_VALUE)
        events.shouldHaveSize(1)
        events.first().shouldBe(
            ChangeTerraformRating(
                player,
                TERRAFORM_RATING_INCREMENT_FOR_GLOBAL_PARAMETERS
            )
        )
    }

    @Test
    fun oxygenWasRaisedWithAditionalEffect() {
        val player = Player()
        val state = oxygenLens.set(State(), OXYGEN_RAISE_PRECEEDING_BONUS)
        val event = RaiseOxygen(player)
        val (newState, events) = event(state)
        (oxygenLens.get(newState) - oxygenLens.get(state)).shouldBe(OXYGEN_INCREMENT_VALUE)
        events.shouldHaveSize(2)
        events.shouldContainExactly(
            ChangeTerraformRating(
                player,
                TERRAFORM_RATING_INCREMENT_FOR_GLOBAL_PARAMETERS
            ),
            RaiseTemperature(player)
        )
    }


    @Test
    fun oxygenWasNotRaisedWhenAlreadyMax() {
        val player = Player()
        val state = oxygenLens.set(State(), MAX_OXYGEN)
        val event = RaiseOxygen(player)
        val (newState, events) = event(state)
        oxygenLens.get(newState).shouldBe(oxygenLens.get(state))
        events.shouldHaveSize(0)
    }
}
