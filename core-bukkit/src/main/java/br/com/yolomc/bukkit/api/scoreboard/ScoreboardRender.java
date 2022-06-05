package br.com.yolomc.bukkit.api.scoreboard;

import lombok.Getter;

/**
 * Representa um renderizador de scoreboards.
 *
 * @author skyprogrammer
 */
@Getter
public abstract class ScoreboardRender {

    private final Scoreboard scoreboard;

    public ScoreboardRender(Scoreboard scoreboard) {
        this.scoreboard = scoreboard;
    }

    public abstract void renderScore(int id, String prefix, String suffix);
}
