package br.com.yolomc.bukkit.hologram.craft.line;

import br.com.yolomc.bukkit.hologram.api.handler.TouchHandler;
import br.com.yolomc.bukkit.hologram.craft.CraftHologram;
import org.bukkit.World;

/**
 * Forked from https://github.com/filoghost/HolographicDisplays
 */
public abstract class CraftTouchableLine extends CraftHologramLine {

    protected CraftTouchSlimeLine touchSlime;
    private TouchHandler touchHandler;

    protected CraftTouchableLine(double height, CraftHologram parent) {
        super(height, parent);
    }

    protected void setTouchHandler(TouchHandler touchHandler, World world, double x, double y, double z) {
        this.touchHandler = touchHandler;
        if (touchHandler != null && this.touchSlime == null && world != null) {
            this.touchSlime = new CraftTouchSlimeLine(getParent(), this);
            this.touchSlime.spawn(world, x, y + getHeight() / 2.0D - this.touchSlime.getHeight() / 2.0D, z);
        } else if (touchHandler == null && this.touchSlime != null) {
            this.touchSlime.despawn();
            this.touchSlime = null;
        }
    }

    public TouchHandler getTouchHandler() {
        return this.touchHandler;
    }

    public void spawn(World world, double x, double y, double z) {
        super.spawn(world, x, y, z);
        if (this.touchHandler != null) {
            this.touchSlime = new CraftTouchSlimeLine(getParent(), this);
            this.touchSlime.spawn(world, x, y + getHeight() / 2.0D - this.touchSlime.getHeight() / 2.0D, z);
        }
    }

    public void despawn() {
        super.despawn();
        if (this.touchSlime != null) {
            this.touchSlime.despawn();
            this.touchSlime = null;
        }
    }

    public void teleport(double x, double y, double z) {
        if (this.touchSlime != null)
            this.touchSlime.teleport(x, y + getHeight() / 2.0D - this.touchSlime.getHeight() / 2.0D, z);
    }

    public CraftTouchSlimeLine getTouchSlime() {
        return this.touchSlime;
    }
}
