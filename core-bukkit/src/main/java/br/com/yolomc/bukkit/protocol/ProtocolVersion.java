package br.com.yolomc.bukkit.protocol;

import com.viaversion.viaversion.api.Via;
import org.bukkit.entity.Player;

import java.util.stream.Stream;

/**
 * Representa a versão do cliente de um jogador.
 *
 * @author skyprogrammer
 */
public enum ProtocolVersion {

    MC_1_18_2(758),

    MC_1_18(757),

    MC_1_17_1(756),

    MC_1_17(755),

    MC_1_16_4(754),

    MC_1_16_3(753),

    MC_1_16_2(751),

    MC_1_16_1(736),

    MC_1_16(735),

    MC_1_15_2(578),

    MC_1_15_1(575),

    MC_1_15(573),

    MC_1_14_4(498),

    MC_1_14_3(490),

    MC_1_14_2(485),

    MC_1_14_1(480),

    MC_1_14(477),

    MC_1_13_2(404),

    MC_1_13_1(401),

    MC_1_13(393),

    MC_1_12_2(340),

    MC_1_12_1(338),

    MC_1_12(335),

    MC_1_11_1(316),

    MC_1_11(315),

    MC_1_10(210),

    MC_1_9_3(110),

    MC_1_9_2(109),

    MC_1_9_1(108),

    MC_1_9(107),

    MC_1_8(47),

    MC_1_7_10(5),

    MC_1_7_2(4),

    UNKNOWN(-1);

    private int id;

    ProtocolVersion(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static ProtocolVersion getById(int id) {
        return Stream.of(values()).filter(e -> e.getId() == id).findFirst().orElse(UNKNOWN);
    }

    public static ProtocolVersion getVersion(Player player) {
        return getById(Via.getAPI().getPlayerVersion(player));
    }
}
