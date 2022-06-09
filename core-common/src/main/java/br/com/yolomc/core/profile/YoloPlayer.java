package br.com.yolomc.core.profile;

import br.com.yolomc.core.Commons;
import br.com.yolomc.core.pakage.PackageGroup;
import br.com.yolomc.core.pakage.PackagePermission;
import br.com.yolomc.core.resolver.MethodResolver;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Armazena todas as informações da conta de
 * um jogador.
 *
 * @author skyprogrammer
 */
@Getter
@RequiredArgsConstructor
public class YoloPlayer {

    private final UUID uniqueId;
    private final String name;

    @Setter
    private Tag tag;

    private List<PackageGroup> groups = new ArrayList<>();
    private List<PackagePermission> permissions = new ArrayList<>();

    public String getId() {
        return getUniqueId().toString();
    }

    public Group getMainGroup() {
        return null;
    }

    public void save(String... fields) {

    }

    public void saveAll() {

    }

    public static YoloPlayer getYoloPlayer(UUID uuid) {
        return Commons.getAccountMap().get(uuid);
    }

    public static YoloPlayer getYoloPlayer(Object player) {
        try {
            return getYoloPlayer((UUID) new MethodResolver(player.getClass(), "getUniqueId").resolve()
                    .invoke(player));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
