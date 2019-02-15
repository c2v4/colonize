package com.c2v4.colonize.domain.action

import com.c2v4.colonize.domain.*

const val TEMPERATURE_INCREMENT_VALUE = 2

data class RaiseTemperature(val player: Player) : Action {
    override fun invoke(state: State): ActionEffect =
        when (temperatureLens.get(state)) {
            MAX_TEMPERATURE -> ActionEffect(state)
            else -> ActionEffect(
                temperatureLens.modify(state) { it + TEMPERATURE_INCREMENT_VALUE },
                listOf(
                    ChangeTerraformRating(
                        player,
                        TERRAFORM_RATING_INCREMENT_FOR_GLOBAL_PARAMETERS
                    )
                )
            )
        }
}
