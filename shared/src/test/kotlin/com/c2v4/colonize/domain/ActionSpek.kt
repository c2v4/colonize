package com.c2v4.colonize.domain

import mock
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import org.mockito.BDDMockito
import org.mockito.Mockito
import java.lang.IllegalArgumentException

@RunWith(JUnitPlatform::class)
object ActionSpek : Spek({
    val testState = State(listOf(Player("Asd"), Player("Bsd")),
            0,
            wallets = mapOf(Player("Asd") to mapOf(Resource.HEAT to 3,
                    Resource.ENERGY to 4,
                    Resource.PLANT to 2),
                    Player("Bsd") to mapOf(Resource.ENERGY to 1, Resource.HEAT to 3))
    )
    given("Pass Action") {
        val passAction = Pass(Player("Asd"))
        on("Invoke") {
            it("Changes player") {
                assertThat(passAction(testState)).isEqualTo(testState.copy(currentPlayer = 1,
                        consecutivePasses = 1))
            }
            it("Changes player to first when at the end") {
                assertThat(Pass(Player("Bsd"))(testState.copy(currentPlayer = 1,
                        consecutivePasses = 1))).isEqualTo(testState.copy(currentPlayer = 0,
                        consecutivePasses = 2))
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

    given("GiveResource") {
        val giveResource = GiveResource(mapOf(Resource.HEAT to 3, Resource.ENERGY to 4),
                Player("Asd"))
        on("Check invoke") {
            it("Gives resource to player") {
                assertThat(giveResource(testState)).isEqualTo(testState.copy(wallets = testState.wallets.plus(
                        Player("Asd") to mapOf(
                                Resource.HEAT to 6, Resource.ENERGY to 8, Resource.PLANT to 2))))
            }
        }
        on("Check applicable") {
            it("Is always true") {
                assertThat(giveResource.isApplicable(testState)).isTrue()
            }
        }
    }

    given("None Action") {
        on("Invoke") {
            it("Returns the same state") {
                assertThat(None(testState)).isEqualTo(testState)
            }
        }
        on("Applicable") {
            it("Is always true") {
                assertThat(None.isApplicable(testState)).isTrue()
            }
        }
    }

    given("SpendResource") {
        val spendResource = SpendResource(mapOf(Resource.ENERGY to 3, Resource.HEAT to 3),
                Player("Asd"))
        on("Check isApplicable") {
            it("True for positive case") {
                assertThat(spendResource.isApplicable(testState)).isTrue()
            }
            it("False for negative case") {
                assertThat(SpendResource(mapOf(Resource.ENERGY to 5,
                        Resource.HEAT to 2), Player("Asd")).isApplicable(testState)).isFalse()
            }
        }
        on("Check invoke") {
            it("Removes resources from player's wallet") {
                assertThat(spendResource(testState)).isIn(testState.copy(
                        wallets = testState.wallets.plus(Player("Asd") to mapOf(Resource.ENERGY to 1,
                                Resource.PLANT to 2))
                ),
                        testState.copy(wallets = testState.wallets.plus(Player("Asd") to mapOf(
                                Resource.ENERGY to 1,
                                Resource.PLANT to 2,
                                Resource.HEAT to 0))
                        ))
            }
            it("Throws exception when is not applicable") {
                Assertions.assertThatThrownBy {
                    SpendResource(mapOf(Resource.ENERGY to 3,
                            Resource.HEAT to 3), Player("Bsd"))(testState)
                }.isInstanceOf(
                                IllegalArgumentException::class.java)
            }
        }
    }

    given("Turn Checked Action") {
        on("Invoke") {
            it("Returns the same state but incremented turns") {
                assertThat(None.withTurnCheck()(testState.copy(actionsPlayed = 0,
                        currentPlayer = 0))).isEqualTo(testState.copy(actionsPlayed = 1,
                        currentPlayer = 0))
            }
            it("When it was second action, changes player ") {
                assertThat(None.withTurnCheck()(testState.copy(players = listOf(Player("Asd"),
                        Player("Bsd")),
                        actionsPlayed = 1,
                        currentPlayer = 0))).isEqualTo(testState.copy(players = listOf(Player("Asd"),
                        Player("Bsd")), actionsPlayed = 0, currentPlayer = 1))
            }
            it("When it was second action, of last player, changes player back to first ") {
                assertThat(None.withTurnCheck()(testState.copy(players = listOf(Player("Asd"),
                        Player("Bsd"),
                        Player("Csd")),
                        actionsPlayed = 1,
                        currentPlayer = 2))).isEqualTo(testState.copy(players = listOf(Player("Asd"),
                        Player("Bsd"),
                        Player("Csd")), actionsPlayed = 0, currentPlayer = 0))
            }
            it("Invokes contained action") {
                val mockedAction = mock<Action>()
                BDDMockito.given(mockedAction.invoke(testState)).willReturn(testState)

                TurnChecked(mockedAction)(testState)

                BDDMockito.then(mockedAction).should(Mockito.times(1)).invoke(testState)
            }
        }
        on("Applicable") {
            it("Is always true") {
                assertThat(None.withTurnCheck().isApplicable(testState)).isTrue()
            }
        }
    }

})