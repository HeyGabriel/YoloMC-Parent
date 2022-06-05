package br.com.yolomc.bukkit.manager;

import br.com.yolomc.bukkit.BukkitMain;
import lombok.Getter;
import org.bukkit.Server;
import org.bukkit.event.Listener;

/**
 * Representa um processo em execução.
 *
 * @author skyprogrammer
 */
@Getter
public abstract class Management {

    private final TaskManager taskManager;
    private boolean running = false;
    private final LoadOrder loadOrder;

    public Management(TaskManager taskManager, LoadOrder loadOrder) {
        this.taskManager = taskManager;
        this.loadOrder = loadOrder;
    }

    public BukkitMain getPlugin() {
        return taskManager.getPlugin();
    }

    public Server getServer() {
        return getPlugin().getServer();
    }

    public void registerListener(Listener listener) {
        getServer().getPluginManager().registerEvents(listener, getPlugin());
    }

    public void start() {
        try {
            onEnable();
            running = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        try {
            onDisable();
        } catch (Exception e) {
            e.printStackTrace();
        }
        running = false;
    }

    public abstract void onEnable();

    public abstract void onDisable();
}
