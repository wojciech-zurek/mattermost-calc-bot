package eu.wojciechzurek.mattermost.calc.bot.event

import eu.wojciechzurek.mattermost.calc.bot.api.mattermost.Event
import eu.wojciechzurek.mattermost.calc.bot.principal.Bot
import eu.wojciechzurek.mattermost.calc.bot.principal.BotService
import eu.wojciechzurek.mattermost.calc.bot.service.MattermostService
import javax.inject.Singleton

@Singleton
class HelloEventSubscriber(
        private val botService: BotService,
        private val mattermostService: MattermostService
) : MattermostEventSubscriber() {

    override fun getType(): String = "hello"

    override fun onEvent(event: Event) {
        event.broadcast.userId?.let { getBotInfo(it) }
    }

    private fun getBotInfo(id: String) {
        mattermostService
                .user(id)
                .map { Bot(it.id, it.userName, it.roles, it.roles.contains("system_admin")) }
                .subscribe { res -> botService.set(res) }
    }
}