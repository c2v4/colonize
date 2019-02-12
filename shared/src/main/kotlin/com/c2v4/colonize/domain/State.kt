package com.c2v4.colonize.domain

data class State(
    val numberOfPlayers: Int,
    val globalParameters: GlobalParameters,
    val surfaceMap: SurfaceMap,
    val milestones: Milestones,
    val awards: Awards
)

fun endGameCheck(state: State) =
    state.globalParameters.oxygen == 14 && state.globalParameters.temperature == 8 && state.surfaceMap.numberOfOceans == 9
