package org.morj.bot.robomorj;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageHistory;
import org.apache.log4j.LogManager;
import org.morj.bot.robomorj.bot.DiscordBot;
import org.morj.bot.robomorj.config.ConfigContainer;

import java.net.UnknownHostException;
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

    public RoboMorj(String configFolder) {

        initConfigContainer(configFolder);
        try {
            initDiscordBot();
        } catch (Exception e) {
            log.error("Configuration problem. Please check it for accuracy", e);
        }
    }

    private void initConfigContainer(String configFolder) {
        this.configContainer = new ConfigContainer(Paths.get(configFolder).toAbsolutePath());
    }

    private void initDiscordBot() throws InterruptedException {
        this.discordBot = new DiscordBot(
                configContainer.getMainConfig().discordBotConfig.botToken,
                configContainer.getMainConfig().discordBotConfig.guildId,
                this
        );
    }

    public void reloadConfigs() {
        this.configContainer.reload();
    }
}
