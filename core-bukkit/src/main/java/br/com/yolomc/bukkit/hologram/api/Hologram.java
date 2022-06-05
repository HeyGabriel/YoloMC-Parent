package br.com.yolomc.bukkit.hologram.api;

import br.com.yolomc.bukkit.hologram.api.line.HologramLine;
import br.com.yolomc.bukkit.hologram.api.line.TextLine;
import org.bukkit.Location;
import org.bukkit.World;

/**
 * Forked from https://github.com/filoghost/HolographicDisplays
 */
public interface Hologram {

    TextLine appendTextLine(String paramString);

    TextLine insertTextLine(int paramInt, String paramString);

    HologramLine getLine(int paramInt);

    void removeLine(int paramInt);

    void clearLines();

    int size();

    double getHeight();

    void teleport(Location location);

    void teleport(World world, double x, double y, double z);

    Location getLocation();

    double getX();

    double getY();

    double getZ();

    World getWorld();

    VisibilityManager getVisibilityManager();

    long getCreationTimestamp();

    void delete();

    boolean isDeleted();
}
