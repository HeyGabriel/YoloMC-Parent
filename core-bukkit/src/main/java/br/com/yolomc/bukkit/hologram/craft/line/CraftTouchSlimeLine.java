package br.com.yolomc.bukkit.hologram.craft.line;

import br.com.yolomc.bukkit.BukkitMain;
import br.com.yolomc.bukkit.hologram.HologramManager;
import br.com.yolomc.bukkit.hologram.craft.CraftHologram;
import br.com.yolomc.bukkit.hologram.nms.entity.NMSEntityBase;
import br.com.yolomc.bukkit.hologram.nms.entity.NMSSlime;
import org.bukkit.World;

/**
 * Forked from https://github.com/filoghost/HolographicDisplays
 */
public class CraftTouchSlimeLine extends CraftHologramLine {

    private CraftTouchableLine touchablePiece;
    private NMSSlime nmsSlime;
    private NMSEntityBase nmsVehicle;

    protected CraftTouchSlimeLine(CraftHologram parent, CraftTouchableLine touchablePiece) {
        super(0.5D, parent);
        this.touchablePiece = touchablePiece;
    }

    public CraftTouchableLine getTouchablePiece() {
        return this.touchablePiece;
    }

    public void spawn(World world, double x, double y, double z) {
        super.spawn(world, x, y, z);
        double offset = -1.49D;
        this.nmsSlime = BukkitMain.getTaskManager().getManager(HologramManager.class)
                .getNmsManager().spawnNMSSlime(world, x, y + offset, z, this);
        this.nmsVehicle = BukkitMain.getTaskManager().getManager(HologramManager.class)
                .getNmsManager().spawnNMSArmorStand(world, x, y + offset, z, this);
        this.nmsSlime.setPassengerOfNMS(this.nmsVehicle);
        this.nmsSlime.setLockTick(true);
        this.nmsVehicle.setLockTick(true);
    }

    public void despawn() {
        super.despawn();
        if (this.nmsSlime != null) {
            this.nmsSlime.killEntityNMS();
            this.nmsSlime = null;
        }
        if (this.nmsVehicle != null) {
            this.nmsVehicle.killEntityNMS();
            this.nmsVehicle = null;
        }
    }

    public void teleport(double x, double y, double z) {
        double offset = -1.49D;
        if (this.nmsVehicle != null)
            this.nmsVehicle.setLocationNMS(x, y + offset, z);
        if (this.nmsSlime != null)
            this.nmsSlime.setLocationNMS(x, y + offset, z);
    }

    public int[] getEntitiesIDs() {
        if (isSpawned())
            return new int[] { this.nmsVehicle.getIdNMS(), this.nmsSlime.getIdNMS() };
        return new int[0];
    }

    public NMSSlime getNmsSlime() {
        return this.nmsSlime;
    }

    public NMSEntityBase getNmsVehicle() {
        return this.nmsVehicle;
    }

    public String toString() {
        return "CraftTouchSlimeLine [touchablePiece=" + this.touchablePiece + "]";
    }
}
