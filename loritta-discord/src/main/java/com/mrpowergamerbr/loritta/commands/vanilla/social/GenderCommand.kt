package com.mrpowergamerbr.loritta.commands.vanilla.social

import com.mrpowergamerbr.loritta.commands.AbstractCommand
import com.mrpowergamerbr.loritta.commands.CommandContext
import com.mrpowergamerbr.loritta.utils.extensions.isEmote
import com.mrpowergamerbr.loritta.utils.locale.Gender
import com.mrpowergamerbr.loritta.utils.locale.BaseLocale
import com.mrpowergamerbr.loritta.utils.loritta
import com.mrpowergamerbr.loritta.utils.onReactionAddByAuthor
import net.dv8tion.jda.api.EmbedBuilder
import net.perfectdreams.loritta.api.commands.CommandCategory
import net.perfectdreams.loritta.api.messages.LorittaReply

class GenderCommand : AbstractCommand("gender", listOf("gênero", "genero"), CommandCategory.SOCIAL) {
    override fun getDescription(locale: BaseLocale): String {
        return locale["commands.social.gender.description"]
    }

    override suspend fun run(context: CommandContext, locale: BaseLocale) {
        val embed = EmbedBuilder()
                .setTitle(locale["commands.social.gender.whatAreYou"])
                .setDescription(locale["commands.social.gender.whyShouldYouSelect"])
                .build()


        val message = context.sendMessage(embed)

        message.addReaction("male:384048518853296128").queue()
        message.addReaction("female:384048518337265665").queue()
        message.addReaction("❓").queue()

        message.onReactionAddByAuthor(context) {
            message.delete().queue()

            if (it.reactionEmote.id == "384048518853296128") {
                loritta.newSuspendedTransaction {
                    context.lorittaUser.profile.settings.gender = Gender.MALE
                }

                context.reply(
						LorittaReply(
								locale["commands.social.gender.successfullyChanged"],
								"\uD83C\uDF89"
						)
				)
            }


            if (it.reactionEmote.id == "384048518337265665") {
                loritta.newSuspendedTransaction {
                    context.lorittaUser.profile.settings.gender = Gender.FEMALE
                }

                context.reply(
						LorittaReply(
								locale["commands.social.gender.successfullyChanged"],
								"\uD83C\uDF89"
						)
				)
            }

            if (it.reactionEmote.isEmote("❓")) {
                loritta.newSuspendedTransaction {
                    context.lorittaUser.profile.settings.gender = Gender.UNKNOWN
                }

                context.reply(
						LorittaReply(
								locale["commands.social.gender.successfullyChanged"],
								"\uD83C\uDF89"
						)
				)
            }
        }
    }
}