package br.com.yolomc.bukkit.api.scoreboard;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * Representa uma Scoreboard de jogadores.
 *
 * @author skyprogrammer
 */
@Getter
@Setter
public abstract class Scoreboard {

    private ScoreboardRender render;
    private ScoreboardModel model;

    public abstract void register();

    public abstract void unregister();

    public abstract void createScoreboard(Player player);

    public abstract void setDisplayName(String displayName);

    public abstract void unregisterScore(int id);

    public abstract boolean isRegistered();

    public void update(Player player) {
        if (!isRegistered())
            return;
        if (this.model != null) {
            String[] scores = this.model.getModel(player).toArray(new String[]{});
            int w = 1;
            for (int x = scores.length; x > 0; x--) {
                String text = scores[x - 1];
                String prefix = text;
                String suffix = "";

                if (text.length() > 12) {
                    int a = 12;
                    while (text.substring(0, a).endsWith("§"))
                        --a;
                    prefix = text.substring(0, a);
                    suffix = text.substring(a, text.length());
                    if (!suffix.startsWith("§"))
                        for (int i = prefix.length(); i > 0; i--) {
                            if (prefix.substring(i - 1, i).equals("§") && prefix.substring(i, i + 1) != null) {
                                suffix = prefix.substring(i - 1, i + 1) + suffix;
                                break;
                            }
                        }
                    if (!suffix.startsWith("§"))
                        suffix = "§f" + suffix;
                }

                this.render.renderScore((w), prefix, suffix);
                ++w;
            }
            if (scores.length < 15) {
                for (int i = 15; i > scores.length; i--) {
                    this.unregisterScore(i);
                }
            }
        }
    }
}
