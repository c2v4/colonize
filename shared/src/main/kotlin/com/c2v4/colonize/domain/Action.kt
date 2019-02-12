package com.c2v4.colonize.domain

sealed class Action {
    abstract operator fun invoke(state: State): Pair<State, List<Action>>
}

const val TEMPERATURE_INCREMENT_VALUE = 2
const val OXYGEN_INCREMENT_VALUE = 2
const val TERRAFORM_RATING_INCREMENT_FOR_GLOBAL_PARAMETERS = 1

data class RaiseTemperature(val player: Player) : Action() {

    override fun invoke(state: State): Pair<State, List<Action>> =
        if (temperatureLens.get(state) == MAX_TEMPERATURE) state to emptyList()
        else temperatureLens.modify(state) { it + TEMPERATURE_INCREMENT_VALUE } to listOf(
            ChangeTerraformRating(
                player,
                TERRAFORM_RATING_INCREMENT_FOR_GLOBAL_PARAMETERS
            )
        )
}


data class RaiseOxygen(val player: Player) : Action() {

    override fun invoke(state: State): Pair<State, List<Action>> =
        if (oxygenLens.get(state) == MAX_OXYGEN) state to emptyList()
        else oxygenLens.modify(state) { it + OXYGEN_INCREMENT_VALUE } to listOf(
            ChangeTerraformRating(
                player,
                TERRAFORM_RATING_INCREMENT_FOR_GLOBAL_PARAMETERS
            )
        )
}

data class ChangeTerraformRating(val player: Player, val amount: Int) : Action() {
    override fun invoke(state: State): Pair<State, List<Action>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
