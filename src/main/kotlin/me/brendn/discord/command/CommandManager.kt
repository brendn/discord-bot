package me.brendn.discord.command

import me.brendn.discord.command.commands.Fun
import me.brendn.discord.command.commands.Math
import me.brendn.discord.command.commands.Nick
import me.brendn.discord.command.commands.undo.Undo
import me.brendn.jdakt.print
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
	const val COMMAND_PREFIX = "!"

	/**
	 * The registered commands.
	 */
	var commands: ArrayList<Command> = arrayListOf()

	init {
		Math()
		Fun()
		Nick()
		Undo()

		command("help", "Lists commands") { (event) ->
			var out = "Here are all of the commands:"
			for (c in commands) out += "\n**${c.name}**: ${c.help}"
			event.print(out, false)
		}
	}

	/**
	 * Called every time a message is read, checks if it starts with [COMMAND_PREFIX] and attempts to perform
	 * the specified command.
	 */
	fun processCommand(message: String, event: MessageReceivedEvent) {
		val hasCommand = message.startsWith(COMMAND_PREFIX)
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
	fun getCommand(name: String) : Command? = commands.firstOrNull { it.name.toLowerCase() == name.toLowerCase() }

	/**
	 * Creates a [Command] with the given [name] and [help] values and adds it to the [commands] list.
	 *
	 * @param[name] The name of the command, which will be used to call it
	 * @param[help] Help for the command, such as an example of usage or description.
	 * @param [process] is a lambda that is called whenever the [Command] is invoked, it returns the String that
	 * will be output into the channel.
	 */
	inline fun command(name: String, help: String,
					   crossinline process: (data: CommandSource) -> Unit) {
		commands.add(object : Command() {
			override val name = name
			override val help = help
			override fun process(event: MessageReceivedEvent, args: List<String>, message: String) {
				process(CommandSource(event, args, message))
			}
		})
	}
}