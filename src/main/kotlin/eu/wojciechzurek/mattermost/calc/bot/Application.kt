package eu.wojciechzurek.mattermost.calc.bot

import io.micronaut.runtime.Micronaut

object Application {

    @JvmStatic
    fun main(args: Array<String>) {
        Micronaut.build()
                .eagerInitConfiguration(true)
                .eagerInitSingletons(true)
                .packages("micronaut.calc.bot")
                .mainClass(Application.javaClass)
                .start()
    }
}