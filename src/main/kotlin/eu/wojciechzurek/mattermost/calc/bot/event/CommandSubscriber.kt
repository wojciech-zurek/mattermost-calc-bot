package eu.wojciechzurek.mattermost.calc.bot.event

import eu.wojciechzurek.mattermost.calc.bot.api.mattermost.EphemeralPost
import eu.wojciechzurek.mattermost.calc.bot.api.mattermost.Event
import eu.wojciechzurek.mattermost.calc.bot.api.mattermost.Post
import eu.wojciechzurek.mattermost.calc.bot.loggerFor
import eu.wojciechzurek.mattermost.calc.bot.service.MattermostService
import io.micronaut.context.MessageSource
import io.micronaut.context.event.ApplicationEventListener
import io.micronaut.runtime.server.event.ServerStartupEvent
import javax.inject.Inject

abstract class CommandSubscriber : PostedEventSubscriber(), ApplicationEventListener<ServerStartupEvent> {

    private val logger = loggerFor(this.javaClass)

    @Inject
    protected lateinit var mattermostService: MattermostService

    @Inject
    protected lateinit var messageSource: MessageSource

    protected val pref = ".prefix"

    abstract fun getName(): String

    abstract fun getHelp(): String

    abstract fun getCommandType(): CommandType

    abstract fun onEvent(event: Event, message: String)

    protected open fun getPrefix(): String = getName() + pref

    override fun onApplicationEvent(event: ServerStartupEvent) {
        botService.setCommand(getCommandType(), getPrefix())
        botService.setHelp(getPrefix(), getHelp())
    }

    override fun filter(event: Event): Boolean {
        return (event.data.post?.message?.trimStart()?.startsWith(getPrefix())
                ?: false) && super.filter(event)
    }

    override fun onEvent(event: Event) {
        logger.info("Command: {}", getPrefix())
        onEvent(event, event.data.post?.message?.removePrefix(getPrefix())?.trim()!!)
    }

    override fun onError(event: Event, e: Exception) {
        val userId = event.data.post!!.userId!!
        val post = Post(

                channelId = event.data.post.channelId,
                message = "${event.data.senderName}\n" +
                        "command error!\n" +
                        "exception:\n" +
                        "```\n" +
                        "${e.message}!\n" +
                        "```\n" +
                        "usage: getPrefix() + " + getHelp()
        )

        val ephemeralPost = EphemeralPost(userId, post)

        mattermostService.ephemeralPost(ephemeralPost)
    }
}