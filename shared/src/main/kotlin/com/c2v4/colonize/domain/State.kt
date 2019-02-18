package com.c2v4.colonize.domain

import com.c2v4.colonize.domain.map.*

data class State(
    val globalParameters: GlobalParameters = GlobalParameters(),
    val surfaceMap: SurfaceMap = SurfaceMap(),
    val milestones: Milestones = Milestones(),
    val awards: Awards = Awards()
)

fun endGameCheck(state: State): Boolean {
    return temperatureLens.get(state) == MAX_TEMPERATURE && oxygenLens.get(state) == MAX_OXYGEN && oceansLens.get(
        state
    ) == MAX_NUMBER_OF_OCEANS
}


