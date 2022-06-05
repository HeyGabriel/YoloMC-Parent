package br.com.yolomc.bukkit.hologram.listener;

import java.util.Map;
import java.util.logging.Level;

import br.com.yolomc.bukkit.BukkitMain;
import br.com.yolomc.bukkit.hologram.craft.PluginHologram;
import br.com.yolomc.bukkit.hologram.craft.PluginHologramManager;
import br.com.yolomc.bukkit.hologram.craft.line.CraftTouchSlimeLine;
import br.com.yolomc.bukkit.hologram.nms.entity.NMSEntityBase;
import br.com.yolomc.bukkit.hologram.nms.interfaces.NMSManager;
import br.com.yolomc.bukkit.hologram.util.Utils;
import br.com.yolomc.core.Commons;
import org.bukkit.Chunk;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.bukkit.plugin.Plugin;

/**
 * Forked from https://github.com/filoghost/HolographicDisplays
 */
public class HologramListener implements Listener {

    private NMSManager nmsManager;

    private Map<Player, Long> anticlickSpam = Utils.newMap();

    public HologramListener(NMSManager nmsManager) {
        this.nmsManager = nmsManager;
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onChunkUnload(ChunkUnloadEvent event) {
        byte b;
        int i;
        Entity[] arrayOfEntity;
        for (i = (arrayOfEntity = event.getChunk().getEntities()).length, b = 0; b < i; ) {
            Entity entity = arrayOfEntity[b];
            if (!entity.isDead()) {
                NMSEntityBase entityBase = this.nmsManager.getNMSEntityBase(entity);
                if (entityBase != null)
                    entityBase.getHologramLine().getParent().despawnEntities();
            }
            b++;
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onChunkLoad(ChunkLoadEvent event) {
        Chunk chunk = event.getChunk();
        PluginHologramManager.onChunkLoad(chunk);
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = false)
    public void onCreatureSpawn(CreatureSpawnEvent event) {
        if (this.nmsManager.isNMSEntityBase((Entity)event.getEntity()) &&
                event.isCancelled())
            event.setCancelled(false);
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = false)
    public void onProjectileLaunch(ProjectileLaunchEvent event) {
        if (this.nmsManager.isNMSEntityBase((Entity)event.getEntity()) &&
                event.isCancelled())
            event.setCancelled(false);
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = false)
    public void onItemSpawn(ItemSpawnEvent event) {
        if (this.nmsManager.isNMSEntityBase((Entity)event.getEntity()) &&
                event.isCancelled())
            event.setCancelled(false);
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onSlimeInteract(PlayerInteractEntityEvent event) {
        if (event.getRightClicked().getType() == EntityType.SLIME) {
            NMSEntityBase entityBase = this.nmsManager.getNMSEntityBase(event.getRightClicked());
            if (entityBase == null)
                return;
            if (entityBase.getHologramLine() instanceof CraftTouchSlimeLine) {
                CraftTouchSlimeLine touchSlime = (CraftTouchSlimeLine)entityBase.getHologramLine();
                if (touchSlime.getTouchablePiece().getTouchHandler() != null && touchSlime.getParent().getVisibilityManager().isVisibleTo(event.getPlayer())) {
                    Long lastClick = this.anticlickSpam.get(event.getPlayer());
                    if (lastClick != null && System.currentTimeMillis() - lastClick.longValue() < 100L)
                        return;
                    this.anticlickSpam.put(event.getPlayer(), Long.valueOf(System.currentTimeMillis()));
                    try {
                        touchSlime.getTouchablePiece().getTouchHandler().onTouch(event.getPlayer());
                    } catch (Exception ex) {
                        Plugin plugin = (touchSlime.getParent() instanceof PluginHologram) ? ((PluginHologram)touchSlime.getParent()).getOwner() : BukkitMain.getInstance();
                        Commons.getLogger().log(Level.WARNING, "The plugin " + plugin.getName() + " generated an exception when the player " + event.getPlayer().getName() + " touched a hologram.", ex);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        this.anticlickSpam.remove(event.getPlayer());
    }
}
