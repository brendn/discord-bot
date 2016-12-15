package me.brendn.discord.command.commands

import me.brendn.discord.command.CommandManager.registerCommand

/**
 * Math-related commands.
 *
 * @since 8:02 PM on 12/14/2016
 */
class Math {

	init {
		registerCommand("add", "Adds the specified numbers.") { _, args, _ ->
			if (args.isEmpty()) {
				"Maybe if you gave me some numbers I'd be able to do something."
			} else {
				val numbers = removeInvalid(args)
				val hadNoNumbers = numbers.isEmpty()
				val rudeResponse = if (hadNoNumbers) "\nHow about you use numbers next time?  Is that so hard?" else ""
				"Those numbers add up to ${numbers.sum()}. $rudeResponse"
			}
		}
	}

	/**
	 * Converts the [list] to an [IntArray], removing anything that isn't an [Int]
	 */
	fun removeInvalid(list: List<String>): IntArray {
		val out: ArrayList<Int> = arrayListOf()
		for (s in list) { try { out.add(s.toInt()) } catch (e: NumberFormatException) {}}
		return out.toIntArray()
	}
}