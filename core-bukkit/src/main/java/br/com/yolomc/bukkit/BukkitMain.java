package br.com.yolomc.bukkit;

import br.com.yolomc.bukkit.api.scoreboard.handler.ScoreboardHandler;
import br.com.yolomc.bukkit.api.scoreboard.handler.ScoreboardUpdateListener;
import br.com.yolomc.bukkit.command.CommandLoader;
import br.com.yolomc.bukkit.hologram.HologramManager;
import br.com.yolomc.bukkit.listener.PlayerNBTListener;
import br.com.yolomc.bukkit.manager.TaskManager;
import br.com.yolomc.bukkit.scheduler.TimerScheduler;
import br.com.yolomc.core.profile.YoloPlayer;
import com.comphenix.protocol.ProtocolConfig;
import com.comphenix.protocol.ProtocolLibrary;
import com.viaversion.viaversion.api.Via;
import lombok.Getter;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldLoadEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Method;

/**
 * Classe Main do Core baseado em BukkitAPI.
 */
@Getter
public abstract class BukkitMain extends JavaPlugin implements Listener {

    @Getter
    private static BukkitMain instance;
    @Getter
    private static TaskManager taskManager;

    private CommandLoader commandLoader;

    public BukkitMain() {
        super();
        instance = this;
        taskManager = new TaskManager(this);
        commandLoader = new CommandLoader(this);
    }

    @Override
    public void onLoad() {
        // VIA VERSION
        Via.getConfig().setCheckForUpdates(false);
        // PROTOCOLLIB
        try {
            Method method = ProtocolLibrary.class.getMethod("getConfiguration");
            ProtocolConfig protocolConfig = (ProtocolConfig) method.invoke(null);
            protocolConfig.setAutoNotify(false);
            protocolConfig.setAutoDownload(false);
            protocolConfig.setMetricsEnabled(false);
        } catch (Exception e) {
            ProtocolConfig protocolConfig = ProtocolLibrary.getConfig();
            protocolConfig.setAutoNotify(false);
            protocolConfig.setAutoDownload(false);
            protocolConfig.setMetricsEnabled(false);
        }
        /**
         * MANAGERS
         */
        taskManager.addQueuedManager(HologramManager.class);
        /**
         */
        getLogger().info("[TaskManager] Iniciando...");
        taskManager.onStartup();
    }

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        getServer().getPluginManager().registerEvents(
                new ScoreboardUpdateListener(ScoreboardHandler.getInstance()), this);
        getServer().getPluginManager().registerEvents(new PlayerNBTListener(), this);
        commandLoader.loadCommandsFromPackage("br.com.yolomc.bukkit.command.registry");
        getServer().getScheduler().runTaskTimer(this, new TimerScheduler(), 1, 1);
    }

    @Override
    public void onDisable() {
        taskManager.destroy();
    }

    @EventHandler
    public void onWorldLoad(WorldLoadEvent event) {
        if (!taskManager.isPostWorlded()) {
            getLogger().info("[TaskManager] Executando processos pendentes...");
            taskManager.onPostWorld();
        }
    }

    public abstract void onAccountLoad(YoloPlayer player);

    public abstract void onAccountUnload(YoloPlayer player);
}
