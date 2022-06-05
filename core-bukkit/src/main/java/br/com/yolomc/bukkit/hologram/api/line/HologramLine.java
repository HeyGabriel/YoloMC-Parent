package br.com.yolomc.bukkit.hologram.api.line;

import br.com.yolomc.bukkit.hologram.api.Hologram;

/**
 * Forked from https://github.com/filoghost/HolographicDisplays
 */
public interface HologramLine {

    Hologram getParent();

    void removeLine();
}
