package br.com.yolomc.bukkit.api.scoreboard;

import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.Vector;

/**
 * Classe utilizada para gerar um design model para
 * sidebars.
 *
 * @author skyprogrammer
 */
@Getter
public abstract class ScoreboardModel {

    private final Scoreboard scoreboard;

    public ScoreboardModel(Scoreboard scoreboard) {
        this.scoreboard = scoreboard;
    }

    public abstract Vector<String> getModel(Player player);
}
