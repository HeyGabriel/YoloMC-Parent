package br.com.yolomc.core.pakage;

import java.time.Instant;

/**
 * Representa uma Permissão recebida por um jogador.
 *
 * @author skyprogrammer.
 */
public class PackagePermission extends Package<String> {

    public PackagePermission(String pack, String name, PackageSender sender, Instant receivedIn,
                             long expirationTime, AcquiredStatus acquiredStatus) {
        super(pack, name, sender, receivedIn, expirationTime, acquiredStatus);
    }
}
