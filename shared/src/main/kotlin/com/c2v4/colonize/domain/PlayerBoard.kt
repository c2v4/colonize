package com.c2v4.colonize.domain

import arrow.optics.Lens

data class PlayerBoard(
    val currentResources: ResourceBundle = ResourceBundle(),
    val production: ResourceBundle = ResourceBundle(),
    val steelMegaCreditRatio: Int = 2,
    val titaniumMegaCreditRatio: Int = 3
) {
    fun canLowerProduction(resource: Resource) =
        playerBoardProductionLensFactory(resource).get(this) > resource.lowerLimitProduction
}

data class ResourceBundle(
    val megaCredits: Int = 0,
    val steel: Int = 0,
    val titanium: Int = 0,
    val plants: Int = 0,
    val energy: Int = 0,
    val heat: Int = 0
)

val currentResourceBundleLens: Lens<PlayerBoard, ResourceBundle> = Lens(
    get = { s -> s.currentResources },
    set = { s, b -> s.copy(currentResources = b) }
)


val productionResourceBundleLens: Lens<PlayerBoard, ResourceBundle> = Lens(
    get = { s -> s.production },
    set = { s, b -> s.copy(production = b) }
)

val playerBoardResourceLensFactory: (Resource) -> Lens<PlayerBoard, Int> = { resource ->
    currentResourceBundleLens.compose(
        resourceBundleLensFactory(resource)
    )
}


val playerBoardProductionLensFactory: (Resource) -> Lens<PlayerBoard, Int> = { resource ->
    productionResourceBundleLens.compose(
        resourceBundleLensFactory(resource)
    )
}


val resourceBundleLensFactory: (Resource) -> Lens<ResourceBundle, Int> = { resource: Resource ->
    when (resource) {
        Resource.HEAT -> Lens(
            get = { s: ResourceBundle -> s.heat },
            set = { s: ResourceBundle, b: Int -> s.copy(heat = b) }
        )
        Resource.PLANTS -> Lens(
            get = { s: ResourceBundle -> s.plants },
            set = { s: ResourceBundle, b: Int -> s.copy(plants = b) }
        )
        Resource.TITANIUM -> Lens(
            get = { s: ResourceBundle -> s.titanium },
            set = { s: ResourceBundle, b: Int -> s.copy(titanium = b) }
        )
        Resource.STEEL -> Lens(
            get = { s: ResourceBundle -> s.steel },
            set = { s: ResourceBundle, b: Int -> s.copy(steel = b) }
        )
        Resource.MEGA_CREDITS -> Lens(
            get = { s: ResourceBundle -> s.megaCredits },
            set = { s: ResourceBundle, b: Int -> s.copy(megaCredits = b) }
        )
        Resource.ENERGY -> Lens(
            get = { s: ResourceBundle -> s.energy },
            set = { s: ResourceBundle, b: Int -> s.copy(energy = b) }
        )
    }
}

