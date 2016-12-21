package me.brendn.discord.command.commands

import me.brendn.discord.command.CommandManager.command
import me.brendn.discord.command.commands.undo.Undo.Companion.undo
import me.brendn.discord.ext.hasQuotes
import me.brendn.discord.ext.quoted
import me.brendn.discord.ext.startAt
import me.brendn.jdakt.print
import net.dv8tion.jda.core.entities.Guild
import net.dv8tion.jda.core.entities.Member
import net.dv8tion.jda.core.managers.GuildController

/**
 * Overcomplicated nickname command.
 *
 * @since 10:10 PM on 12/19/2016
 */
class Nick {

	init {
		command("nick", "Sets the nickname of the specified user.") { (event, _, message) ->
			try {
				val member = event.guild.parseName(message)
				val nick = getNick(message)
				val name = member!!.effectiveName
				event.guild.controller.setNick(name, nick)
				event.print("Set nickname of *$name* to *$nick*")
			} catch (e: Exception) {
				e.printStackTrace()
				event.print("""Usage: !nick "**name of user**" **nickname** """)
			}
		}
	}

	fun GuildController.setNick(name: String, nick: String) {
		val member = guild.getMembersByNickname(name, true)[0]
		setNickname(member, nick).queue()
		undo { (event) ->
			event.guild.controller.setNick(nick, name)
			event.print("Set $nick's nickname back to $name")
		}
	}

	fun getNick(input: String) : String {
		if (input.hasQuotes()) return input.substringAfter(input.quoted()[1]).startAt(2)
		else return input.substringAfter(input.split(" ")[1]).startAt(1)
	}

	fun Guild.parseName(input: String) : Member? {
		if (input.hasQuotes()) return assumeName(input.quoted()[1]) ?: getMembersByNickname(input.quoted()[1], true)[0]
		else {
			val name = input.split(" ")[1]; return assumeName(name) ?: getMembersByNickname(name, true)[0]
		}
	}

	fun Guild.assumeName(input: String) : Member? = members.firstOrNull { it.nickname.startsWith(input, true) }
}