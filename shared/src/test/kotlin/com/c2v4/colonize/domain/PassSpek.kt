package com.c2v4.colonize.domain

import org.assertj.core.api.Assertions.assertThat
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith

@RunWith(JUnitPlatform::class)
class PassSpek : Spek({
    val testState = State(listOf(Player("Asd"), Player("Bsd")), 0)
    given("Pass Action") {
        val passAction = Pass(Player("Asd"))
        on("Invoke") {
            it("Changes player") {
                assertThat(passAction(testState)).isEqualTo(testState.copy(currentPlayer = 1,consecutivePasses=1))
            }
            it("Changes player to first when at the end") {
                assertThat(Pass(Player("Bsd"))(testState.copy(currentPlayer = 1,consecutivePasses=1))).isEqualTo(testState.copy(currentPlayer = 0,consecutivePasses=2))
            }
        }
        on("Applicable") {
            it("Is true when it's current player's turn") {
                assertThat(passAction.isApplicable(testState)).isTrue()
            }
            it("Is false when it's not current player's turn") {
                assertThat(Pass(Player("Bsd")).isApplicable(testState)).isFalse()
            }
        }
    }
})