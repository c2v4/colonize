package com.c2v4.colonize.domain.action

import com.c2v4.colonize.domain.*
import com.c2v4.colonize.domain.map.SurfaceMap
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.kotlintest.matchers.boolean.shouldBeFalse
import io.kotlintest.matchers.boolean.shouldBeTrue
import io.kotlintest.specs.AnnotationSpec

internal class StateTest : AnnotationSpec() {

    @Test
    internal fun endGamePositive() {
        val surfaceMapMock = mock<SurfaceMap>()
        whenever(surfaceMapMock.numberOfOceans).thenReturn(MAX_NUMBER_OF_OCEANS)
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
