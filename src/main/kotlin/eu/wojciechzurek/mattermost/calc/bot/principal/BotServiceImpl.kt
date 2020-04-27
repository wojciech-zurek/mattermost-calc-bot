package eu.wojciechzurek.mattermost.calc.bot.principal

import eu.wojciechzurek.mattermost.calc.bot.event.CommandType
import eu.wojciechzurek.mattermost.calc.bot.loggerFor
import java.util.*
import javax.annotation.PostConstruct
import javax.inject.Singleton

@Singleton
class BotServiceImpl : BotService {

    private lateinit var bot: Bot
    private val logger = loggerFor(this.javaClass)

    private val commands: MutableMap<CommandType, MutableSet<String>> = TreeMap<CommandType, MutableSet<String>>().toMutableMap()

    private val help = mutableMapOf<String, String>()

    @PostConstruct
    fun afterPropertiesSet() {
        CommandType.values().map {
            commands[it] = mutableSetOf<String>().toSortedSet()
        }
    }

    override fun set(bot: Bot) {
        logger.info("Setting new bot: {}", bot)
        this.bot = bot
    }

    override fun get(): Bot = bot

    override fun setCommand(commandType: CommandType, command: String) {
        commands[commandType]?.add(command)
    }

    override fun setHelp(command: String, message: String) {
        help[command] = message
    }

    override fun getHelp(): Map<String, String> = help

    override fun getCommands(): Map<CommandType, Set<String>> = commands
}