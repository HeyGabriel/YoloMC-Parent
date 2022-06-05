package br.com.yolomc.bukkit.hologram.nms;

import br.com.yolomc.bukkit.hologram.craft.line.CraftHologramLine;
import br.com.yolomc.bukkit.hologram.nms.entity.NMSArmorStand;
import br.com.yolomc.bukkit.hologram.util.ReflectionUtils;
import br.com.yolomc.bukkit.hologram.util.Utils;
import net.minecraft.server.v1_8_R3.AxisAlignedBB;
import net.minecraft.server.v1_8_R3.DamageSource;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityArmorStand;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityTeleport;
import net.minecraft.server.v1_8_R3.Vec3D;
import net.minecraft.server.v1_8_R3.World;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;

/**
 * Forked from https://github.com/filoghost/HolographicDisplays
 */
public class EntityNMSArmorStand extends EntityArmorStand implements NMSArmorStand {

    private boolean lockTick;

    private CraftHologramLine parentPiece;

    public EntityNMSArmorStand(World world, CraftHologramLine parentPiece) {
        super(world);
        setInvisible(true);
        setSmall(true);
        setArms(false);
        setGravity(true);
        setBasePlate(true);
        this.parentPiece = parentPiece;
        try {
            ReflectionUtils.setPrivateField(EntityArmorStand.class, this, "bi", Integer.valueOf(2147483647));
        } catch (Exception exception) {}
        forceSetBoundingBox(new NullBoundingBox());
    }

    public void b(NBTTagCompound nbttagcompound) {}

    public boolean c(NBTTagCompound nbttagcompound) {
        return false;
    }

    public boolean d(NBTTagCompound nbttagcompound) {
        return false;
    }

    public void e(NBTTagCompound nbttagcompound) {}

    public boolean isInvulnerable(DamageSource source) {
        return true;
    }

    public void setCustomName(String customName) {}

    public void setCustomNameVisible(boolean visible) {}

    public boolean a(EntityHuman human, Vec3D vec3d) {
        return true;
    }

    public boolean d(int i, ItemStack item) {
        return false;
    }

    public void setEquipment(int i, ItemStack item) {}

    public void a(AxisAlignedBB boundingBox) {}

    public void forceSetBoundingBox(AxisAlignedBB boundingBox) {
        super.a(boundingBox);
    }

    public int getId() {
        StackTraceElement[] elements = Thread.currentThread().getStackTrace();
        if (elements.length > 2 && elements[2] != null && elements[2].getFileName().equals("EntityTrackerEntry.java") && elements[2].getLineNumber() > 137 && elements[2].getLineNumber() < 147)
            return -1;
        return super.getId();
    }

    public void t_() {
        if (!this.lockTick)
            super.t_();
    }

    public void makeSound(String sound, float f1, float f2) {}

    public void setCustomNameNMS(String name) {
        if (name != null && name.length() > 300)
            name = name.substring(0, 300);
        super.setCustomName(name);
        super.setCustomNameVisible((name != null && !name.isEmpty()));
    }

    public String getCustomNameNMS() {
        return getCustomName();
    }

    public void callSuperTick() {
        h();
    }

    public void setLockTick(boolean lock) {
        this.lockTick = lock;
    }

    public void die() {
        setLockTick(false);
        super.die();
    }

    public CraftEntity getBukkitEntity() {
        if (this.bukkitEntity == null)
            this.bukkitEntity = new CraftNMSArmorStand(this.world.getServer(), this);
        return this.bukkitEntity;
    }

    public void killEntityNMS() {
        die();
    }

    public void setLocationNMS(double x, double y, double z) {
        setPosition(x, y, z);
        PacketPlayOutEntityTeleport teleportPacket = new PacketPlayOutEntityTeleport((Entity)this);
        for (Object obj : this.world.players) {
            if (obj instanceof EntityPlayer) {
                EntityPlayer nmsPlayer = (EntityPlayer)obj;
                double distanceSquared = Utils.square(nmsPlayer.locX - this.locX) + Utils.square(nmsPlayer.locZ - this.locZ);
                if (distanceSquared < 8192.0D && nmsPlayer.playerConnection != null)
                    nmsPlayer.playerConnection.sendPacket((Packet)teleportPacket);
            }
        }
    }

    public boolean isDeadNMS() {
        return this.dead;
    }

    public int getIdNMS() {
        return getId();
    }

    public CraftHologramLine getHologramLine() {
        return this.parentPiece;
    }

    public org.bukkit.entity.Entity getBukkitEntityNMS() {
        return getBukkitEntity();
    }
}
