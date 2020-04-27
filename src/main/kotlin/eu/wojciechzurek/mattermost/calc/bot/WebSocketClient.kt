package eu.wojciechzurek.mattermost.calc.bot

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import eu.wojciechzurek.mattermost.calc.bot.api.mattermost.Event
import eu.wojciechzurek.mattermost.calc.bot.service.DispatcherService
import io.micronaut.context.event.ApplicationEventPublisher
import io.micronaut.http.HttpRequest
import io.micronaut.websocket.WebSocketSession
import io.micronaut.websocket.annotation.*

@ClientWebSocket
abstract class WebSocketClient(
        private val dispatcherService: DispatcherService,
        private val eventPublisher: ApplicationEventPublisher
) : AutoCloseable {

    var session: WebSocketSession? = null
        private set
    var request: HttpRequest<*>? = null
        private set

    private val logger = loggerFor(this.javaClass)
    private val mapper = jacksonObjectMapper()

    @OnOpen
    fun onOpen(session: WebSocketSession, request: HttpRequest<*>) {
        this.session = session
        this.request = request
    }

    @OnError
    fun onError(err: Throwable) {
        logger.error(err.toString())
    }

    @OnMessage
    fun onMessage(message: String) {
        val event = mapper.readValue(message, Event::class.java)
        dispatcherService.onNext(event)
        logger.info(message)
    }

    @OnClose
    fun onClose() {
        logger.info("onClose")
        eventPublisher.publishEvent(WebSocketConnectionTerminatedEvent(this))
    }
}