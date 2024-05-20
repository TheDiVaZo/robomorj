package org.morj.bot.robomorj.core;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.morj.bot.robomorj.core.discord.DiscordBot;
import org.morj.bot.robomorj.core.config.MainConfigContainer;
import org.morj.bot.robomorj.core.plugin.Plugin;
import org.morj.bot.robomorj.core.util.DatabaseUtil;

import java.lang.module.Configuration;
import java.lang.module.ModuleDescriptor;
import java.lang.module.ModuleFinder;
import java.lang.module.ModuleReference;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * @author TheDiVaZo
 * created on 01.05.2024
 */
@Getter
@Log4j2
public class RoboMorj {

    private MainConfigContainer mainConfigContainer;
    private DiscordBot discordBot;

    private DatabaseUtil databaseUtil;
    private final Path dataFolder;

    public RoboMorj(String dataFolder) {
        this.dataFolder = Path.of(dataFolder).toAbsolutePath();
        initConfigContainer(dataFolder);
        initDatabaseUtil();
        initDiscordBot();
    }

    private void initConfigContainer(String configFolder) {
        this.mainConfigContainer = new MainConfigContainer(Paths.get(configFolder).toAbsolutePath());
    }

    private void initDiscordBot() {
        try {
            this.discordBot = new DiscordBot(
                    mainConfigContainer.getMainConfig().discordBotConfig.botToken,
                    this.getMainConfigContainer()
            );
        } catch (Exception e) {
            log.error("Configuration problem. Please check it for accuracy", e);
        }
    }

    private void initDatabaseUtil() {
        this.databaseUtil = new DatabaseUtil(dataFolder.resolve("hikari").toString());
    }

    private void initPlugins() {
        Path pluginsDir = Paths.get("plugins");

        // Будем искать плагины в папке plugins
        ModuleFinder pluginsFinder = ModuleFinder.of(pluginsDir);

        // Пусть ModuleFinder найдёт все модули в папке plugins и вернёт нам список их имён
        List<String> plugins = pluginsFinder
                .findAll()
                .stream()
                .map(ModuleReference::descriptor)
                .map(ModuleDescriptor::name)
                .toList();

        // Создадим конфигурацию, которая выполнит резолюцию указанных модулей (проверит корректность графа зависимостей)
        Configuration pluginsConfiguration = ModuleLayer
                .boot()
                .configuration()
                .resolve(pluginsFinder, ModuleFinder.of(), plugins);

        // Создадим слой модулей для плагинов
        ModuleLayer layer = ModuleLayer
                .boot()
                .defineModulesWithOneLoader(pluginsConfiguration, ClassLoader.getSystemClassLoader());

        // Найдём все реализации сервиса IPlugin в слое плагинов и в слое Boot
        List<Plugin> services = Plugin.getServices(layer);
        for (Plugin service : services) {
            service.onEnable(this);
        }
    }

    public void reloadConfigs() {
        this.mainConfigContainer.reload();
    }

    @Getter
    private static RoboMorj roboMorj;

    public static void start(String dataFolder) {
        if (roboMorj == null) roboMorj = new RoboMorj(dataFolder);
        roboMorj.initPlugins();
    }

    public static void main(String[] args) {
        RoboMorj.start("");
    }
}
