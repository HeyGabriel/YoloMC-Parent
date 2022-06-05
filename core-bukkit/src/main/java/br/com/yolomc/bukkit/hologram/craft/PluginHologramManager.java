package br.com.yolomc.bukkit.hologram.craft;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import br.com.yolomc.bukkit.hologram.api.Hologram;
import br.com.yolomc.bukkit.hologram.util.Utils;
import org.bukkit.Chunk;
import org.bukkit.plugin.Plugin;

/**
 * Forked from https://github.com/filoghost/HolographicDisplays
 */
public class PluginHologramManager {

    private static List<PluginHologram> pluginHolograms = Utils.newList();

    public static void addHologram(PluginHologram hologram) {
        pluginHolograms.add(hologram);
    }

    public static void removeHologram(PluginHologram hologram) {
        pluginHolograms.remove(hologram);
        if (!hologram.isDeleted())
            hologram.delete();
    }

    public static List<PluginHologram> getHolograms() {
        return new ArrayList<PluginHologram>(pluginHolograms);
    }

    public static Set<Hologram> getHolograms(Plugin plugin) {
        Set<Hologram> ownedHolograms = Utils.newSet();
        for (PluginHologram hologram : pluginHolograms) {
            if (hologram.getOwner().equals(plugin))
                ownedHolograms.add(hologram);
        }
        return Collections.unmodifiableSet(ownedHolograms);
    }

    public static void onChunkLoad(Chunk chunk) {
        for (PluginHologram hologram : pluginHolograms) {
            if (hologram.isInChunk(chunk))
                hologram.spawnEntities();
        }
    }

    public static void onChunkUnload(Chunk chunk) {
        for (PluginHologram hologram : pluginHolograms) {
            if (hologram.isInChunk(chunk))
                hologram.despawnEntities();
        }
    }

    public static void clearAll() {
        List<PluginHologram> oldHolograms = new ArrayList<PluginHologram>(pluginHolograms);
        pluginHolograms.clear();
        for (PluginHologram hologram : oldHolograms)
            hologram.delete();
    }
}
