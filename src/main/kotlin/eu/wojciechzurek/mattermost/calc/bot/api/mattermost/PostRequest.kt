package eu.wojciechzurek.mattermost.calc.bot.api.mattermost

import com.fasterxml.jackson.annotation.JsonProperty

data class PostRequest (
        @JsonProperty("user_id")
        val userId: String,
        val post: Post
)