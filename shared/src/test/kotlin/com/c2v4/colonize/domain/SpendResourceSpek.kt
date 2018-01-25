package com.c2v4.colonize.domain

import com.c2v4.colonize.domain.Resource.ENERGY
import com.c2v4.colonize.domain.Resource.HEAT
import org.assertj.core.api.Assertions.assertThat
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on


object SpendResourceSpek : Spek({
    val testState = State(
            wallets = mapOf(Player("Asd") to mapOf(HEAT to 3, ENERGY to 4),
                    Player("Bsd") to mapOf(ENERGY to 1, HEAT to 2))
    )
    given("SpendResource") {
        on("Check isApplicable") {
            it("True for positive case") {
                assertThat(SpendResource(mapOf(ENERGY to 3,
                        HEAT to 2)).isApplicable(Player("Asd"), testState)).isTrue()
            }
        }
    }
})

