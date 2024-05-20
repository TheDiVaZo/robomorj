package org.morj.bot.robomorj.core.discord;

import lombok.Getter;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;
import org.incendo.cloud.discord.jda5.JDA5CommandManager;
import org.incendo.cloud.discord.jda5.JDAInteraction;
import org.incendo.cloud.execution.ExecutionCoordinator;
import org.morj.bot.robomorj.core.command.DiscordCommand;
import org.morj.bot.robomorj.core.config.MainConfigContainer;
import org.morj.bot.robomorj.core.config.MainConfig;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.stream.Collectors;

/**
 * @author TheDiVaZo
 * created on 02.05.2024
 */
@Getter
public class DiscordBot {
    private ShardManager shardManager;
    private JDA5CommandManager<JDAInteraction> discordCommandManager;

    public DiscordBot(String token, MainConfigContainer mainConfigContainer) {
        initCommandManager();
        MainConfig mainConfig = mainConfigContainer.getMainConfig();
        this.shardManager = DefaultShardManagerBuilder.createDefault(token)
                .setStatus(OnlineStatus.ONLINE)
                .setActivity(Activity.of(mainConfig.discordBotConfig.activityType, mainConfig.discordBotConfig.activityName))
                .addEventListeners(discordCommandManager.createListener())
                .build();
    }

    private void initCommandManager() {
        this.discordCommandManager = new JDA5CommandManager<>(
                ExecutionCoordinator.asyncCoordinator(),
                JDAInteraction.InteractionMapper.identity());
    }

    public void registerCommand(Function<JDA5CommandManager<JDAInteraction>, DiscordCommand> command) {
        command.apply(discordCommandManager).registerCommand();
    }

    public void addEventListener(Object... listeners) {
        getShardManager().addEventListener(listeners);
    }

    public void removeEventListener(Object... listeners) {
        getShardManager().removeEventListener(listeners);
    }

    public void addEventListeners(IntFunction<Object> eventListenerProvider) {
        getShardManager().addEventListeners(eventListenerProvider);
    }

    public void removeEventListeners(IntFunction<Collection<Object>> eventListenerProvider) {
        getShardManager().removeEventListeners(eventListenerProvider);
    }

    public void removeEventListenerProvider(IntFunction<Object> eventListenerProvider) {
        getShardManager().removeEventListenerProvider(eventListenerProvider);
    }
}
