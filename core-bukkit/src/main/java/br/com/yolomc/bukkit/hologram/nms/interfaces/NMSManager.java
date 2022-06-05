package br.com.yolomc.bukkit.hologram.nms.interfaces;

import br.com.yolomc.bukkit.hologram.craft.line.CraftHologramLine;
import br.com.yolomc.bukkit.hologram.craft.line.CraftTouchSlimeLine;
import br.com.yolomc.bukkit.hologram.nms.entity.NMSArmorStand;
import br.com.yolomc.bukkit.hologram.nms.entity.NMSEntityBase;
import br.com.yolomc.bukkit.hologram.nms.entity.NMSSlime;
import org.bukkit.World;
import org.bukkit.entity.Entity;

/**
 * Forked from https://github.com/filoghost/HolographicDisplays
 */
public interface NMSManager {

    void setup() throws Exception;

    NMSArmorStand spawnNMSArmorStand(World world, double x, double y, double z, CraftHologramLine line);

    NMSSlime spawnNMSSlime(World world, double x, double y, double z, CraftTouchSlimeLine line);

    boolean isNMSEntityBase(Entity entity);

    NMSEntityBase getNMSEntityBase(Entity entity);

    boolean hasChatHoverFeature();
}
