package eu.wojciechzurek.mattermost.calc.bot.principal

data class Bot (
        val userId: String,
        val userName: String,
        val roles: String,
        val systemAdmin: Boolean
)