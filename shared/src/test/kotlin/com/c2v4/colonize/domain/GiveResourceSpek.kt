package com.c2v4.colonize.domain

import com.c2v4.colonize.domain.Resource.*
import org.assertj.core.api.Assertions.assertThat
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith

@RunWith(JUnitPlatform::class)
class GiveResourceSpek : Spek({
    val testState = State(
            wallets = mapOf(Player("Asd") to mapOf(HEAT to 3, ENERGY to 4, PLANT to 2),
                    Player("Bsd") to mapOf(ENERGY to 1, HEAT to 3))
    )
    given("GiveResource") {
        val giveResource = GiveResource(mapOf(HEAT to 3,ENERGY to 4),Player("Asd") )
        on("Check invoke"){
            it("Gives resource to player"){
                assertThat(giveResource(testState)).isEqualTo(testState.copy(wallets = testState.wallets.plus(Player("Asd") to mapOf(HEAT to 6, ENERGY to 8, PLANT to 2))))
            }
        }
        on("Check applicable"){
            it("Is always true"){
                assertThat(giveResource.isApplicable(testState)).isTrue()
            }
        }
    }
})

