package com.c2v4.colonize

import com.c2v4.colonize.domain.State
import cucumber.api.java8.En
import org.assertj.core.api.Assertions.assertThat

class InitializationStepDef(private val world: World): En {

    init {

        Given("I set up game for {int} players") { int1: Int ->
            world.state = State()
        }

        Then("The state exists") {
            assertThat(world.state).isNotNull()
        }
    }
}
