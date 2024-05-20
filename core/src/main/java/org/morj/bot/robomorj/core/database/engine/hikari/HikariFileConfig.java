package org.morj.bot.robomorj.core.database.engine.hikari;

import com.zaxxer.hikari.HikariConfig;
import org.morj.bot.robomorj.core.config.core.ConfigHolder;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;

import java.io.File;
import java.util.concurrent.TimeUnit;

/**
 * @author TheDiVaZo
 * created on 08.05.2024
 */
@ConfigSerializable
public class HikariFileConfig extends ConfigHolder<HikariFileConfig> {
    private int minIdle;
    private int maxPoolSize;
    private long maxLifeTime;
    private long connectionTimeOut;
    private long validationTimeout;
    private long idleTimeOut;
    private boolean isAutoCommit;

    public HikariFileConfig(File baseFilePath) {
        super(baseFilePath);
        this.minIdle = 10;
        this.maxPoolSize = 10;
        this.maxLifeTime = TimeUnit.MINUTES.toMillis(30L);
        this.connectionTimeOut = TimeUnit.SECONDS.toMillis(30L);
        this.validationTimeout = TimeUnit.SECONDS.toMillis(5L);
        this.idleTimeOut = TimeUnit.MINUTES.toMillis(10L);
        this.isAutoCommit = true;
    }

    private HikariFileConfig() {
        this(null);
    }

    public HikariConfig toHikari() {
        HikariConfig config = new HikariConfig();
        config.setAutoCommit(this.isAutoCommit);
        config.setMinimumIdle(this.minIdle);
        config.setMaximumPoolSize(this.maxPoolSize);
        config.setMaxLifetime(this.maxLifeTime);
        config.setConnectionTimeout(this.connectionTimeOut);
        config.setValidationTimeout(this.validationTimeout);
        config.setIdleTimeout(this.idleTimeOut);
        return config;
    }
}
