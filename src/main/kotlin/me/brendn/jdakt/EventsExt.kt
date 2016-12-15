package me.brendn.jdakt

import net.dv8tion.jda.core.events.message.MessageReceivedEvent

/**
 * Extension functions for various events.
 *
 * @since 5:43 PM on 12/15/2016
 */

//fun MessageReceivedEvent.getPreviousMessage() : Message? {
//}

val MessageReceivedEvent.authorName: String get() = member.effectiveName
val MessageReceivedEvent.messageId: String get() = message.id

fun MessageReceivedEvent.print(message: String) = channel.message(message)