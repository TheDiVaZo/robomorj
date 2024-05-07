package org.morj.bot.robomorj.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.dv8tion.jda.api.Permission;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.incendo.cloud.context.CommandContext;
import org.incendo.cloud.description.Description;
import org.incendo.cloud.discord.jda5.JDA5CommandManager;
import org.incendo.cloud.discord.jda5.JDAInteraction;
import org.incendo.cloud.discord.jda5.ReplySetting;
import org.incendo.cloud.discord.slash.CommandScope;
import org.incendo.cloud.discord.slash.DiscordPermission;
import org.incendo.cloud.parser.standard.LongParser;
import org.incendo.cloud.parser.standard.StringParser;

/**
 * @author TheDiVaZo
 * created on 02.05.2024
 */
@AllArgsConstructor
public class PingDiscordCommand implements DiscordCommand {
    @Getter
    private final JDA5CommandManager<JDAInteraction> commandManager;

    @Override
    public void registerCommand() {
        commandManager.command(
                commandManager.commandBuilder("ping")
                        .apply(ReplySetting.defer(true))
                        .apply(CommandScope.guilds())
                        .handler(this)
        );
    }

    @Override
    public void execute(@NonNull CommandContext<JDAInteraction> commandContext) {
        commandContext.sender().interactionEvent().getHook().sendMessage("Я люблю когда волосатые мужики обмазываются маслом").queue();
    }
}
