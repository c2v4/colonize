package com.c2v4.colonize.domain

import com.c2v4.colonize.domain.action.*
import com.c2v4.colonize.domain.observer.Observer
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
            it("Should use proper observers and apply their actions") {
                val qualified = object : Observer {
                    override fun isApplicable(action: Action,
                                              state: State): Boolean = action == None

                    override fun react(action: Action, state: State): Action = GiveResource(mapOf(
                        Resource.ENERGY to 1), Player("Asd"))
                }
                val notQualified = object : Observer {
                    override fun isApplicable(action: Action, state: State): Boolean = false
                    override fun react(action: Action, state: State): Action = GiveResource(mapOf(
                        Resource.ENERGY to 1), Player("Bsd"))
                }
                val state = testState.copy(observers = listOf(qualified, notQualified))

                assertThat(state.apply(None))
                    .isEqualToComparingFieldByField(
                        state.copy(actionsPlayed = 1,
                            wallets = mapOf(Player("Asd") to mapOf(Resource.ENERGY to 1))))
            }
            it("Should split Combined actions") {
                val qualified = object : Observer {
                    override fun isApplicable(action: Action,
                                              state: State): Boolean = action == None

                    override fun react(action: Action, state: State): Action = GiveResource(mapOf(
                        Resource.ENERGY to 1), Player("Asd"))
                }
                val notQualified = object : Observer {
                    override fun isApplicable(action: Action,
                                              state: State): Boolean = action == Pass(Player("Bsd"))

                    override fun react(action: Action, state: State): Action = GiveResource(
                        mapOf(
                            Resource.PLANT to 1),
                        Player("Bsd"))
                }
                val state = testState.copy(observers = listOf(qualified, notQualified))

                val action = Pass(Player("Bsd")).combined(None.combined(None)).combined(None)

                assertThat(state.apply(action))
                    .isEqualToComparingFieldByField(
                        state.copy(consecutivePasses = 0, currentPlayer = 0,
                            wallets = mapOf(Player("Asd") to mapOf(Resource.ENERGY to 3),
                                Player("Bsd") to mapOf(Resource.PLANT to 1))))

            }
            it("Should split Combined actions") {
                val qualified = object : Observer {
                    override fun isApplicable(action: Action,
                                              state: State): Boolean = action == None

                    override fun react(action: Action, state: State): Action = GiveResource(mapOf(
                        Resource.ENERGY to 1), Player("Asd"))
                }
                val notQualified = object : Observer {
                    override fun isApplicable(action: Action,
                                              state: State): Boolean = action == Pass(Player("Bsd"))

                    override fun react(action: Action, state: State): Action = GiveResource(
                        mapOf(
                            Resource.PLANT to 1),
                        Player("Bsd"))
                }
                val state = testState.copy(observers = listOf(qualified, notQualified))

                val action = Pass(Player("Bsd")).consequent(None.consequent(None)).consequent(None)

                assertThat(state.apply(action))
                    .isEqualToComparingFieldByField(
                        state.copy(consecutivePasses = 1, currentPlayer = 0,
                            wallets = mapOf(Player("Asd") to mapOf(Resource.ENERGY to 3),
                                Player("Bsd") to mapOf(Resource.PLANT to 1))))

            }
        }

        on("After 2 actions played") {
            it("Changes player") {
                assertThat(testState.copy(actionsPlayed = 1).apply(None)).isEqualToComparingFieldByField(
                    testState.copy(
                        currentPlayer = 0))
            }
        }

        on("2 consecutive passes") {
            it("should end generation (add resources, " +
                "change current player to starting player, change starting player)") {
                val localState = testState.copy(incomes = mapOf(Player("Asd") to mapOf(Resource.GOLD to -3,
                    Resource.IRON to 1)),
                    points = mapOf(Player("Asd") to 15))
                assertThat(localState.apply(Pass(Player("Bsd"))).apply(Pass(
                    Player("Asd")))).isEqualToComparingFieldByField(localState.copy(
                    nextStartingPlayer = 0,
                    wallets = mapOf(Player("Asd") to mapOf(Resource.GOLD to 12, Resource.IRON to 1))
                ))
            }
        }
    }
})
