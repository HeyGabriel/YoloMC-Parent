package br.com.yolomc.bukkit.hologram;

import br.com.yolomc.bukkit.hologram.api.Hologram;
import br.com.yolomc.bukkit.hologram.craft.PluginHologram;
import br.com.yolomc.bukkit.hologram.craft.PluginHologramManager;
import br.com.yolomc.bukkit.hologram.listener.HologramListener;
import br.com.yolomc.bukkit.hologram.nms.NmsManagerImpl;
import br.com.yolomc.bukkit.hologram.nms.interfaces.NMSManager;
import br.com.yolomc.bukkit.manager.Management;
import br.com.yolomc.bukkit.manager.LoadOrder;
import br.com.yolomc.bukkit.manager.TaskManager;
import lombok.Getter;
import org.apache.commons.lang3.Validate;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.Plugin;

import java.util.Collection;

/**
 * Gerenciador de Hologramas.
 *
 * @author skyprogrammer
 */
@Getter
public class HologramManager extends Management {

    private NMSManager nmsManager;

    public HologramManager(TaskManager taskManager) {
        super(taskManager, LoadOrder.POSTWORLD);
    }

    @Override
    public void onEnable() {
        nmsManager = new NmsManagerImpl();
        try {
            nmsManager.setup();
            registerListener(new HologramListener(nmsManager));
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public Hologram createHologram(Plugin plugin, Location source) {
        Validate.notNull(plugin, "plugin");
        Validate.notNull(source, "source");
        Validate.notNull(source.getWorld(), "source's world");
        Validate.isTrue(Bukkit.isPrimaryThread(), "Async hologram creation");
        PluginHologram hologram = new PluginHologram(source, plugin);
        PluginHologramManager.addHologram(hologram);
        return hologram;
    }

    public boolean isHologramEntity(Entity bukkitEntity) {
        Validate.notNull(bukkitEntity, "bukkitEntity");
        return nmsManager.isNMSEntityBase(bukkitEntity);
    }

    public Collection<Hologram> getHolograms(Plugin plugin) {
        Validate.notNull(plugin, "plugin");
        return PluginHologramManager.getHolograms(plugin);
    }

    @Override
    public void onDisable() {

    }
}
