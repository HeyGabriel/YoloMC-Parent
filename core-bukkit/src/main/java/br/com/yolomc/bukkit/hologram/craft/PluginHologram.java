package br.com.yolomc.bukkit.hologram.craft;

import org.apache.commons.lang3.Validate;
import org.bukkit.Location;
import org.bukkit.plugin.Plugin;

/**
 * Forked from https://github.com/filoghost/HolographicDisplays
 */
public class PluginHologram extends CraftHologram {

    private Plugin plugin;

    public PluginHologram(Location source, Plugin plugin) {
        super(source);
        Validate.notNull(plugin, "plugin");
        this.plugin = plugin;
    }

    public Plugin getOwner() {
        return this.plugin;
    }

    public void delete() {
        super.delete();
        PluginHologramManager.removeHologram(this);
    }
}
