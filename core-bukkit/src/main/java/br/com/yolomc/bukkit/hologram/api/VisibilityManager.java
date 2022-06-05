package br.com.yolomc.bukkit.hologram.api;

import org.bukkit.entity.Player;

/**
 * Forked from https://github.com/filoghost/HolographicDisplays
 */
public interface VisibilityManager {

    boolean isVisibleByDefault();

    void setVisibleByDefault(boolean visibleByDefault);

    void showTo(Player player);

    void hideTo(Player player);

    boolean isVisibleTo(Player player);

    void resetVisibility(Player player);

    void resetVisibilityAll();
}
