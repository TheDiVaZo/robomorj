package org.morj.bot.robomorj.bot;

import lombok.Getter;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import org.incendo.cloud.discord.jda5.JDA5CommandManager;
import org.incendo.cloud.discord.jda5.JDAInteraction;
import org.incendo.cloud.execution.ExecutionCoordinator;
import org.morj.bot.robomorj.command.TestDiscordCommand;

import java.util.EnumSet;

/**
 * @author TheDiVaZo
 * created on 02.05.2024
 */
@Getter
public class DiscordBot {
    private JDA jda;
    private Guild guild;
    private JDA5CommandManager<JDAInteraction> discordCommandManager;

    public DiscordBot(String token, long guildId) throws InterruptedException {
        initCommandManager();
        this.jda = JDABuilder.createDefault(token)
                .enableIntents(EnumSet.allOf(GatewayIntent.class))
                .enableCache(EnumSet.allOf(CacheFlag.class))
                .setMemberCachePolicy(MemberCachePolicy.ALL)
                .addEventListeners(
                        discordCommandManager.createListener()
                )
                .build().awaitReady();
        jda.getGuildById(guildId);
    }

    private void initCommandManager() {
        this.discordCommandManager = new JDA5CommandManager<>(
                ExecutionCoordinator.asyncCoordinator(),
                JDAInteraction.InteractionMapper.identity());
        new TestDiscordCommand(discordCommandManager).registerCommand();
    }
}
