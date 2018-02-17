package com.c2v4.colonize.domain.requirement

import com.c2v4.colonize.domain.State
import org.assertj.core.api.Assertions.assertThat
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith

@RunWith(JUnitPlatform::class)
object SelectorSpek : Spek({
    given("Temperature selector") {
        on("invoke") {
            it("returns temperature value") {
                assertThat(Temperature(State(temperature = 12))).isEqualTo(12)
            }
        }
    }
})