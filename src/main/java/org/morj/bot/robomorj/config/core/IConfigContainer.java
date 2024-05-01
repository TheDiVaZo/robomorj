package org.morj.bot.robomorj.config.core;

/**
 * @author TheDiVaZo
 * created on 01.05.2024
 */
public interface IConfigContainer extends IClassContainer<ConfigHolder<?>> {
    default void reload() {
        this.getContainer().values().forEach(ConfigHolder::reload);
    }
}
