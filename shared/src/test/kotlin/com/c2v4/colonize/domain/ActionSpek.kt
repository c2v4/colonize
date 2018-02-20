package com.c2v4.colonize.domain

import com.c2v4.colonize.domain.Resource.*
import com.c2v4.colonize.domain.observer.Observer
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
import java.lang.IllegalArgumentException

@RunWith(JUnitPlatform::class)
object ActionSpek : Spek({
    val testState = State(listOf(Player("Asd"), Player("Bsd")),
        0,
        wallets = mapOf(Player("Asd") to mapOf(HEAT to 3,
            ENERGY to 4,
            PLANT to 2),
            Player("Bsd") to mapOf(ENERGY to 1, HEAT to 3))
    )
    given("Pass Action") {
        val passAction = Pass(Player("Asd"))
        on("Invoke") {
            it("Sets players state as both actions played") {
                assertThat(passAction(testState)).isEqualTo(testState.copy(actionsPlayed = 1,
                    consecutivePasses = 1))
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
        val giveResource = GiveResource(mapOf(HEAT to 3,
            ENERGY to 4,
            IRON to 2),
            Player("Asd"))
        on("Check invoke") {
            it("Gives resource to player") {
                assertThat(giveResource(testState)).isEqualTo(testState.copy(wallets = testState.wallets.plus(
                    Player("Asd") to mapOf(
                        HEAT to 6,
                        ENERGY to 8,
                        PLANT to 2,
                        IRON to 2))))
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
        val spendResource = SpendResource(mapOf(ENERGY to 3, HEAT to 3),
            Player("Asd"))
        on("Check isApplicable") {
            it("True for positive case") {
                assertThat(spendResource.isApplicable(testState)).isTrue()
            }
            it("False for negative case") {
                assertThat(SpendResource(mapOf(ENERGY to 5,
                    HEAT to 2), Player("Asd")).isApplicable(testState)).isFalse()
            }
        }
        on("Check invoke") {
            it("Removes resources from player's wallet") {
                assertThat(spendResource(testState)).isIn(testState.copy(
                    wallets = testState.wallets.plus(Player("Asd") to mapOf(ENERGY to 1,
                        PLANT to 2))
                ),
                    testState.copy(wallets = testState.wallets.plus(Player("Asd") to mapOf(
                        ENERGY to 1,
                        PLANT to 2,
                        HEAT to 0))
                    ))
            }
            it("Throws exception when is not applicable") {
                Assertions.assertThatThrownBy {
                    SpendResource(mapOf(ENERGY to 3,
                        HEAT to 3), Player("Bsd"))(testState)
                }.isInstanceOf(
                        IllegalArgumentException::class.java)
            }
        }
    }

    given("AddObserver") {
        val observer = mock<Observer>()
        val addObserver = AddObserver(observer)
        on("Check isApplicable") {
            it("Is True") {
                assertThat(addObserver.isApplicable(testState)).isTrue()
            }
        }
        on("Check invoke") {
            it("Adds observer") {
                assertThat(addObserver(testState)).isEqualTo(testState.copy(observers = testState.observers.plus(
                    observer)))
            }
        }
    }

    given("Combined Actions") {
        on("isApplicable") {
            it("should return true when both actions are applicable") {
                val action = mock<Action>()
                val action2 = mock<Action>()

                BDDMockito.given(action.isApplicable(testState)).willReturn(true)
                BDDMockito.given(action2.isApplicable(testState)).willReturn(true)

                assertThat(action.combined(action2).isApplicable(testState)).isTrue()
            }
            it("should return false when at least one action is not applicable") {
                val action = mock<Action>()
                val action2 = mock<Action>()

                BDDMockito.given(action.isApplicable(testState)).willReturn(false)
                BDDMockito.given(action2.isApplicable(testState)).willReturn(true)

                assertThat(action.combined(action2).isApplicable(testState)).isFalse()
            }
        }
        on("invoke") {
            it("should apply all actions to the state") {
                val action = GiveResource(mapOf(IRON to 2), Player("Asd"))
                val action2 = GiveResource(mapOf(PLANT to 1), Player("Asd"))

                assertThat(action.combined(action2)(State(wallets = mapOf(Player("Asd") to mapOf(
                    HEAT to 3,
                    IRON to 2)))))
                    .isEqualTo(
                        State(wallets = mapOf(Player("Asd") to mapOf(
                            HEAT to 3,
                            IRON to 4,
                            PLANT to 1)))
                    )
            }
        }
    }
    given("Consequent Actions") {
        on("isApplicable") {
            it("should return true when both actions are applicable") {
                val action = mock<Action>()
                val action2 = mock<Action>()

                BDDMockito.given(action.isApplicable(testState)).willReturn(true)
                BDDMockito.given(action(testState)).willReturn(testState)
                BDDMockito.given(action2.isApplicable(testState)).willReturn(true)
                BDDMockito.given(action2(testState)).willReturn(testState)

                assertThat(action.consequent(action2).isApplicable(testState)).isTrue()
            }
            it("should return false when at least one action is not applicable") {
                val action = SpendResource(mapOf(IRON to 1), Player("Asd"))
                val action2 = SpendResource(mapOf(IRON to 2), Player("Asd"))

                assertThat(action.consequent(action2).isApplicable(State(wallets = mapOf(Player(
                    "Asd") to mapOf(IRON to 2))))).isFalse()
            }
        }
        on("invoke") {
            it("should apply all actions to the state") {
                val action = GiveResource(mapOf(IRON to 2), Player("Asd"))
                val action2 = GiveResource(mapOf(PLANT to 1), Player("Asd"))

                assertThat(action.consequent(action2)(State(wallets = mapOf(Player("Asd") to mapOf(
                    HEAT to 3,
                    IRON to 2)))))
                    .isEqualTo(
                        State(wallets = mapOf(Player("Asd") to mapOf(
                            HEAT to 3,
                            IRON to 4,
                            PLANT to 1)))
                    )
            }
        }
    }
})
