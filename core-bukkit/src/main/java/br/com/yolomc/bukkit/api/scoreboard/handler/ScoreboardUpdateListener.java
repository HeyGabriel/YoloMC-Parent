package br.com.yolomc.bukkit.api.scoreboard.handler;

import br.com.yolomc.bukkit.event.timer.ServerTimerEvent;
import lombok.AllArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

/**
 * Atualizador das scoreboards de jogadores.
 */
@AllArgsConstructor
public class ScoreboardUpdateListener implements Listener {

    private ScoreboardHandler handler;

    @EventHandler
    public void onTimer(ServerTimerEvent event) {
        if (event.getCurrentTick() % 3 != 0)
            return;
        Bukkit.getOnlinePlayers().stream().forEach(ps -> handler.update(ps));
    }
}
