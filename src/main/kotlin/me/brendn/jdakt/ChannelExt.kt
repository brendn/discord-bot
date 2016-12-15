package me.brendn.jdakt
import net.dv8tion.jda.core.entities.MessageChannel
import net.dv8tion.jda.core.entities.TextChannel

fun MessageChannel.message(message: String) = this.sendMessage(message).queue()

/**
 * Deletes the given [amount] of messages from [TextChannel.getHistory]
 */
fun TextChannel.deleteMessages(amount: Int) {
	val messages = history.retrievePast(amount).block()
	deleteMessages(messages).block()
}