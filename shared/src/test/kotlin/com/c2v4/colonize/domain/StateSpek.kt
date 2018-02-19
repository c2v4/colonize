package com.c2v4.colonize.domain

import com.c2v4.colonize.domain.observer.Observer
import org.assertj.core.api.Assertions.assertThat
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.jetbrains.spek.api.dsl.xit
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith

@RunWith(JUnitPlatform::class)
object StateSpek : Spek({
    given("A State") {
        val testState = State(players = listOf(Player("Asd"), Player("Bsd")), currentPlayer = 1)

        on("After action played") {
            xit("Stays with the same player") {
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
                        state.copy(actionsPlayed = 1,wallets = mapOf(Player("Asd") to mapOf(Resource.ENERGY to 1))))
            }
        }

        on("After 2 actions played") {
            xit("Changes player") {
                assertThat(testState.copy(actionsPlayed = 1).apply(None)).isEqualToComparingFieldByField(
                    testState.copy(
                        currentPlayer = 0))
            }
        }

    }
})
