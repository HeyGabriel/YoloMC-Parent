package br.com.yolomc.bukkit.hologram.api.protocol;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.google.common.primitives.Ints;
import java.util.List;

/**
 * Forked from https://github.com/filoghost/HolographicDisplays
 */
public class WrapperPlayServerEntityDestroy extends AbstractPacket {

    public static final PacketType TYPE = PacketType.Play.Server.ENTITY_DESTROY;

    public WrapperPlayServerEntityDestroy() {
        super(new PacketContainer(TYPE), TYPE);
        this.handle.getModifier().writeDefaults();
    }

    public WrapperPlayServerEntityDestroy(PacketContainer packet) {
        super(packet, TYPE);
    }

    public List<Integer> getEntities() {
        return Ints.asList(this.handle.getIntegerArrays().read(0));
    }

    public void setEntities(int[] entities) {
        this.handle.getIntegerArrays().write(0, entities);
    }

    public void setEntities(List<Integer> entities) {
        setEntities(Ints.toArray(entities));
    }
}
