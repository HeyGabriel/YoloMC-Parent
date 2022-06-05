package br.com.yolomc.core.backend;

/**
 * Representa um gerenciador backend.
 */
public interface Backend {

    void connect();

    boolean isConnected();

    void disconnect();
}
