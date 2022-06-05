package br.com.yolomc.core.backend.mysql;

import br.com.yolomc.core.backend.Backend;

/**
 * Representa um Gerenciador de conexão com banco de dados MySQL.
 *
 * @author skyprogrammer
 */
public class MySQLBackend implements Backend {

    @Override
    public void connect() {

    }

    @Override
    public boolean isConnected() {
        return false;
    }

    @Override
    public void disconnect() {

    }
}
