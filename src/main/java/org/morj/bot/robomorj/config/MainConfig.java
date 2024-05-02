package org.morj.bot.robomorj.config;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.morj.bot.robomorj.config.core.ConfigHolder;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;

import java.io.File;
import java.net.URL;

/**
 * @author TheDiVaZo
 * created on 01.05.2024
 *
 * Класс представляемый основную конфигурацию
 */
@ConfigSerializable
@FieldDefaults(level = AccessLevel.PUBLIC)
public class MainConfig extends ConfigHolder<MainConfig> {

    public MainConfig(File baseFilePath) {
        super(baseFilePath);
    }

    public MainConfig() {
        this(null);
    }

    DiscordBotConfig discordBotConfig;

    ForumBotConfig forumBotConfig = new ForumBotConfig();

    @ConfigSerializable
    @AllArgsConstructor
    @NoArgsConstructor
    @FieldDefaults(level = AccessLevel.PUBLIC)
    public static class DiscordBotConfig {
        long guildId = 0;
        String botToken = "";
    }

    @ConfigSerializable
    @AllArgsConstructor
    @NoArgsConstructor
    @FieldDefaults(level = AccessLevel.PUBLIC)
    public static class ForumBotConfig {
        String botToken = "";
        String linkToForum = "https://google.com";
        String authUser = "user";
        String authPass = "password";
    }
}
