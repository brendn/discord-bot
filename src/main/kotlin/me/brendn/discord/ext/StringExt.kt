package me.brendn.discord.ext

/**
 * String extension functions mainly for parsing input.
 *
 * @since 7:49 PM on 12/20/2016
 */


fun String.hasQuotes() = quoted().size > 1
fun String.quoted() = split("\"")
fun String.startAt(index: Int) = substring(index, length)