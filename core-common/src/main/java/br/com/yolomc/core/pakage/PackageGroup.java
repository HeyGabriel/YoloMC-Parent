package br.com.yolomc.core.pakage;

import br.com.yolomc.core.profile.Group;
import br.com.yolomc.core.profile.model.GroupModel;

import java.time.Instant;

/**
 * Representa um Cargo recebido por um
 * jogador.
 *
 * @author skyprogrammer
 */
public class PackageGroup extends Package<GroupModel> {

    public PackageGroup(GroupModel pack, String name, PackageSender sender, Instant receivedIn,
                        long expirationTime, AcquiredStatus acquiredStatus) {
        super(pack, name, sender, receivedIn, expirationTime, acquiredStatus);
    }
}
