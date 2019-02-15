package com.c2v4.colonize.domain.action

import com.c2v4.colonize.domain.*

const val OXYGEN_INCREMENT_VALUE = 2
const val OXYGEN_RAISE_PRECEEDING_BONUS = 8

data class RaiseOxygen(val player: Player) : Action {

    override fun invoke(state: State): ActionEffect =
        when (oxygenLens.get(state)) {
            OXYGEN_RAISE_PRECEEDING_BONUS -> ActionEffect(
                oxygenLens.modify(state) { it + OXYGEN_INCREMENT_VALUE },
                listOf(
                    ChangeTerraformRating(
                        player,
                        TERRAFORM_RATING_INCREMENT_FOR_GLOBAL_PARAMETERS
                    ),
                    RaiseTemperature(player)
                )
            )
            MAX_OXYGEN -> ActionEffect(state)
            else -> ActionEffect(
                oxygenLens.modify(state) { it + OXYGEN_INCREMENT_VALUE },
                listOf(
                    ChangeTerraformRating(
                        player,
                        TERRAFORM_RATING_INCREMENT_FOR_GLOBAL_PARAMETERS
                    )
                )
            )
        }
}

