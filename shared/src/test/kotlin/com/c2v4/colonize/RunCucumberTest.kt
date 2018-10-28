package com.c2v4.colonize

import cucumber.api.CucumberOptions
import cucumber.api.junit.Cucumber
import org.junit.runner.RunWith

@RunWith(Cucumber::class)
@CucumberOptions(
    features = ["src/test/resources/features"],
    tags = ["not @ignored"],
    strict = true)
class RunCucumberTest
