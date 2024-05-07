package org.morj.bot.robomorj.bot;

import lombok.Getter;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;
import org.incendo.cloud.discord.jda5.JDA5CommandManager;
import org.incendo.cloud.discord.jda5.JDAInteraction;
import org.incendo.cloud.execution.ExecutionCoordinator;
import org.morj.bot.robomorj.RoboMorj;
import org.morj.bot.robomorj.command.PingDiscordCommand;

/**
 * @author TheDiVaZo
 * created on 02.05.2024
 */
@Getter
public class DiscordBot {
    private ShardManager shardManager;
    private Guild guild;
    private JDA5CommandManager<JDAInteraction> discordCommandManager;

    private RoboMorj roboMorj;

    public DiscordBot(String token, long guildId, RoboMorj roboMorj) throws InterruptedException {
        this.roboMorj = roboMorj;
        initCommandManager();

        this.shardManager = DefaultShardManagerBuilder.createDefault(token)
                .setStatus(OnlineStatus.ONLINE)
                .setActivity(Activity.watching("Робоморж"))
                .addEventListeners(discordCommandManager.createListener())
                .build();
//        this.jda = JDABuilder.createDefault(token)
//                .enableIntents(EnumSet.allOf(GatewayIntent.class))
//                .enableCache(EnumSet.allOf(CacheFlag.class))
//                .setMemberCachePolicy(MemberCachePolicy.ALL)
//                .addEventListeners(
//                        discordCommandManager.createListener()
//                )
//                .build();
        this.guild = shardManager.getGuildById(guildId);
    }

    private void initCommandManager() {
        this.discordCommandManager = new JDA5CommandManager<>(
                ExecutionCoordinator.asyncCoordinator(),
                JDAInteraction.InteractionMapper.identity());
        new PingDiscordCommand(discordCommandManager).registerCommand();
    }
}
