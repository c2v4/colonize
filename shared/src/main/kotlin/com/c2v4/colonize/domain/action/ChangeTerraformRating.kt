package com.c2v4.colonize.domain.action

import com.c2v4.colonize.domain.Player
import com.c2v4.colonize.domain.State

const val TERRAFORM_RATING_INCREMENT_FOR_GLOBAL_PARAMETERS = 1

data class ChangeTerraformRating(val player: Player, val amount: Int) :
    Action {
    override fun invoke(state: State): ActionEffect {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
