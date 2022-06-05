package br.com.yolomc.bukkit.api.scoreboard.handler;

import br.com.yolomc.bukkit.api.scoreboard.Scoreboard;
import br.com.yolomc.bukkit.api.scoreboard.def4lt.DefaultScoreboard;
import br.com.yolomc.bukkit.api.scoreboard.def4lt.DefaultScoreboardRender;
import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Representa um gerenciador de Scoreboards de jogadores.
 *
 * @author skyprogrammer
 */
public class ScoreboardHandler {

    @Getter
    private static final ScoreboardHandler instance = new ScoreboardHandler();
    private final Map<UUID, Scoreboard> scoreboards;

    public ScoreboardHandler() {
        this.scoreboards = new HashMap<>();
    }

    public void newScoreboard(Player player) {
        DefaultScoreboard scoreboard = new DefaultScoreboard();
        scoreboard.setRender(new DefaultScoreboardRender(scoreboard));
        scoreboard.createScoreboard(player);
        scoreboard.register();
        this.scoreboards.put(player.getUniqueId(), scoreboard);
    }

    public Scoreboard getScoreboard(Player player) {
        return this.scoreboards.get(player.getUniqueId());
    }

    public Scoreboard removeScoreboard(Player player) {
        return this.scoreboards.remove(player.getUniqueId());
    }

    public void update(Player player) {
        if (!this.scoreboards.containsKey(player.getUniqueId()))
            return;
        this.scoreboards.get(player.getUniqueId()).update(player);
    }
}
