package me.brendn.discord.command.commands

import me.brendn.discord.command.CommandManager.command
import me.brendn.jdakt.print
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

/**
 * Fun commands.
 *
 * Time: 8:05 PM
 * Date: 12/14/2016
 */
class Fun {

	init { for (type in FunType.values()) funCommand(type) }

	fun funCommand(type: FunType) = command(type.title, type.description) { (event) -> event.print(type.getResponse()) }

	enum class FunType(val title: String, val description: String, val webURL: String, val element: String) {
		INSULT_GENERATOR("insult", "Give yourself some love.", "http://www.insultgenerator.org", "wrap") {
			override fun getResponse(): String = getDoc().getElementsByClass(element).text()
		},
		SHAKESPEARE("raven", "Nevermore!", "http://www.pangloss.com/seidel/Shaker/", "font"),
		LUTHERAN("luther", "Martin Luther laying down some :fire: :fire:", "http://ergofabulous.org/luther/", "p");

		fun getDoc(): Document = Jsoup.connect(webURL).get()

		open fun getResponse() : String = getDoc().getElementsByTag(element).first().text().trim()
	}
}