package br.com.yolomc.bukkit.manager;

import br.com.yolomc.bukkit.BukkitMain;
import lombok.Getter;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Gerenciador de Tarefas.
 *
 * @author skyprogrammer
 */
public final class TaskManager {

    @Getter
    private BukkitMain plugin;
    private Map<Class<?>, Management> managers = new HashMap<>();
    @Getter
    private boolean postWorlded = false;

    public TaskManager(BukkitMain plugin) {
        this.plugin = plugin;
    }

    public <T extends Management> T getManager(Class<T> tClass) {
        return tClass.cast(managers.get(tClass));
    }

    public Management addQueuedManager(Class<? extends Management> clazz) {
        if (!managers.containsKey(clazz)) {
            try {
                Management manager = clazz.getConstructor(TaskManager.class).newInstance(this);
                managers.put(clazz, manager);
                return manager;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public boolean addManager(Class<? extends Management> clazz) {
        Management manager = addQueuedManager(clazz);
        if (manager != null) {
            manager.start();
            plugin.getLogger().info("Processo " + clazz.getSimpleName() + " iniciado!");
            return true;
        }
        plugin.getLogger().info("Processo " + clazz.getSimpleName() + " não iniciado!");
        return false;
    }

    public void onStartup() {
        for (Management manager : managers.values()) {
            if (!manager.isRunning()) {
                if (manager.getLoadOrder() == LoadOrder.STARTUP) {
                    manager.start();
                    if (manager.isRunning()) {
                        plugin.getLogger().info("[STARTUP] Processo " + manager.getClass().getSimpleName()
                                + " iniciado!");
                    }
                }
            }
        }
    }

    public void onPostWorld() {
        postWorlded = true;
        for (Management manager : managers.values()) {
            if (!manager.isRunning()) {
                if (manager.getLoadOrder() == LoadOrder.POSTWORLD) {
                    manager.start();
                    if (manager.isRunning()) {
                        plugin.getLogger().info("[POSTWORLD] Processo " + manager.getClass().getSimpleName()
                                + " iniciado!");
                    }
                }
            }
        }
    }

    public void destroy() {
        Iterator<Management> it = managers.values().iterator();
        while (it.hasNext()) {
            it.next().stop();
            it.remove();
        }
        postWorlded = false;
        managers = null;
        plugin = null;
    }
}
