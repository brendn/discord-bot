package me.brendn.discord.command.commands

import me.brendn.discord.command.CommandManager.registerCommand
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

/**
 * Fun commands.
 *
 * @since 8:05 PM on 12/14/2016
 */
class Fun {

	init {
		for (type in FunType.values()) funCommand(type)
	}

	fun funCommand(type: FunType) = registerCommand(type.title, type.description) { _, _, _ -> type.getResponse() }

	enum class FunType(val title: String, val description: String, val webURL: String, val element: String) {

		INSULT_GENERATOR("insult", "Give yourself some love.", "http://www.insultgenerator.org", "wrap") {
			override fun getResponse(): String = getDoc().getElementsByClass(element).text()
		},
		SHAKESPEARE("raven", "Nevermore!", "http://www.pangloss.com/seidel/Shaker/", "font"),
		LUTHERAN("luther", "Martin Luther laying down some :fire: :fire:", "http://ergofabulous.org/luther/", "p");

		fun getDoc(): Document = Jsoup.connect(webURL).get()

		/**
		 * The response, or output for this
		 */
		open fun getResponse() : String = getDoc().getElementsByTag(element).first().text().trim()
	}
}