package me.brendn.discord.util

import net.dv8tion.jda.core.entities.MessageChannel

/**
 * Some helper functions to make life easier.
 *
 * @since 3:53 PM on 12/14/2016
 */

/**
 * Sends the specified [message]
 */
fun MessageChannel.message(message: String) = this.sendMessage(message).queue()