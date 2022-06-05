package br.com.yolomc.bukkit.scheduler;

import br.com.yolomc.bukkit.event.timer.ServerTimerEvent;
import br.com.yolomc.core.profile.YoloPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

/**
 * Task que executa o evento do timer.
 *
 * @author skyprogrammer
 */
public class TimerScheduler implements Runnable {

    private long tick;

    @Override
    public void run() {
        if (++tick % 30 == 0)
            Bukkit.getOnlinePlayers().stream().filter(ps -> YoloPlayer.getYoloPlayer(ps) == null)
                    .forEach(ps -> ps.kickPlayer(ChatColor.RED + "ERROR"));
        Bukkit.getPluginManager().callEvent(new ServerTimerEvent(tick));
    }
}
