package eu.wojciechzurek.mattermost.calc.bot.event

import eu.wojciechzurek.mattermost.calc.bot.api.mattermost.Event
import eu.wojciechzurek.mattermost.calc.bot.principal.BotService
import javax.inject.Inject

abstract class PostedEventSubscriber : MattermostEventSubscriber() {

    @Inject
    protected lateinit var botService: BotService

    override fun getType(): String = "posted"

    override fun filter(event: Event): Boolean {
        return event.data.userId != botService.get().userId
    }
}