package org.morj.bot.robomorj.util;

import com.j256.ormlite.db.DatabaseType;
import com.j256.ormlite.jdbc.db.DatabaseTypeUtils;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.morj.bot.robomorj.config.core.ConfigHolder;
import org.morj.bot.robomorj.database.engine.DelegateHikariDataSource;
import org.morj.bot.robomorj.database.engine.credentials.DatabaseCredentials;
import org.morj.bot.robomorj.database.engine.hikari.HikariFileConfig;
import org.morj.bot.robomorj.database.engine.hikari.LibConfig;


import java.io.File;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author TheDiVaZo
 * created on 08.05.2024
 */
public class DatabaseUtil {
    private LibConfig libConfig;
    private HikariFileConfig hikariConfig;
    private Map<DatabaseCredentials, DelegateHikariDataSource> hikariPools = new ConcurrentHashMap();

    public DatabaseUtil(String dataFolder) {
        this.libConfig = this.loadOrCreateConfig(new LibConfig(new File(dataFolder, "config.yml")));
        File hikariPath = new File(dataFolder, this.libConfig.pathToHikari);
        this.hikariConfig = loadOrCreateConfig(new HikariFileConfig(hikariPath));
    }

    private <T extends ConfigHolder<T>> T loadOrCreateConfig(T config) {
        return config.getBaseFilePath().exists() ? config.load(false) : config.loadAndSave();
    }

    public DelegateHikariDataSource getHikariPool(String url, String username, String password) {
        DatabaseCredentials credentials = new DatabaseCredentials(url, username, password);
        DelegateHikariDataSource src = hikariPools.get(credentials);
        if (src == null) {
            DatabaseType databaseType = DatabaseTypeUtils.createDatabaseType(url);
            databaseType.loadDriver();
            HikariConfig config = hikariConfig.toHikari();
            config.setJdbcUrl(url);
            config.setUsername(username);
            config.setPassword(password);
            src = new DelegateHikariDataSource(new HikariDataSource(config));
            hikariPools.put(credentials, src);
        }

        return src;
    }
}
