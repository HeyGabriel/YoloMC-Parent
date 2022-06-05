package br.com.yolomc.bukkit.hologram.nms.entity;

import br.com.yolomc.bukkit.hologram.craft.line.CraftHologramLine;
import org.bukkit.entity.Entity;

/**
 * Forked from https://github.com/filoghost/HolographicDisplays
 */
public interface NMSEntityBase {

    CraftHologramLine getHologramLine();

    void setLockTick(boolean paramBoolean);

    void setLocationNMS(double paramDouble1, double paramDouble2, double paramDouble3);

    boolean isDeadNMS();

    void killEntityNMS();

    int getIdNMS();

    Entity getBukkitEntityNMS();
}
