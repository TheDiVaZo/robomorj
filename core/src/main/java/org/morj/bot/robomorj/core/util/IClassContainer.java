package org.morj.bot.robomorj.core.util;

import java.util.Map;

/**
 * @author TheDiVaZo
 * created on 01.05.2024
 */
public interface IClassContainer<T> {
    <O extends T> O get(Class<O> var1);

    <O extends T> boolean contains(Class<O> var1);

    Map<Class<? extends T>, ? extends T> getContainer();
}
