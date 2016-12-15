package me.brendn.jdakt
import net.dv8tion.jda.core.entities.MessageChannel

fun MessageChannel.message(message: String) = this.sendMessage(message).queue()