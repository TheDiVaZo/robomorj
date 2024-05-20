package org.morj.bot.robomorj.core.config;

import org.morj.bot.robomorj.core.config.core.RawConfigContainer;

import java.nio.file.Path;

/**
 * @author TheDiVaZo
 * created on 01.05.2024
 */
public class MainConfigContainer extends RawConfigContainer {

    public MainConfigContainer(Path path) {
        super(path.toFile());
        loadConfig(new MainConfig(toFile("config.yml")));
    }

    public MainConfig getMainConfig() {
        return get(MainConfig.class);
    }
}
