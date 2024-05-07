package org.morj.bot.robomorj.database.engine.hikari;

import org.morj.bot.robomorj.config.core.ConfigHolder;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;

import java.io.File;

/**
 * @author TheDiVaZo
 * created on 08.05.2024
 */
@ConfigSerializable
public class LibConfig extends ConfigHolder<LibConfig> {
    public String pathToHikari;

    public LibConfig(File baseFilePath) {
        super(baseFilePath);
        this.pathToHikari = "hikari.yml";
    }

    private LibConfig() {
        this((File)null);
    }
}
