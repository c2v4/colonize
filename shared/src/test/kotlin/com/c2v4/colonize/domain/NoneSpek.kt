package com.c2v4.colonize.domain

import org.assertj.core.api.Assertions.assertThat
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith

@RunWith(JUnitPlatform::class)
class NoneSpek : Spek({
    given("None Action"){
        on("Invoke"){
            it("Returns the same state"){
                assertThat(None(testState)).isEqualTo(testState)
            }
        }
        on("Applicable"){
            it("Is always true"){
                assertThat(None.isApplicable(testState)).isTrue()
            }
        }
    }
})