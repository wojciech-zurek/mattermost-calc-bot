package eu.wojciechzurek.mattermost.calc.bot

import io.micronaut.context.annotation.Value
import io.micronaut.http.HttpRequest
import io.micronaut.http.client.annotation.Client
import io.micronaut.http.client.netty.DefaultHttpClient
import io.micronaut.runtime.event.annotation.EventListener
import io.micronaut.runtime.server.event.ServerStartupEvent
import io.micronaut.websocket.RxWebSocketClient
import javax.inject.Singleton

@Singleton
class WebSocket(
        @Client("\${mattermost.api.endpoint}") private val client: RxWebSocketClient,
        @Value("\${mattermost.key}") private val apiKey: String
) {

    private val logger = loggerFor(this::class.java)

    @EventListener
    fun onApplicationEvent(event: ServerStartupEvent) {
        connect()
    }

    @EventListener
    fun onApplicationEvent(event: WebSocketConnectionTerminatedEvent) {
        connect()
    }

    private fun connect() {

        val wss = client as DefaultHttpClient
        val request = HttpRequest.GET<Any>("/websocket")
        request.bearerAuth(apiKey)
        wss.connect(WebSocketClient::class.java, request).subscribe {
            logger.info(it.session?.id)
        }
    }
}