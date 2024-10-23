package org.morj.bot.robomorj.core.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.incendo.cloud.context.CommandContext;
import org.incendo.cloud.discord.jda5.JDA5CommandManager;
import org.incendo.cloud.discord.jda5.JDAInteraction;
import org.incendo.cloud.discord.jda5.ReplySetting;
import org.incendo.cloud.discord.slash.CommandScope;

import java.util.Objects;

/**
 * @author TheDiVaZo
 * created on 02.05.2024
 */
@Getter
@AllArgsConstructor
public class PingDiscordCommand implements DiscordCommand {
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
        Objects.requireNonNull(commandContext.sender().interactionEvent()).getHook().sendMessage("Я люблю когда волосатые мужики обмазываются маслом").queue();
    }
}
