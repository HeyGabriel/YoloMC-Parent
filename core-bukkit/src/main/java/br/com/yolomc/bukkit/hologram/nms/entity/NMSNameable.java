package br.com.yolomc.bukkit.hologram.nms.entity;

/**
 * Forked from https://github.com/filoghost/HolographicDisplays
 */
public interface NMSNameable extends NMSEntityBase {

    void setCustomNameNMS(String paramString);

    String getCustomNameNMS();
}
