package org.morj.bot.robomorj;

import org.morj.bot.robomorj.config.ConfigContainer;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author TheDiVaZo
 * created on 01.05.2024
 */
public class RoboMorj {
    private ConfigContainer configContainer;

    public static void main(String[] args) {
        RoboMorj roboMorj = new RoboMorj();
        roboMorj.initConfigContainer();
    }

    private void initConfigContainer() {
        this.configContainer = new ConfigContainer(Paths.get("").toAbsolutePath());
    }

    public void reloadConfigs() {
        this.configContainer.reload();
    }
}
