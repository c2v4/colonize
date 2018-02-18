package com.c2v4.colonize.domain.requirement

import com.c2v4.colonize.domain.State
import mock
import org.assertj.core.api.Assertions.assertThat
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import org.mockito.BDDMockito
import org.mockito.Mockito.times

@RunWith(JUnitPlatform::class)
object RequirementSpek : Spek({
    given("Requirement") {
        on("invoke") {
            it("calls selector and passes it's result to condition") {
                val selector = mock<Selector<Int>>()
                val condition = mock<Condition<Int>>()
                val state = State()

                val value = 17
                BDDMockito.given(selector.invoke(state)).willReturn(value)
                BDDMockito.given(condition.invoke(value)).willReturn(false)

                assertThat(Requirement(selector, condition).invoke(state)).isFalse()

                BDDMockito.then(selector).should(times(1)).invoke(state)
                BDDMockito.then(condition).should(times(1)).invoke(value)
            }
        }
    }
})
