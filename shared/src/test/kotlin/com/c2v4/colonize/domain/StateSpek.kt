package com.c2v4.colonize.domain

import org.assertj.core.api.Assertions.assertThat
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith

@RunWith(JUnitPlatform::class)
object StateSpek : Spek({
    given("A State") {
        val testState = State(players = listOf(Player("Asd"), Player("Bsd")), currentPlayer = 1)

        on("After action played") {
            it("Stays with the same player") {
                assertThat(testState.apply(None)).isEqualToComparingFieldByField(testState.copy(
                        currentPlayer = 1, actionsPlayed = 1))
            }
        }

        on("After 2 actions played") {
            it("Changes player") {
                assertThat(testState.copy(actionsPlayed = 1).apply(None)).isEqualToComparingFieldByField(testState.copy(
                        currentPlayer = 0))
            }
        }
    }
})