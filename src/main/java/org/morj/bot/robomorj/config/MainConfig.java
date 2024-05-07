package org.morj.bot.robomorj.config;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.morj.bot.robomorj.config.core.ConfigHolder;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;

import java.io.File;

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

    Database database = new Database();

    //ForumBotConfig forumBotConfig = new ForumBotConfig();

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

    @ConfigSerializable
    @Data
    public static class Database {
        String host = "localhost";
        int port = 3310;
        String database = "testdb";
        String username = "user";
        String password = "qwerty123";

        boolean useParams = false;
        String params = "";

        public DatabaseCredentials asCredentials() {
            return DatabaseCredentials.getCredentials(
                    DatabaseTypeUrl.MYSQL,
                    host + ":" + port + "/" + database +
                            (useParams ? "?" + params : ""),
                    username,
                    password);
        }
    }
}
