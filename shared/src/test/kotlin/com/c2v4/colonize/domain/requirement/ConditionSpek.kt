package com.c2v4.colonize.domain.requirement

import org.assertj.core.api.Assertions.assertThat
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on

object ConditionSpek : Spek({
    given("AtMost condition") {
        val atMost = AtMost(10)
        on("invoke") {
            it("returns true for equal value") {
                assertThat(atMost(10)).isTrue()
            }
            it("returns false for greater value") {
                assertThat(atMost(12)).isFalse()

            }
            it("returns true for smaller value") {
                assertThat(atMost(8)).isTrue()

            }
        }
    }
})