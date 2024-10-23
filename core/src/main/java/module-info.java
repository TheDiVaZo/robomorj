import org.morj.bot.robomorj.core.plugin.BasePlugin;
import org.morj.bot.robomorj.core.plugin.Plugin;

module core {
    requires static lombok;
    requires cloud.core;
    requires cloud.jda5;
    requires net.dv8tion.jda;
    requires org.apache.commons.collections4;
    requires ormlite.jdbc;
    requires java.sql;
    requires org.spongepowered.configurate;
    requires kotlin.stdlib;
    requires log4j;
    requires org.apache.logging.log4j.core;
    requires org.spongepowered.configurate.yaml;
    requires annotations;
    requires HikariCP;
    requires org.jooq.jool;
    requires cloud.discord.common;
    requires org.checkerframework.checker.qual;
    exports org.morj.bot.robomorj.core;
    exports org.morj.bot.robomorj.core.plugin;
    exports org.morj.bot.robomorj.core.discord;
    exports org.morj.bot.robomorj.core.database.engine;
    exports org.morj.bot.robomorj.core.config;
    exports org.morj.bot.robomorj.core.command;
    exports org.morj.bot.robomorj.core.util;

    uses Plugin;
    provides Plugin with BasePlugin;
}