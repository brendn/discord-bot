package me.brendn.discord.command.commands

import me.brendn.discord.command.CommandManager.command
import me.brendn.discord.command.commands.undo.Undo.Companion.setUndo
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
		setUndo { (event) ->
			event.guild.controller.setNick(name, nick)
			event.print("Set $name's nickname back to $nick")
		}
	}

	fun hasQuotes(input: String) = quoted(input).size > 1
	fun quoted(input: String) = input.split("\"")

	fun getNick(input: String) : String {
		if (hasQuotes(input)) return input.substringAfter(quoted(input)[1]).startAt(2)
		else return input.substringAfter(input.split(" ")[1]).startAt(1)
	}

	private fun String.startAt(index: Int) : String {
		return this.substring(index, length)
	}

	fun Guild.parseName(input: String) : Member? {
		if (hasQuotes(input)) return assumeName(quoted(input)[1]) ?: getMembersByNickname(quoted(input)[1], true)[0]
		else {
			val name = input.split(" ")[1]; return assumeName(name) ?: getMembersByNickname(name, true)[0]
		}
	}

	fun Guild.assumeName(input: String) : Member? = members.firstOrNull { it.nickname.startsWith(input, true) }
}