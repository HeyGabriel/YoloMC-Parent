package br.com.yolomc.bukkit.hologram.craft;

import br.com.yolomc.bukkit.hologram.api.Hologram;
import br.com.yolomc.bukkit.hologram.api.handler.TouchHandler;
import br.com.yolomc.bukkit.hologram.api.line.TouchableLine;
import br.com.yolomc.bukkit.hologram.api.replacements.OldTouchHandlerWrapper;
import br.com.yolomc.bukkit.hologram.craft.line.CraftHologramLine;
import br.com.yolomc.bukkit.hologram.craft.line.CraftTextLine;
import br.com.yolomc.bukkit.hologram.util.Utils;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.Validate;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.List;

/**
 * Forked from https://github.com/filoghost/HolographicDisplays
 */
public class CraftHologram implements Hologram {

    private World world;
    private double x;
    private double y;
    private double z;
    private int chunkX;
    private int chunkZ;
    private final List<CraftHologramLine> lines;
    private CraftVisibilityManager visibilityManager;
    private boolean allowPlaceholders;
    private long creationTimestamp;
    private boolean deleted;

    public CraftHologram(Location location) {
        updateLocation(location.getWorld(), location.getX(), location.getY(), location.getZ());
        this.lines = Lists.newArrayList();
        this.allowPlaceholders = false;
        this.creationTimestamp = System.currentTimeMillis();
        this.visibilityManager = new CraftVisibilityManager(this);
    }

    public boolean isInChunk(Chunk chunk) {
        return (chunk.getX() == this.chunkX && chunk.getZ() == this.chunkZ);
    }

    public World getWorld() {
        return this.world;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public double getZ() {
        return this.z;
    }

    public Location getLocation() {
        return new Location(this.world, this.x, this.y, this.z);
    }

    private void updateLocation(World world, double x, double y, double z) {
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
        this.chunkX = Utils.floor(x) >> 4;
        this.chunkZ = Utils.floor(z) >> 4;
    }

    public boolean isDeleted() {
        return this.deleted;
    }

    public void delete() {
        if (!this.deleted) {
            this.deleted = true;
            clearLines();
        }
    }

    public List<CraftHologramLine> getLinesUnsafe() {
        return this.lines;
    }

    public CraftHologramLine getLine(int index) {
        return this.lines.get(index);
    }

    public CraftTextLine appendTextLine(String text) {
        Validate.isTrue(!this.deleted, "hologram already deleted");
        CraftTextLine line = new CraftTextLine(this, text);
        this.lines.add(line);
        return line;
    }

    public CraftTextLine insertTextLine(int index, String text) {
        Validate.isTrue(!this.deleted, "hologram already deleted");
        CraftTextLine line = new CraftTextLine(this, text);
        this.lines.add(index, line);
        return line;
    }

    public void removeLine(int index) {
        Validate.isTrue(!this.deleted, "hologram already deleted");
        ((CraftHologramLine)this.lines.remove(index)).despawn();
    }

    public void removeLine(CraftHologramLine line) {
        Validate.isTrue(!this.deleted, "hologram already deleted");
        this.lines.remove(line);
        line.despawn();
    }

    public void clearLines() {
        for (CraftHologramLine line : this.lines)
            line.despawn();
        this.lines.clear();
    }

    public int size() {
        return this.lines.size();
    }

    public double getHeight() {
        if (this.lines.isEmpty())
            return 0.0D;
        double height = 0.0D;
        for (CraftHologramLine line : this.lines)
            height += line.getHeight();
        height += 0.02 * (this.lines.size() - 1);
        return height;
    }

    public CraftVisibilityManager getVisibilityManager() {
        return this.visibilityManager;
    }

    public long getCreationTimestamp() {
        return this.creationTimestamp;
    }

    public void refreshAll() {
        if (this.world.isChunkLoaded(this.chunkX, this.chunkZ))
            spawnEntities();
    }

    public void spawnEntities() {
        Validate.isTrue(!this.deleted, "hologram already deleted");
        despawnEntities();
        double currentY = this.y;
        boolean first = true;
        for (CraftHologramLine line : this.lines) {
            currentY -= line.getHeight();
            if (first) {
                first = false;
            } else {
                currentY -= 0.02;
            }
            line.spawn(this.world, this.x, currentY, this.z);
        }
    }

    public void despawnEntities() {
        for (CraftHologramLine piece : this.lines)
            piece.despawn();
    }

    public void teleport(Location location) {
        Validate.notNull(location, "location");
        teleport(location.getWorld(), location.getX(), location.getY(), location.getZ());
    }

    public void teleport(World world, double x, double y, double z) {
        Validate.isTrue(!this.deleted, "hologram already deleted");
        Validate.notNull(world, "world");
        if (this.world != world) {
            updateLocation(world, x, y, z);
            despawnEntities();
            refreshAll();
            return;
        }
        updateLocation(world, x, y, z);
        double currentY = y;
        boolean first = true;
        for (CraftHologramLine line : this.lines) {
            if (!line.isSpawned())
                continue;
            currentY -= line.getHeight();
            if (first) {
                first = false;
            } else {
                currentY -= 0.02;
            }
            line.teleport(x, currentY, z);
        }
    }

    public String toString() {
        return "CraftHologram [world=" + this.world + ", x=" + this.x + ", y=" + this.y + ", z=" + this.z + ", lines=" + this.lines + ", deleted=" + this.deleted + "]";
    }

    @Deprecated
    public boolean update() {
        return true;
    }

    @Deprecated
    public void hide() {}

    @Deprecated
    public void addLine(String text) {
        appendTextLine(text);
    }

    @Deprecated
    public void setLine(int index, String text) {
        this.lines.get(index).despawn();
        this.lines.set(index, new CraftTextLine(this, text));
    }

    @Deprecated
    public void insertLine(int index, String text) {
        insertLine(index, text);
    }

    @Deprecated
    public String[] getLines() {
        return null;
    }

    @Deprecated
    public int getLinesLength() {
        return size();
    }

    @Deprecated
    public void setLocation(Location location) {
        teleport(location);
    }

    @Deprecated
    public void setTouchHandler(TouchHandler handler) {
        if (size() > 0) {
            TouchableLine line = (TouchableLine)getLine(0);
            if (handler != null) {
                line.setTouchHandler(new OldTouchHandlerWrapper(this, handler));
            } else {
                line.setTouchHandler(null);
            }
        }
    }

    @Deprecated
    public TouchHandler getTouchHandler() {
        return null;
    }

    @Deprecated
    public boolean hasTouchHandler() {
        return false;
    }
}
