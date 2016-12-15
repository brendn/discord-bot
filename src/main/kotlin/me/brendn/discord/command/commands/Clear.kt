package me.brendn.discord.command.commands

import me.brendn.discord.command.CommandManager.registerCommand
import me.brendn.jdakt.deleteMessages
import me.brendn.jdakt.print
import net.dv8tion.jda.core.Permission

/**
 * @since 6:09 PM on 12/15/2016
 */
class Clear {

	init {
		registerCommand("clear", "Clears the given amount of messages.") { event, args, _ ->
			if (event.member.hasPermission(event.textChannel, Permission.MANAGE_CHANNEL)) {
				val amount = if (args.isEmpty()) 100 else getAmount(args) ?: -1
				if (amount <= 1 || amount > 100) {
					event.print("Input the specified amount of messages you want to clear (1..100)")
				} else {
					event.print("Deleting messages...")
					event.textChannel.deleteMessages(amount)
					event.print("Cleared messages!")
				}
			} else {
				event.print("You don't have permission to do this!")
			}
		}
	}

	fun getAmount(input: List<String>): Int? = try { input[0].toInt() + 1 } catch (e: NumberFormatException) { null }
}