package me.brendn.jdakt

import net.dv8tion.jda.core.events.message.MessageReceivedEvent

/**
 * Extension functions for various events.
 *
 * @since 5:43 PM on 12/15/2016
 */

fun MessageReceivedEvent.getPreviousMessage() {

}

fun MessageReceivedEvent.getName() = message.author.name
fun MessageReceivedEvent.getMessageId() = message.id
fun MessageReceivedEvent.print(message: String) = channel.message(message)