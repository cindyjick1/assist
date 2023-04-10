package com.cindyjick.res.assist.util;

import java.util.Optional;

public final class NumberUtil {
    private NumberUtil() {
    }

    /**
     * judge source is in range
     *
     * @param source     be compared target
     * @param leftRange  range of left
     * @param rightRange range of right
     * @param <T>        Class implement Comparable
     * @return true -> in range, false -> out of range
     */
    public static <T extends Comparable<T>> boolean inRange(T source, T leftRange, T rightRange) {
        return Optional.of(source)
                .filter(e -> Optional.ofNullable(leftRange).map(left -> e.compareTo(left) >= 0).orElse(true))
                .map(e -> Optional.ofNullable(rightRange).map(right -> e.compareTo(right) <= 0).orElse(true))
                .orElse(false);
    }
}
