package me.brendn.discord.command.commands.undo

import me.brendn.discord.command.CommandData
import me.brendn.discord.command.CommandManager.command
import me.brendn.jdakt.print

/**
 * @since 10:29 PM on 12/19/2016
 */
class Undo {

	companion object {
		var currentAction: UndoAction? = null

		inline fun setUndo(crossinline process: (data: CommandData) -> Unit) {
			currentAction = (object : UndoAction {
				override fun dispatch(data: CommandData) = process(data)
			})
		}
	}

	init {
		command("undo", "Reverts the last command (if applicable)") { data -> currentAction?.dispatch(data) }
	}
}

interface UndoAction {
	fun dispatch(data: CommandData) {
		data.event.print("No undo action available!")
	}
}