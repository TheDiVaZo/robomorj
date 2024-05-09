package org.morj.bot.robomorj.config;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.morj.bot.robomorj.config.core.ConfigHolder;
import org.morj.bot.robomorj.database.engine.credentials.DatabaseCredentials;
import org.morj.bot.robomorj.database.engine.url.DatabaseTypeUrl;
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

    String guildConfigsFolder = "guilds";

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
    }

    @ConfigSerializable
    @Data
    public static class Database {
        boolean isMySQL = false;

        String h2FileName = "database";

        String host = "localhost";
        int port = 3310;
        String database = "testdb";

        String username = "user";
        String password = "qwerty123";

        boolean useParams = false;
        String params = "";

        public DatabaseCredentials asCredentials(String absolutePath) {
            return DatabaseCredentials.getCredentials(isMySQL ? DatabaseTypeUrl.MYSQL : DatabaseTypeUrl.H2, getUrl(absolutePath), username, password);
        }

        private String getUrl(String absolutePath) {
            if (isMySQL) {
                return host + ":" + port + "/" + database + (useParams ? "?" + params : "");
            } else {
                return absolutePath + File.separator + h2FileName;
            }
        }
    }
}
