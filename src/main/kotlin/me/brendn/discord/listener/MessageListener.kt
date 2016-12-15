package me.brendn.discord.listener

import me.brendn.discord.DiscordBot.Companion.commandManager
import me.brendn.jdakt.*
import net.dv8tion.jda.core.events.message.MessageReceivedEvent
import net.dv8tion.jda.core.hooks.ListenerAdapter

/**
 * Implementation of [ListenerAdapter] used to read messages sent into the chat.
 *
 * @since 3:31 PM on 12/14/2016
 */
class MessageListener : ListenerAdapter() {

	/**
	 * Prints messages from chat into console as well as calls [processMessage] for each message.
	 *
	 * @see [ListenerAdapter.onMessageReceived]
	 */
	override fun onMessageReceived(event: MessageReceivedEvent) {
		val channel = event.channel
		val guildName = event.guild.name
		val channelName = channel.name
		val memberName = event.authorName
		val message = event.message.content
		println("[$guildName][$channelName] $memberName: $message")
		processMessage(message, event)
		
	}

	/**
	 * Ran every time a message is read, for now only calls [commandManager]
	 *
	 * @param[message] The message processed.
	 * @param[event] The [MessageReceivedEvent].
	 */
	fun processMessage(message: String, event: MessageReceivedEvent) = commandManager.processCommand(message, event)
}