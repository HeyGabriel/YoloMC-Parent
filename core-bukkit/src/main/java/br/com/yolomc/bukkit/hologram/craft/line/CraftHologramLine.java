package br.com.yolomc.bukkit.hologram.craft.line;

import br.com.yolomc.bukkit.hologram.api.line.HologramLine;
import br.com.yolomc.bukkit.hologram.craft.CraftHologram;
import org.apache.commons.lang3.Validate;
import org.bukkit.World;

/**
 * Forked from https://github.com/filoghost/HolographicDisplays
 */
public abstract class CraftHologramLine implements HologramLine {

    private final double height;
    private final CraftHologram parent;
    private boolean isSpawned;

    protected CraftHologramLine(double height, CraftHologram parent) {
        Validate.notNull(parent, "parent hologram");
        this.height = height;
        this.parent = parent;
    }

    public final double getHeight() {
        return this.height;
    }

    public final CraftHologram getParent() {
        return this.parent;
    }

    public void removeLine() {
        this.parent.removeLine(this);
    }

    public void spawn(World world, double x, double y, double z) {
        Validate.notNull(world, "world");
        despawn();
        this.isSpawned = true;
    }

    public void despawn() {
        this.isSpawned = false;
    }

    public final boolean isSpawned() {
        return this.isSpawned;
    }

    public abstract int[] getEntitiesIDs();

    public abstract void teleport(double paramDouble1, double paramDouble2, double paramDouble3);
}
