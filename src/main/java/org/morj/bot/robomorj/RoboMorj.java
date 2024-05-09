package org.morj.bot.robomorj;

import com.j256.ormlite.jdbc.db.H2DatabaseType;
import com.j256.ormlite.jdbc.db.MysqlDatabaseType;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.morj.bot.robomorj.discord.DiscordBot;
import org.morj.bot.robomorj.config.ConfigContainer;
import org.morj.bot.robomorj.database.DatabaseContainer;
import org.morj.bot.robomorj.util.DatabaseUtil;

import java.nio.file.Path;
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

    private DatabaseUtil databaseUtil;
    private DatabaseContainer databaseContainer;
    private final Path dataFolder;

    public RoboMorj(String dataFolder) {
        this.dataFolder = Path.of(dataFolder).toAbsolutePath();
        initConfigContainer(dataFolder);
        initDatabase();
        initDiscordBot();
    }

    private void initConfigContainer(String configFolder) {
        this.configContainer = new ConfigContainer(Paths.get(configFolder).toAbsolutePath());
    }

    private void initDiscordBot() {
        try {
            this.discordBot = new DiscordBot(
                    configContainer.getMainConfig().discordBotConfig.botToken,
                    configContainer.getMainConfig().discordBotConfig.guildId,
                    this
            );
        } catch (Exception e) {
            log.error("Configuration problem. Please check it for accuracy", e);
        }
    }

    private void initDatabase() {
        this.databaseUtil = new DatabaseUtil(dataFolder.resolve("hikari").toString());
        this.databaseContainer = new DatabaseContainer(
                configContainer.getMainConfig().database.isMySQL() ? new MysqlDatabaseType(): new H2DatabaseType(),
                ()-> databaseUtil.getHikariPool(configContainer.getMainConfig().database.asCredentials(dataFolder.resolve("data").toString())));
    }

    public void reloadConfigs() {
        this.configContainer.reload();
    }
}
