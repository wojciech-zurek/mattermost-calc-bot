package eu.wojciechzurek.mattermost.calc.bot.api.mattermost

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class Link(
        val link: String
)