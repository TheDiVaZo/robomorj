package org.morj.bot.robomorj.command;

import org.incendo.cloud.Command;
import org.incendo.cloud.CommandManager;
import org.incendo.cloud.execution.CommandExecutionHandler;

/**
 * @author TheDiVaZo
 * created on 02.05.2024
 */
public interface ICommand<T, C extends CommandManager<T>> extends CommandExecutionHandler<T> {
      C getCommandManager();
      void registerCommand();
}
