package me.brendn.discord.command

import net.dv8tion.jda.core.events.message.MessageReceivedEvent

abstract class Command {

	/**
	 * The name of the [Command]
	 */
	abstract val name: String

	/**
	 * Information such as usage or a description of the [Command]
	 */
	abstract val help: String

	/**
	 * Processes the [Command], performed every time it is called.
	 */
	abstract fun process(event: MessageReceivedEvent, args: List<String>, message: String)
}

data class CommandData(val event: MessageReceivedEvent, val args: List<String>, val message: String)