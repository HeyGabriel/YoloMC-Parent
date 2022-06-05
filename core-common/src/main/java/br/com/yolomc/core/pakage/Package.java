package br.com.yolomc.core.pakage;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.util.UUID;

/**
 * Representa um pacote recebido por um jogador.
 *
 * @author skyprogrammer
 */
@Getter
@AllArgsConstructor
public abstract class Package<T> {

    private T pack;
    private String name;
    private PackageSender sender;
    private Instant receivedIn;
    private long expirationTime;
    private AcquiredStatus acquiredStatus;

    public boolean isLifetime() {
        return expirationTime == -1;
    }

    public boolean isExpired() {
        return !isLifetime() && expirationTime < System.currentTimeMillis();
    }

    public enum AcquiredStatus {
        SERVER, STORE;
    }

    @Getter
    @RequiredArgsConstructor
    public static class PackageSender {

        private final UUID uniqueId;
        private final String name;
    }
}
