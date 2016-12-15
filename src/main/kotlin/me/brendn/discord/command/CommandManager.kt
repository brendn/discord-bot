package me.brendn.discord.command

import me.brendn.discord.command.commands.Fun
import me.brendn.discord.command.commands.Math
import me.brendn.jdakt.message
import net.dv8tion.jda.core.MessageHistory
import net.dv8tion.jda.core.Permission
import net.dv8tion.jda.core.events.message.MessageReceivedEvent

/**
 * Used to handle command loading and processing.
 *
 * @since 6:47 PM on 12/14/2016
 */
object CommandManager {

	/**
	 * The alias used to identify commands in the chat.
	 */
	const val COMMAND_ALIAS = "!"

	/**
	 * The registered commands.
	 */
	var commands: ArrayList<Command> = arrayListOf()

	init {
		Math()
		Fun()

		registerCommand("help", "Lists commands") { _, _, _ ->
			var out = "Here are all of the commands:"
			for (c in commands) out += "\n- ${c.name}: ${c.help}"
			out
		}
		registerCommand("clear", "Clears chat history.") { event, args, _ ->
			if (event.member.hasPermission(event.textChannel, Permission.MANAGE_CHANNEL)) {
				val amount = 0
				if (args.isNotEmpty()) {
					//TODO
				}
				val history: MessageHistory = event.textChannel.history
				val messages = history.retrievePast(100).block()
				event.textChannel.deleteMessages(messages).queue()
				"Cleared history!"
			} else {
				"You ain't got permission for that!" //i actually haven't checked if this works or not...
			}
		}
	}

	/**
	 * Called every time a message is read, checks if it starts with [COMMAND_ALIAS] and attempts to perform
	 * the specified command.
	 */
	fun processCommand(message: String, event: MessageReceivedEvent) {
		val hasCommand = message.startsWith(COMMAND_ALIAS)
		if (hasCommand) {
			val msg = message.substring(1, message.length)
			val args: List<String> = msg.split(" ")
			val commandName = args[0]
			try {
				getCommand(commandName)?.process(event, args.subList(1, args.size), msg)
			} catch (e: Exception) {
				e.printStackTrace()
			}
		}
	}

	/**
	 * @return a [Command] from [commands] that is named [name]
	 */
	fun getCommand(name: String) : Command? {
		commands.filter { it.name.toLowerCase() == name.toLowerCase() }
				.forEach { return it }
		return null
	}

	/**
	 * Creates a [Command] with the given [name] and [help] values and adds it to the [commands] list.
	 *
	 * @param[name] The name of the command, which will be used to call it
	 * @param[help] Help for the command, such as an example of usage or description.
	 * @param [process] is a lambda that is called whenever the [Command] is invoked, it returns the String that
	 * will be output into the channel.
	 */
	inline fun registerCommand(name: String, help: String,
							   crossinline process: (event: MessageReceivedEvent,
													 args: List<String>,
													 message: String) -> String) {
		commands.add(object : Command() {
			override val name = name
			override val help = help
			override fun process(event: MessageReceivedEvent, args: List<String>, message: String) {
				event.channel.message(process(event, args, message))
			}
		})
	}
}