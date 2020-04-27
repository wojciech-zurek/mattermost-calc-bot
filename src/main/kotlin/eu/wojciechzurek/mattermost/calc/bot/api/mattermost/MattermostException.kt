package eu.wojciechzurek.mattermost.calc.bot.api.mattermost

import io.micronaut.http.HttpStatus
import io.micronaut.websocket.exceptions.WebSocketClientException

class MattermostException(
        val httpStatus: HttpStatus,
        message: String
) : WebSocketClientException(message)