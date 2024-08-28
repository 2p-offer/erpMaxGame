package com.erp.core.servernode;

public interface ServerNodeSupplier<T extends ServerNode> {

    T get();
}
