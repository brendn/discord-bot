package me.brendn.discord.command.commands

import com.udojava.evalex.Expression
import me.brendn.discord.command.CommandManager.registerCommand

/**
 * Math-related commands.
 *
 * @since 8:02 PM on 12/14/2016
 */
class Math {

	init {
		registerCommand("calc", "Calculates the given expression.") { event, args, message ->
			fun getResponse(error: String) : String {
				val unknownOp = ": "
				if (error.lastIndexOf(unknownOp) != -1) {
					val input = error.substring(error.lastIndexOf(unknownOp) + 1, error.length)
					return "$input? Are you kidding me?"
				}
				return error
			}

			if (args.isEmpty()) {
				"Maybe if you gave me some numbers I'd be able to do something."
			} else {
				try {
					val inputExpression = message.substring(4, message.length)
					"`$inputExpression = ${Expression(inputExpression).eval().toFloat()}.`"
				} catch (e: Exception) {
					val error = e.localizedMessage
					if (error.isNullOrBlank()) "Do you know how math works?" else getResponse(error)
				}
			}
		}
	}
}