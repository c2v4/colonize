package com.c2v4.colonize.domain

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.kotlintest.matchers.boolean.shouldBeFalse
import io.kotlintest.matchers.boolean.shouldBeTrue
import io.kotlintest.specs.AnnotationSpec

internal class StateTest : AnnotationSpec() {

    @Test
    internal fun endGamePositive() {
        val surfaceMapMock = mock<SurfaceMap>()
        whenever(surfaceMapMock.numberOfOceans).thenReturn(9)
        endGameCheck(
            State(
                globalParameters = GlobalParameters(temperature = 8, oxygen = 14),
                surfaceMap = surfaceMapMock
            )
        ).shouldBeTrue()
    }

    @Test
    internal fun endGameNegative() {
        endGameCheck(State()).shouldBeFalse()
    }
}
