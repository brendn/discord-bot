@file:JvmName("DiscordBot")
package me.brendn.discord

import me.brendn.discord.command.CommandManager
import me.brendn.discord.listener.MessageListener
import net.dv8tion.jda.core.AccountType
import net.dv8tion.jda.core.JDABuilder

/**
 * @since 6:46 PM on 12/14/2016
 */
public class DiscordBot {

	companion object {

		val commandManager = CommandManager

		@JvmStatic public fun main(args: Array<String>) {
			val jda = JDABuilder(AccountType.BOT).setToken(args[0]).buildBlocking()
			jda.addEventListener(MessageListener())
		}
	}
}