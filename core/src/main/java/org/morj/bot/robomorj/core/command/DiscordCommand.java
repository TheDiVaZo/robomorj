package org.morj.bot.robomorj.core.command;

import org.incendo.cloud.discord.jda5.JDA5CommandManager;
import org.incendo.cloud.discord.jda5.JDAInteraction;

/**
 * @author TheDiVaZo
 * created on 02.05.2024
 */
public interface DiscordCommand extends ICommand<JDAInteraction, JDA5CommandManager<JDAInteraction>> {
}
