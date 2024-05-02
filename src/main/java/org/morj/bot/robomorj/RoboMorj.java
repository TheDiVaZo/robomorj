package org.morj.bot.robomorj;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogBuilder;
import org.apache.logging.log4j.Logger;
import org.incendo.cloud.discord.jda5.JDA5CommandManager;
import org.incendo.cloud.discord.jda5.JDAInteraction;
import org.incendo.cloud.execution.ExecutionCoordinator;
import org.morj.bot.robomorj.bot.DiscordBot;
import org.morj.bot.robomorj.bot.ForumBot;
import org.morj.bot.robomorj.config.ConfigContainer;

import java.nio.file.Paths;

/**
 * @author TheDiVaZo
 * created on 01.05.2024
 */
@Getter
@Log4j2
public class RoboMorj {

    private ConfigContainer configContainer;
    private DiscordBot discordBot;
    private ForumBot forumBot;

    public RoboMorj(String configFolder) {
        initConfigContainer(configFolder);
        try {
            initForumBot();
            initDiscordBot();
        } catch (Exception e) {
            log.error("Config Error. Please, check the config");
        }
    }

    private void initConfigContainer(String configFolder) {
        this.configContainer = new ConfigContainer(Paths.get(configFolder).toAbsolutePath());
    }

    private void initDiscordBot() throws InterruptedException {
        this.discordBot = new DiscordBot(
                configContainer.getMainConfig().discordBotConfig.botToken,
                configContainer.getMainConfig().discordBotConfig.guildId
        );
    }

    private void initForumBot() {
        this.forumBot = new ForumBot(
                configContainer.getMainConfig().forumBotConfig.botToken,
                configContainer.getMainConfig().forumBotConfig.linkToForum,
                configContainer.getMainConfig().forumBotConfig.authUser,
                configContainer.getMainConfig().forumBotConfig.authPass
        );
    }

    public void reloadConfigs() {
        this.configContainer.reload();
    }
}
