package br.com.yolomc.bukkit;

import br.com.yolomc.bukkit.api.scoreboard.handler.ScoreboardHandler;
import br.com.yolomc.bukkit.scheduler.TimerScheduler;
import br.com.yolomc.core.profile.YoloPlayer;
import com.comphenix.protocol.ProtocolConfig;
import com.comphenix.protocol.ProtocolLibrary;
import com.viaversion.viaversion.api.Via;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Method;

/**
 * Classe Main do Core baseado em BukkitAPI.
 */
@Getter
public abstract class BukkitMain extends JavaPlugin {

    @Getter
    private static BukkitMain instance;

    public BukkitMain() {
        super();
        instance = this;
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
    }

    @Override
    public void onEnable() {
        ScoreboardHandler.getInstance().registerEvents(this);
        getServer().getScheduler().runTaskTimer(this, new TimerScheduler(), 1, 1);
    }

    @Override
    public void onDisable() {

    }

    public abstract void onAccountLoad(YoloPlayer player);

    public abstract void onAccountUnload(YoloPlayer player);
}
