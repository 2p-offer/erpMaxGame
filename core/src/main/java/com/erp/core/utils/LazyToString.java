package com.erp.core.utils;

import java.util.function.Supplier;

/**
 * 延迟toString
 */
public class LazyToString {
    private final Supplier<String> supplier;

    protected LazyToString(Supplier<String> supplier) {
        this.supplier = supplier;
    }

    public static LazyToString valueOf(Supplier<String> supplier) {
        return new LazyToString(supplier);
    }

    public static LazyToString valueOf(Object object) {
        return new LazyToString(object::toString);
    }

    @Override
    public String toString() {
        return supplier.get();
    }
}
