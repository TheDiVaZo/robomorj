package org.morj.bot.robomorj.core.plugin;

import lombok.extern.log4j.Log4j2;
import org.morj.bot.robomorj.core.RoboMorj;

public class BasePlugin extends Plugin {
    @Override
    public void onEnable(RoboMorj roboMorj) {
        getLogger().info("BasePlugin has been enabled");
    }

    @Override
    public void onDisable() {
        getLogger().info("BasePlugin has been disabled");
    }
}
