package eu.wojciechzurek.mattermost.calc.bot.event.command

import eu.wojciechzurek.mattermost.calc.bot.api.mattermost.EphemeralPost
import eu.wojciechzurek.mattermost.calc.bot.api.mattermost.Event
import eu.wojciechzurek.mattermost.calc.bot.api.mattermost.Post
import eu.wojciechzurek.mattermost.calc.bot.event.CommandSubscriber
import eu.wojciechzurek.mattermost.calc.bot.event.CommandType
import eu.wojciechzurek.mattermost.calc.bot.loggerFor
import org.mariuszgromada.math.mxparser.Expression
import org.mariuszgromada.math.mxparser.mXparser
import javax.inject.Singleton

@Singleton
class CalcCommand : CommandSubscriber() {

    private val logger = loggerFor(this.javaClass)

    private val publicArg = "-p"

    override fun getPrefix(): String = "!calc"

    override fun getName(): String = ""

    override fun getCommandType(): CommandType = CommandType.MAIN

    override fun getHelp(): String = " !calc -p [math expression]"

    override fun onEvent(event: Event, message: String) = calc(event, message)

    private fun calc(event: Event, arg: String) {

        val channelId = event.data.post?.channelId!!
        val userId = event.data.post.userId!!
        var ephemeral = true

        val expr = if (arg.startsWith(publicArg)) {
            ephemeral = false
            arg.removePrefix(publicArg)
        } else {
            arg
        }
        val expression = Expression(expr)

        val post = Post(
                userId = userId,
                channelId = channelId,
                message = "```latex\n" +
                        expression.expressionString + " = " + expression.calculate() +
                        "\n```\n"
        )

        when (ephemeral) {
            true -> mattermostService.ephemeralPost(EphemeralPost(userId, post))
            false -> mattermostService.post(post)
        }
    }
}