package br.com.yolomc.bukkit.api.scoreboard.def4lt;

import br.com.yolomc.bukkit.api.scoreboard.Scoreboard;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;

/**
 * Representa uma scoreboard sidebar.
 *
 * @author skyprogrammer
 */
@Getter
public class DefaultScoreboard extends Scoreboard {

    private Objective objective;
    private org.bukkit.scoreboard.Scoreboard scoreboard;

    @Override
    public void register() {
        if (!isRegistered()) {
            this.objective = this.scoreboard.registerNewObjective("sidebar", "dummy");
            this.objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        }
    }

    @Override
    public void unregister() {
        if (this.objective != null) {
            this.objective.unregister();
            this.objective = null;
        }
    }

    @Override
    public void createScoreboard(Player player) {
        this.scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        player.setScoreboard(scoreboard);
    }

    @Override
    public void setDisplayName(String displayName) {
        if (this.objective != null)
            this.objective.setDisplayName(displayName);
    }

    @Override
    public void unregisterScore(int id) {
        this.scoreboard.resetScores(ChatColor.values()[id - 1].toString());
    }

    @Override
    public boolean isRegistered() {
        return this.objective != null;
    }
}
