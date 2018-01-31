package com.c2v4.colonize.domain

import mock
import org.assertj.core.api.Assertions.assertThat
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.jetbrains.spek.api.dsl.xon
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import org.mockito.BDDMockito
import org.mockito.BDDMockito.then
import org.mockito.Mockito
import org.mockito.Mockito.times
import org.mockito.Mockito.verify

@RunWith(JUnitPlatform::class)
class TurnCheckedSpek : Spek({
    given("Turn Checked Action") {
        on("Invoke") {
            it("Returns the same state but incremented turns") {
                assertThat(None.withTurnCheck()(testState.copy(actionsPlayed = 0, currentPlayer = 0))).isEqualTo(testState.copy(actionsPlayed = 1, currentPlayer = 0))
            }
            it("When it was second action, changes player ") {
                assertThat(None.withTurnCheck()(testState.copy(players = listOf(Player("Asd"), Player("Bsd")), actionsPlayed = 1, currentPlayer = 0))).isEqualTo(testState.copy(players = listOf(Player("Asd"), Player("Bsd")), actionsPlayed = 0, currentPlayer = 1))
            }
            it("When it was second action, of last player, changes player back to first ") {
                assertThat(None.withTurnCheck()(testState.copy(players = listOf(Player("Asd"), Player("Bsd"), Player("Csd")), actionsPlayed = 1, currentPlayer = 2))).isEqualTo(testState.copy(players = listOf(Player("Asd"), Player("Bsd"), Player("Csd")), actionsPlayed = 0, currentPlayer = 0))
            }
            it("Invokes contained action") {
                val mockedAction = mock<Action>()
                BDDMockito.given(mockedAction.invoke(testState)).willReturn(testState)
                
                TurnChecked(mockedAction)(testState)
                
                then(mockedAction).should(times(1)).invoke(testState)
            }
        }
        xon("Applicable") {
            it("Is always true") {
                assertThat(None.isApplicable(testState)).isTrue()
            }
        }
    }
})