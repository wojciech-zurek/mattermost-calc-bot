package eu.wojciechzurek.mattermost.calc.bot.service

import eu.wojciechzurek.mattermost.calc.bot.api.mattermost.Event
import eu.wojciechzurek.mattermost.calc.bot.event.MattermostEventSubscriber
import io.reactivex.processors.PublishProcessor
import javax.inject.Singleton

@Singleton
class DispatcherServiceImpl(list: List<MattermostEventSubscriber>) : DispatcherService {

    private val emitter = PublishProcessor.create<Event>().also { ep ->
        list.forEach { ep.onBackpressureBuffer().subscribe(it) }
    }

    override fun subscribe(s: MattermostEventSubscriber) = emitter.subscribe(s)

    override fun onNext(event: Event) {
        emitter.onNext(event)
    }
}