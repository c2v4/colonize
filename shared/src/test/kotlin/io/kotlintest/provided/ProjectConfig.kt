package io.kotlintest.provided

import io.kotlintest.AbstractProjectConfig
import io.kotlintest.RandomSpecExecutionOrder
import io.kotlintest.SpecExecutionOrder

object ProjectConfig : AbstractProjectConfig(){

    override fun parallelism(): Int = 7

    override fun specExecutionOrder(): SpecExecutionOrder = RandomSpecExecutionOrder

}
