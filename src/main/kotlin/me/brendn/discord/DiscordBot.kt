@file:JvmName("DiscordBot")
package me.brendn.discord

import me.brendn.discord.command.CommandManager
import me.brendn.discord.listener.EventListener
import net.dv8tion.jda.core.AccountType
import net.dv8tion.jda.core.JDABuilder

/**
 * Time: 6:46 PM
 * Date: 12/14/2016
 */
public class DiscordBot {

	companion object {

		val commandManager = CommandManager

		@JvmStatic public fun main(args: Array<String>) {
			val jda = JDABuilder(AccountType.BOT).setToken(args[0]).buildBlocking()
			jda.addEventListener(EventListener())
		}
	}
}