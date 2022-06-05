package br.com.yolomc.bukkit.hologram.api.protocol;

import br.com.yolomc.bukkit.hologram.craft.CraftHologram;
import br.com.yolomc.bukkit.hologram.craft.line.CraftHologramLine;
import br.com.yolomc.bukkit.hologram.craft.line.CraftTextLine;
import br.com.yolomc.bukkit.hologram.craft.line.CraftTouchSlimeLine;
import br.com.yolomc.bukkit.hologram.craft.line.CraftTouchableLine;
import br.com.yolomc.bukkit.hologram.nms.entity.NMSEntityBase;
import br.com.yolomc.bukkit.hologram.nms.interfaces.NMSManager;
import br.com.yolomc.bukkit.hologram.util.Utils;
import java.util.List;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

/**
 * Forked from https://github.com/filoghost/HolographicDisplays
 */
public class ProtocolHook {

    private static NMSManager nmsManager;

    public static boolean load(NMSManager nmsManager) {
        ProtocolHook.nmsManager = nmsManager;
        return true;
    }

    public static void sendDestroyEntitiesPacket(Player player, CraftHologram hologram) {
        List<Integer> ids = Utils.newList();
        for (CraftHologramLine line : hologram.getLinesUnsafe()) {
            if (line.isSpawned()) {
                byte b;
                int i;
                int[] arrayOfInt;
                for (i = (arrayOfInt = line.getEntitiesIDs()).length, b = 0; b < i; ) {
                    int id = arrayOfInt[b];
                    ids.add(Integer.valueOf(id));
                    b++;
                }
            }
        }
        if (!ids.isEmpty()) {
            WrapperPlayServerEntityDestroy packet = new WrapperPlayServerEntityDestroy();
            packet.setEntities(ids);
            packet.sendPacket(player);
        }
    }

    public static void sendCreateEntitiesPacket(Player player, CraftHologram hologram) {
        for (CraftHologramLine line : hologram.getLinesUnsafe()) {
            if (line.isSpawned()) {
                if (line instanceof CraftTextLine) {
                    CraftTextLine textLine = (CraftTextLine)line;
                    if (textLine.isSpawned()) {
                        AbstractPacket nameablePacket = new WrapperPlayServerSpawnEntityLiving(textLine.getNmsNameble().getBukkitEntityNMS());
                        nameablePacket.sendPacket(player);
                        if (textLine.getNmsSkullVehicle() != null) {
                            AbstractPacket vehiclePacket = new WrapperPlayServerSpawnEntity(textLine.getNmsSkullVehicle().getBukkitEntityNMS(), 66, 0);
                            vehiclePacket.sendPacket(player);
                            WrapperPlayServerAttachEntity attachPacket = new WrapperPlayServerAttachEntity();
                            attachPacket.setVehicleId(textLine.getNmsSkullVehicle().getIdNMS());
                            attachPacket.setEntityId(textLine.getNmsNameble().getIdNMS());
                            attachPacket.sendPacket(player);
                        }
                    }
                }
                CraftTouchableLine touchableLine = (CraftTouchableLine)line;
                if (touchableLine.isSpawned() && touchableLine.getTouchSlime() != null) {
                    CraftTouchSlimeLine touchSlime = touchableLine.getTouchSlime();
                    if (touchSlime.isSpawned()) {
                        AbstractPacket vehiclePacket = new WrapperPlayServerSpawnEntityLiving(
                                touchSlime.getNmsVehicle().getBukkitEntityNMS());
                        vehiclePacket.sendPacket(player);
                        AbstractPacket slimePacket = new WrapperPlayServerSpawnEntityLiving(
                                touchSlime.getNmsSlime().getBukkitEntityNMS());
                        slimePacket.sendPacket(player);
                        WrapperPlayServerAttachEntity attachPacket = new WrapperPlayServerAttachEntity();
                        attachPacket.setVehicleId(touchSlime.getNmsVehicle().getIdNMS());
                        attachPacket.setEntityId(touchSlime.getNmsSlime().getIdNMS());
                        attachPacket.sendPacket(player);
                    }
                }
            }
        }
    }

    private static boolean isHologramType(EntityType type) {
        return !(type != EntityType.HORSE && type != EntityType.WITHER_SKULL && type !=
                EntityType.DROPPED_ITEM && type != EntityType.SLIME && type != EntityType.ARMOR_STAND);
    }

    private static CraftHologram getHologram(Entity bukkitEntity) {
        NMSEntityBase entity = nmsManager.getNMSEntityBase(bukkitEntity);
        if (entity != null)
            return entity.getHologramLine().getParent();
        return null;
    }
}
