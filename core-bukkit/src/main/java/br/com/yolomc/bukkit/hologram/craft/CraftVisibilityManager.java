package br.com.yolomc.bukkit.hologram.craft;

import br.com.yolomc.bukkit.hologram.api.VisibilityManager;
import br.com.yolomc.bukkit.hologram.api.protocol.ProtocolHook;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Forked from https://github.com/filoghost/HolographicDisplays
 */
public class CraftVisibilityManager implements VisibilityManager {

    private final CraftHologram hologram;
    private Map<String, Boolean> playersVisibilityMap;
    private boolean visibleByDefault;
    private static final int VISIBILITY_DISTANCE_SQUARED = 4096;

    public CraftVisibilityManager(CraftHologram hologram) {
        this.hologram = hologram;
        this.visibleByDefault = true;
    }

    @Override
    public boolean isVisibleByDefault() {
        return this.visibleByDefault;
    }

    @Override
    public void setVisibleByDefault(boolean visibleByDefault) {
        if (this.visibleByDefault != visibleByDefault) {
            boolean oldVisibleByDefault = this.visibleByDefault;
            this.visibleByDefault = visibleByDefault;
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (this.playersVisibilityMap == null ||
                        !this.playersVisibilityMap.containsKey(player.getName().toLowerCase())) {
                    if (oldVisibleByDefault) {
                        sendDestroyPacketIfNear(player, this.hologram);
                    } else {
                        sendCreatePacketIfNear(player, this.hologram);
                    }
                }
            }
        }
    }

    @Override
    public void showTo(Player player) {
        boolean wasVisible = isVisibleTo(player);
        if (this.playersVisibilityMap == null)
            this.playersVisibilityMap = new ConcurrentHashMap<String, Boolean>();
        this.playersVisibilityMap.put(player.getName().toLowerCase(), Boolean.valueOf(true));
        if (!wasVisible)
            sendCreatePacketIfNear(player, this.hologram);
    }

    @Override
    public void hideTo(Player player) {
        boolean wasVisible = isVisibleTo(player);
        if (this.playersVisibilityMap == null)
            this.playersVisibilityMap = new ConcurrentHashMap<String, Boolean>();
        this.playersVisibilityMap.put(player.getName().toLowerCase(), Boolean.valueOf(false));
        if (wasVisible)
            sendDestroyPacketIfNear(player, this.hologram);
    }

    @Override
    public boolean isVisibleTo(Player player) {
        if (this.playersVisibilityMap != null) {
            Boolean value = this.playersVisibilityMap.get(player.getName().toLowerCase());
            if (value != null)
                return value.booleanValue();
        }
        return this.visibleByDefault;
    }

    @Override
    public void resetVisibility(Player player) {
        if (this.playersVisibilityMap == null)
            return;
        boolean wasVisible = isVisibleTo(player);
        this.playersVisibilityMap.remove(player.getName().toLowerCase());
        if (this.visibleByDefault && !wasVisible) {
            sendCreatePacketIfNear(player, this.hologram);
        } else if (!this.visibleByDefault && wasVisible) {
            sendDestroyPacketIfNear(player, this.hologram);
        }
    }

    @Override
    public void resetVisibilityAll() {
        if (this.playersVisibilityMap != null) {
            Set<String> playerNames = new HashSet<String>(this.playersVisibilityMap.keySet());
            for (String playerName : playerNames) {
                Player onlinePlayer = Bukkit.getPlayerExact(playerName);
                if (onlinePlayer != null)
                    resetVisibility(onlinePlayer);
            }
            this.playersVisibilityMap.clear();
            this.playersVisibilityMap = null;
        }
    }

    private static void sendCreatePacketIfNear(Player player, CraftHologram hologram) {
        if (isNear(player, hologram))
            ProtocolHook.sendCreateEntitiesPacket(player, hologram);
    }

    private static void sendDestroyPacketIfNear(Player player, CraftHologram hologram) {
        if (isNear(player, hologram))
            ProtocolHook.sendDestroyEntitiesPacket(player, hologram);
    }

    private static boolean isNear(Player player, CraftHologram hologram) {
        return player.isOnline() && player.getWorld().equals(hologram.getWorld()) &&
                player.getLocation().distanceSquared(hologram.getLocation()) < 4096.0D;
    }

    @Override
    public String toString() {
        return "CraftVisibilityManager [playersMap=" + this.playersVisibilityMap + ", visibleByDefault=" + this.visibleByDefault + "]";
    }
}
