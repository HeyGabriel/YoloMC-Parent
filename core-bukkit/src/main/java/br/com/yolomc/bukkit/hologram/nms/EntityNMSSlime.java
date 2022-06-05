package br.com.yolomc.bukkit.hologram.nms;

import br.com.yolomc.bukkit.hologram.craft.line.CraftHologramLine;
import br.com.yolomc.bukkit.hologram.craft.line.CraftTouchSlimeLine;
import br.com.yolomc.bukkit.hologram.nms.entity.NMSEntityBase;
import br.com.yolomc.bukkit.hologram.nms.entity.NMSSlime;
import br.com.yolomc.bukkit.hologram.util.ReflectionUtils;
import net.minecraft.server.v1_8_R3.AxisAlignedBB;
import net.minecraft.server.v1_8_R3.DamageSource;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityDamageSource;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.EntitySlime;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.World;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.event.player.PlayerInteractEntityEvent;

/**
 * Forked from https://github.com/filoghost/HolographicDisplays
 */
public class EntityNMSSlime extends EntitySlime implements NMSSlime {

    private boolean lockTick;
    private CraftTouchSlimeLine parentPiece;

    public EntityNMSSlime(World world, CraftTouchSlimeLine parentPiece) {
        super(world);
        this.persistent = true;
        a(new float[] { 0.0F, 0.0F });
        setSize(1);
        setInvisible(true);
        this.parentPiece = parentPiece;
        forceSetBoundingBox(new NullBoundingBox());
    }

    public void a(AxisAlignedBB boundingBox) {}

    public void forceSetBoundingBox(AxisAlignedBB boundingBox) {
        super.a(boundingBox);
    }

    public void t_() {
        if (this.ticksLived % 20 == 0)
            if (this.vehicle == null)
                die();
        if (!this.lockTick)
            super.t_();
    }

    public void b(NBTTagCompound nbttagcompound) {}

    public boolean c(NBTTagCompound nbttagcompound) {
        return false;
    }

    public boolean d(NBTTagCompound nbttagcompound) {
        return false;
    }

    public void e(NBTTagCompound nbttagcompound) {}

    public boolean damageEntity(DamageSource damageSource, float amount) {
        if (damageSource instanceof EntityDamageSource) {
            EntityDamageSource entityDamageSource = (EntityDamageSource) damageSource;
            if (entityDamageSource.getEntity() instanceof EntityPlayer)
                Bukkit.getPluginManager().callEvent(
                        new PlayerInteractEntityEvent(((EntityPlayer) entityDamageSource.getEntity())
                                .getBukkitEntity(), getBukkitEntity()));
        }
        return false;
    }

    public boolean isInvulnerable(DamageSource source) {
        return true;
    }

    public void setCustomName(String customName) {}

    public void setCustomNameVisible(boolean visible) {}

    public void makeSound(String sound, float volume, float pitch) {}

    public void setLockTick(boolean lock) {
        this.lockTick = lock;
    }

    public void die() {
        setLockTick(false);
        super.die();
    }

    public CraftEntity getBukkitEntity() {
        if (this.bukkitEntity == null)
            this.bukkitEntity = (CraftEntity)new CraftNMSSlime(this.world.getServer(), this);
        return this.bukkitEntity;
    }

    public boolean isDeadNMS() {
        return this.dead;
    }

    public void killEntityNMS() {
        die();
    }

    public void setLocationNMS(double x, double y, double z) {
        setPosition(x, y, z);
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

    public void setPassengerOfNMS(NMSEntityBase vehicleBase) {
        if (vehicleBase == null || !(vehicleBase instanceof Entity))
            return;
        Entity entity = (Entity)vehicleBase;
        try {
            ReflectionUtils.setPrivateField(Entity.class, this, "ar", Double.valueOf(0.0D));
            ReflectionUtils.setPrivateField(Entity.class, this, "as", Double.valueOf(0.0D));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (this.vehicle != null)
            this.vehicle.passenger = null;
        this.vehicle = entity;
        entity.passenger = (Entity)this;
    }
}
