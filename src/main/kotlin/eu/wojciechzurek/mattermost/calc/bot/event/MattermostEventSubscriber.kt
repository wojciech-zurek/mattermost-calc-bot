package eu.wojciechzurek.mattermost.calc.bot.event

import eu.wojciechzurek.mattermost.calc.bot.api.mattermost.Event
import eu.wojciechzurek.mattermost.calc.bot.loggerFor
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription

abstract class MattermostEventSubscriber : Subscriber<Event> {

    private val logger = loggerFor(this.javaClass)

    private lateinit var subscription: Subscription

    abstract fun getType(): String
    abstract fun onEvent(event: Event)

    final override fun onNext(event: Event) {
        try {
            if (event.event == getType() && filter(event)) onEvent(event)
        } catch (e: Exception) {
            logger.error(e.message, e)
            onError(event, e)
        }

        subscription.request(1)
    }

    override fun onError(t: Throwable?) {
        logger.error(t.toString())
    }

    override fun onComplete() {
        logger.info("On complete: {}", this.javaClass.simpleName)
    }

    open fun onError(event: Event, e: Exception) = Unit

    open fun filter(event: Event): Boolean = true

    override fun onSubscribe(subscription: Subscription) {
        this.subscription = subscription
        subscription.request(1)
        logger.info("Subscription started: {}", this.javaClass.simpleName)
    }
}