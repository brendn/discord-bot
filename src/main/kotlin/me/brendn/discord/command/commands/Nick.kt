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
 * The Nick command will set the nickname (nick) of the specified user to whatever you want.
 * ---
 * The username of the target can be specified within quotes (useful for users with names that are not one word) or
 * without quotes with a portion of the name or the full name.
 * ---
 * Written on 12/19/2016 at 10:10PM
 * @author brendn
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

	/**
	 * Sets the nickname of the [user] (by name) to the [nick].
	 */
	fun GuildController.setNick(user: String, nick: String) {
		val member = guild.getMembersByNickname(user, true)[0]
		setNickname(member, nick).queue()
		undo { (event) ->
			event.guild.controller.setNick(nick, user)
			event.print("Set $nick's nickname back to $user")
		}
	}

	/**
	 * Parses the nickname for the user from the given [input].
	 * ---
	 * If the [input] has quotes, it will return the quoted portion of the message.  Otherwise, it will just get the
	 * second word in the [input] message.
	 */
	fun getNick(input: String) : String {
		if (input.hasQuotes()) return input.substringAfter(input.quoted()[1]).startAt(2)
		else return input.substringAfter(input.split(" ")[1]).startAt(1)
	}

	/**
	 * Parses the name of the target user from the given [input].
	 */
	fun Guild.parseName(input: String) : Member? {
		if (input.hasQuotes()) return assumeName(input.quoted()[1]) ?: getMembersByNickname(input.quoted()[1], true)[0]
		else {
			val name = input.split(" ")[1]; return assumeName(name) ?: getMembersByNickname(name, true)[0]
		}
	}

	/**
	 * Loops through the members and returns the first one that has a name starting with the specified [input].
	 * ---
	 * This isn't super accurate, since many members could have names starting with the specified [input].
	 */
	fun Guild.assumeName(input: String) : Member? = members.firstOrNull { it.nickname.startsWith(input, true) }
}