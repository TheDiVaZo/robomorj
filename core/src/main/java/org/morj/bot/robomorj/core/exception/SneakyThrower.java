package org.morj.bot.robomorj.core.exception;

import org.jooq.lambda.fi.lang.CheckedRunnable;
import org.jooq.lambda.fi.util.function.CheckedSupplier;

/**
 * @author TheDiVaZo
 * created on 08.05.2024
 */
public class SneakyThrower {
    public SneakyThrower() {
    }

    public static void sneaky(CheckedRunnable checkedRunnable) {
        try {
            checkedRunnable.run();
        } catch (Throwable var2) {
            throw new RuntimeException(var2);
        }
    }

    public static <T> T sneaky(CheckedSupplier<T> checkedSupplier) {
        try {
            return checkedSupplier.get();
        } catch (Throwable var2) {
            throw new RuntimeException(var2);
        }
    }
}
