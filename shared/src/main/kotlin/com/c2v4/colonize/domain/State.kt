package com.c2v4.colonize.domain

data class State(
    val numberOfPlayers: Int,
    val globalParameters: GlobalParameters,
    val surfaceMap: SurfaceMap,
    val milestones: Milestones,
    val awards: Awards
)
