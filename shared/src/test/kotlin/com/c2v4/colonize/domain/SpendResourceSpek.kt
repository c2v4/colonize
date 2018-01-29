package com.c2v4.colonize.domain

import com.c2v4.colonize.domain.Resource.*
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import java.lang.IllegalArgumentException

@RunWith(JUnitPlatform::class)
class SpendResourceSpek : Spek({
    val testState = State(
            wallets = mapOf(Player("Asd") to mapOf(HEAT to 3, ENERGY to 4, PLANT to 2),
                    Player("Bsd") to mapOf(ENERGY to 1, HEAT to 3))
    )
    given("SpendResource") {
        val spendResource = SpendResource(mapOf(ENERGY to 3, HEAT to 3))
        on("Check isApplicable") {
            it("True for positive case") {
                assertThat(spendResource.isApplicable(Player("Asd"), testState)).isTrue()
            }
            it("False for negative case") {
                assertThat(SpendResource(mapOf(ENERGY to 5,
                        HEAT to 2)).isApplicable(Player("Asd"), testState)).isFalse()
            }
        }
        on("Check invoke") {
            it("Removes resources from player's wallet") {
                assertThat(spendResource(Player("Asd"), testState)).isIn(State(
                        wallets = testState.wallets.plus(Player("Asd") to mapOf(ENERGY to 1,
                                PLANT to 2))
                ), State(wallets = testState.wallets.plus(Player("Asd") to mapOf(ENERGY to 1,
                        PLANT to 2, HEAT to 0))
                ))
            }
            it("Throws exception when is not applicable") {
                assertThatThrownBy { spendResource(Player("Bsd"), testState) }.isInstanceOf(
                        IllegalArgumentException::class.java)
            }
        }
    }
})

