package org.morj.bot.robomorj.config;

import org.morj.bot.robomorj.config.core.RawConfigContainer;

import java.io.File;
import java.nio.file.Path;

/**
 * @author TheDiVaZo
 * created on 01.05.2024
 */
public class ConfigContainer extends RawConfigContainer {

    public ConfigContainer(Path path) {
        super(path.toFile());
        loadConfig(new MainConfig(toFile("config.yml")));
        loadConfig(new OfferConfig(toFile("offer.yml")));
    }

    public MainConfig getMainConfig() {
        return get(MainConfig.class);
    }

    public OfferConfig getOfferConfig() {
        return get(OfferConfig.class);
    }
}
