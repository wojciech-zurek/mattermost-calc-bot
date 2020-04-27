package eu.wojciechzurek.mattermost.calc.bot.event.command

import eu.wojciechzurek.mattermost.calc.bot.api.mattermost.EphemeralPost
import eu.wojciechzurek.mattermost.calc.bot.api.mattermost.Event
import eu.wojciechzurek.mattermost.calc.bot.api.mattermost.Post
import eu.wojciechzurek.mattermost.calc.bot.event.CommandSubscriber
import eu.wojciechzurek.mattermost.calc.bot.event.CommandType
import eu.wojciechzurek.mattermost.calc.bot.loggerFor
import org.mariuszgromada.math.mxparser.mXparser
import javax.inject.Singleton

@Singleton
class HelpCommand  : CommandSubscriber() {

    private val logger = loggerFor(this.javaClass)

    override fun getPrefix(): String = "!help"

    override fun getName(): String  = ""

    override fun getCommandType(): CommandType = CommandType.MAIN

    override fun getHelp(): String = " !help"

    override fun onEvent(event: Event, message: String) = help(event)

    private fun help(event: Event) {

        val channelId = event.data.post?.channelId!!
        val userId = event.data.post.userId!!

        val post = Post(
                channelId = channelId,
                message = "```\n" +
                        mXparser.getHelp() +
                        "\n```\n"
        )


        val ephemeralPost = EphemeralPost(userId, post)

        mattermostService.ephemeralPost(ephemeralPost)
    }
}