package eu.wojciechzurek.mattermost.calc.bot.service

import eu.wojciechzurek.mattermost.calc.bot.api.mattermost.Event
import eu.wojciechzurek.mattermost.calc.bot.event.MattermostEventSubscriber


interface DispatcherService {

    fun subscribe(s: MattermostEventSubscriber)
    fun onNext(event: Event)
}