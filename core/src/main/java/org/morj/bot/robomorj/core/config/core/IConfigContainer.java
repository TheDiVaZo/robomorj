package org.morj.bot.robomorj.core.config.core;

import org.morj.bot.robomorj.core.util.IClassContainer;

/**
 * @author TheDiVaZo
 * created on 01.05.2024
 */
public interface IConfigContainer extends IClassContainer<ConfigHolder<?>> {
    default void reload() {
        this.getContainer().values().forEach(ConfigHolder::reload);
    }
}
