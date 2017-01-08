package me.brendn.jdakt

import net.dv8tion.jda.core.events.message.MessageReceivedEvent

/**
 * Extension functions for various events.
 *
 * Time: 5:43 PM
 * Date: 12/15/2016
 */

val MessageReceivedEvent.authorName: String get() = member.effectiveName
val MessageReceivedEvent.messageId: String get() = message.id

fun MessageReceivedEvent.print(message: String, showBalloon: Boolean = true) =
		channel.message("${if (showBalloon) ":speech_balloon: " else ""}$message")