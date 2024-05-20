package org.morj.bot.robomorj.core.util;

import java.util.Objects;
import java.util.function.Function;

@FunctionalInterface
public interface DualFunction<A,B,R> {

    R apply(A a, B b);

    default <V> DualFunction<A, B, V> andThen(
            Function<? super R, ? extends V> after) {
        Objects.requireNonNull(after);
        return (A a, B b) -> after.apply(apply(a, b));
    }
}
