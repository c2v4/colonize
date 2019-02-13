package com.c2v4.colonize.domain

sealed class Action {
    abstract operator fun invoke(state: State): ActionEffect
}

const val TEMPERATURE_INCREMENT_VALUE = 2
const val OXYGEN_INCREMENT_VALUE = 2
const val TERRAFORM_RATING_INCREMENT_FOR_GLOBAL_PARAMETERS = 1

data class RaiseTemperature(val player: Player) : Action() {

    override fun invoke(state: State): ActionEffect =
        if (temperatureLens.get(state) == MAX_TEMPERATURE) ActionEffect(state)
        else ActionEffect(
            temperatureLens.modify(state) { it + TEMPERATURE_INCREMENT_VALUE }, listOf(
                ChangeTerraformRating(
                    player,
                    TERRAFORM_RATING_INCREMENT_FOR_GLOBAL_PARAMETERS
                )
            )
        )
}


data class RaiseOxygen(val player: Player) : Action() {

    override fun invoke(state: State): ActionEffect =
        if (oxygenLens.get(state) == MAX_OXYGEN) ActionEffect(state)
        else ActionEffect(
            oxygenLens.modify(state) { it + OXYGEN_INCREMENT_VALUE }, listOf(
                ChangeTerraformRating(
                    player,
                    TERRAFORM_RATING_INCREMENT_FOR_GLOBAL_PARAMETERS
                )
            )
        )
}

data class ChangeTerraformRating(val player: Player, val amount: Int) : Action() {
    override fun invoke(state: State): ActionEffect {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
