package br.com.yolomc.bukkit.hologram.nms;

import br.com.yolomc.bukkit.hologram.craft.line.CraftHologramLine;
import br.com.yolomc.bukkit.hologram.craft.line.CraftTouchSlimeLine;
import br.com.yolomc.bukkit.hologram.nms.entity.NMSArmorStand;
import br.com.yolomc.bukkit.hologram.nms.entity.NMSEntityBase;
import br.com.yolomc.bukkit.hologram.nms.interfaces.NMSManager;
import br.com.yolomc.bukkit.hologram.util.ReflectionUtils;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityTypes;
import net.minecraft.server.v1_8_R3.World;
import net.minecraft.server.v1_8_R3.WorldServer;
import org.apache.commons.lang3.Validate;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.event.entity.CreatureSpawnEvent;

/**
 * Forked from https://github.com/filoghost/HolographicDisplays
 */
public class NmsManagerImpl implements NMSManager {

    public void setup() throws Exception {
        registerCustomEntity(EntityNMSArmorStand.class, "ArmorStand", 30);
        registerCustomEntity(EntityNMSSlime.class, "Slime", 55);
    }

    public void registerCustomEntity(Class entityClass, String name, int id) throws Exception {
        ReflectionUtils.putInPrivateStaticMap(EntityTypes.class, "d", entityClass, name);
        ReflectionUtils.putInPrivateStaticMap(EntityTypes.class, "f", entityClass, id);
    }

    public EntityNMSSlime spawnNMSSlime(org.bukkit.World bukkitWorld, double x, double y, double z,
                                        CraftTouchSlimeLine parentPiece) {
        WorldServer nmsWorld = ((CraftWorld) bukkitWorld).getHandle();
        EntityNMSSlime touchSlime = new EntityNMSSlime(nmsWorld, parentPiece);
        touchSlime.setLocationNMS(x, y, z);
        addEntityToWorld(nmsWorld, touchSlime);
        return touchSlime;
    }

    public NMSArmorStand spawnNMSArmorStand(org.bukkit.World world, double x, double y, double z,
                                            CraftHologramLine parentPiece) {
        WorldServer nmsWorld = ((CraftWorld) world).getHandle();
        EntityNMSArmorStand invisibleArmorStand = new EntityNMSArmorStand(nmsWorld, parentPiece);
        invisibleArmorStand.setLocationNMS(x, y, z);
        addEntityToWorld(nmsWorld, invisibleArmorStand);
        return invisibleArmorStand;
    }

    private boolean addEntityToWorld(WorldServer nmsWorld, Entity nmsEntity) {
        Validate.isTrue(Bukkit.isPrimaryThread(), "Async entity add");
        return nmsWorld.addEntity(nmsEntity, CreatureSpawnEvent.SpawnReason.CUSTOM);
    }

    public boolean isNMSEntityBase(org.bukkit.entity.Entity bukkitEntity) {
        return ((CraftEntity) bukkitEntity).getHandle() instanceof NMSEntityBase;
    }

    public NMSEntityBase getNMSEntityBase(org.bukkit.entity.Entity bukkitEntity) {
        Entity nmsEntity = ((CraftEntity) bukkitEntity).getHandle();
        if (nmsEntity instanceof NMSEntityBase)
            return (NMSEntityBase)nmsEntity;
        return null;
    }

    public boolean hasChatHoverFeature() {
        return false;
    }
}
