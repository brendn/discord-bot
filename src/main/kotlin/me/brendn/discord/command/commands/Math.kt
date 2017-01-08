package me.brendn.discord.command.commands

import com.udojava.evalex.Expression
import me.brendn.discord.command.CommandManager.command
import me.brendn.jdakt.print

/**
 * Math-related commands.
 *
 * @since 8:02 PM on 12/14/2016
 */
class Math {

	init {
		command("calc", "Calculates the given expression.") { (event, args, message) ->
			if (args.isEmpty()) {
				event.print("Maybe if you gave me some numbers I'd be able to do something.")
			} else {
				try {
					val expression = message.substring(4, message.length)
					event.print("`$expression = ${Expression(expression).eval().toFloat()}`")
				} catch (e: Exception) {
					event.print(if (e.localizedMessage.isNullOrBlank()) "Do you know how math works?"
					else getResponse(e.localizedMessage))
				}
			}
		}
	}

	/**
	 * This is added to the end of the output message, just for some fun
	 */
	fun getResponse(error: String) : String {
		val unknownOp = ": "
		if (error.lastIndexOf(unknownOp) != -1) {
			val input = error.substring(error.lastIndexOf(unknownOp) + 1, error.length)
			return "$input? Are you kidding me?"
		}
		return error
	}
}