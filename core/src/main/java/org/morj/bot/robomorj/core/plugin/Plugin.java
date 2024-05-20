package org.morj.bot.robomorj.core.plugin;

import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.Logger;
import org.morj.bot.robomorj.core.RoboMorj;

import java.util.List;
import java.util.ServiceLoader;
import java.util.stream.Collectors;

@Log4j2
public abstract class Plugin {

    public abstract void onEnable(RoboMorj roboMorj);
    public abstract void onDisable();

    Logger getLogger() {
        return log;
    }

    public static List<Plugin> getServices(ModuleLayer layer) {
        return ServiceLoader
                .load(layer, Plugin.class)
                .stream()
                .map(ServiceLoader.Provider::get)
                .collect(Collectors.toList());
    }

}
