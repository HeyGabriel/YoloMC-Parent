package br.com.yolomc.bukkit.api.scoreboard.def4lt;

import br.com.yolomc.bukkit.api.scoreboard.Scoreboard;
import br.com.yolomc.bukkit.api.scoreboard.ScoreboardRender;
import org.bukkit.ChatColor;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Team;

/**
 * Representa um renderizador de sidebars.
 *
 * @author skyprogrammer
 */
public class DefaultScoreboardRender extends ScoreboardRender {

    private Objective objective;

    public DefaultScoreboardRender(Scoreboard scoreboard) {
        super(scoreboard);
        this.objective = ((DefaultScoreboard) scoreboard).getObjective();
    }

    @Override
    public void renderScore(int id, String prefix, String suffix) {
        if (!getScoreboard().isRegistered())
            return;

        String entry = ChatColor.values()[id - 1].toString();
        ((DefaultScoreboard) getScoreboard()).getObjective().getScore(entry).setScore(id);

        String teamName = String.format("sb-line-%d", id);
        Team team;
        if ((team = ((DefaultScoreboard) getScoreboard()).getScoreboard().getTeam(teamName)) == null)
            team = ((DefaultScoreboard) getScoreboard()).getScoreboard().registerNewTeam(teamName);
        if (!team.hasEntry(entry))
            team.addEntry(entry);

        team.setPrefix(prefix.length() > 16 ? prefix.substring(0, 16) : prefix);
        team.setSuffix(suffix.length() > 16 ? suffix.substring(0, 16) : suffix);
    }
}
