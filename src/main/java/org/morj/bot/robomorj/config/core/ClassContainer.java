package org.morj.bot.robomorj.config.core;

import java.util.HashMap;
import java.util.Map;

/**
 * @author TheDiVaZo
 * created on 01.05.2024
 */
public abstract class ClassContainer<T> implements IClassContainer<T> {
    protected final Map<Class<? extends T>, T> container;

    public ClassContainer(Map<Class<? extends T>, T> container) {
        this.container = container;
    }

    public ClassContainer() {
        this(new HashMap());
    }

    public <O extends T> O get(Class<O> clazz) {
        return (O) this.container.get(clazz);
    }

    public <O extends T> boolean contains(Class<O> clazz) {
        return this.get(clazz) != null;
    }

    public Map<Class<? extends T>, ? extends T> getContainer() {
        return new HashMap<>(this.container);
    }
}
