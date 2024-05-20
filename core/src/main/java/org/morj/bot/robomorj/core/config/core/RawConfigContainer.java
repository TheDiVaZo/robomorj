package org.morj.bot.robomorj.core.config.core;

import org.morj.bot.robomorj.core.util.ClassContainer;

import java.io.File;
import java.util.Arrays;

/**
 * @author TheDiVaZo
 * created on 01.05.2024
 */
public abstract class RawConfigContainer extends ClassContainer<ConfigHolder<?>> implements IConfigContainer {
    protected final File path;

    protected RawConfigContainer(File path) {
        this.path = path;
    }

    protected File toFile(String path) {
        return new File(this.path, path);
    }

    protected void loadConfig(ConfigHolder<?> configHolder) {
        this.container.put((Class<? extends ConfigHolder<?>>) configHolder.getClass(), configHolder.loadOrCreateConfig());
    }

    protected void loadConfigs(ConfigHolder<?>... configs) {
        Arrays.stream(configs).forEach(this::loadConfig);
    }
}
