package com.c2v4.colonize.domain.action

import com.c2v4.colonize.domain.*
import com.c2v4.colonize.domain.map.MAX_NUMBER_OF_OCEANS
import com.c2v4.colonize.domain.map.SurfaceMap
import io.kotlintest.matchers.boolean.shouldBeFalse
import io.kotlintest.matchers.boolean.shouldBeTrue
import io.kotlintest.specs.AnnotationSpec
import io.mockk.every
import io.mockk.mockk

internal class StateTest : AnnotationSpec() {

    @Test
    internal fun endGamePositive() {
        val surfaceMapMock = mockk<SurfaceMap>()
        every{surfaceMapMock.numberOfOceans } returns MAX_NUMBER_OF_OCEANS
        endGameCheck(
            State(
                globalParameters = GlobalParameters(
                    temperature = MAX_TEMPERATURE,
                    oxygen = MAX_OXYGEN
                ),
                surfaceMap = surfaceMapMock
            )
        ).shouldBeTrue()
    }

    @Test
    internal fun endGameNegative() {
        endGameCheck(State()).shouldBeFalse()
    }
}
