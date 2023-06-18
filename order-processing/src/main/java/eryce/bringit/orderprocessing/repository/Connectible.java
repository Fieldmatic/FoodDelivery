package eryce.bringit.orderprocessing.repository;

import eryce.bringit.orderprocessing.repository.configuration.ConnectionProperties;

public abstract class Connectible {

    protected final ConnectionProperties connectionProperties;

    public Connectible(final ConnectionProperties connectionProperties) {
        this.connectionProperties = connectionProperties;
        connect();
    }

    public abstract void connect();
}
