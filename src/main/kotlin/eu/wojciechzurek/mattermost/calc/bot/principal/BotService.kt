package eu.wojciechzurek.mattermost.calc.bot.principal

import eu.wojciechzurek.mattermost.calc.bot.event.CommandType


interface BotService {

    fun set(bot: Bot)
    fun get(): Bot

    fun getHelp(): Map<String, String>
    fun getCommands(): Map<CommandType, Set<String>>

    fun setHelp(command: String, message: String)
    fun setCommand(commandType: CommandType, command: String)
}