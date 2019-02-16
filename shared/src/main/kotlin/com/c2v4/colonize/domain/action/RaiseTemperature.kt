package com.c2v4.colonize.domain.action

import com.c2v4.colonize.domain.*
import com.c2v4.colonize.domain.action.expectance.PlaceOcean

const val TEMPERATURE_INCREMENT_VALUE = 2
const val TEMPERATURE_PRECEDING_FIRST_HEAT_BONUS = -26
const val TEMPERATURE_PRECEDING_SECOND_HEAT_BONUS = -22
const val TEMPERATURE_PRECEDING_OCEAN_BONUS = -2

data class RaiseTemperature(val player: Player) : Action {
    override fun invoke(state: State): ActionEffect =
        when (temperatureLens.get(state)) {
            TEMPERATURE_PRECEDING_FIRST_HEAT_BONUS,
            TEMPERATURE_PRECEDING_SECOND_HEAT_BONUS -> ActionEffect(
                temperatureLens.modify(state) { it + TEMPERATURE_INCREMENT_VALUE },
                listOf(
                    ChangeTerraformRating(
                        player,
                        TERRAFORM_RATING_INCREMENT_FOR_GLOBAL_PARAMETERS
                    ),
                    IncreaseProduction(player,Resource.HEAT,1)
                )
            )
            TEMPERATURE_PRECEDING_OCEAN_BONUS ->ActionEffect(
                temperatureLens.modify(state) { it + TEMPERATURE_INCREMENT_VALUE },
                listOf(
                    ChangeTerraformRating(
                        player,
                        TERRAFORM_RATING_INCREMENT_FOR_GLOBAL_PARAMETERS
                    )
                ),
                setOf(PlaceOcean(player))
            )
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
