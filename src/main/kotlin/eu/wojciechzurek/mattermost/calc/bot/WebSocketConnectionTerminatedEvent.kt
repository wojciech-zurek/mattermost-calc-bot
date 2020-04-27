package eu.wojciechzurek.mattermost.calc.bot

import io.micronaut.context.event.ApplicationEvent

class WebSocketConnectionTerminatedEvent (source: Any) : ApplicationEvent(source)