package br.com.yolomc.core.profile;

import br.com.yolomc.core.util.ChatColor;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Representa uma nametag que fica a amostra sobre a cabeça
 * de um jogador e também no tablist.
 *
 * @author skyprogrammer
 */
@Getter
@AllArgsConstructor
public class Tag {

    private ChatColor color;
    private String name;
    private String teamId;
    private String prefix;

    public String getColouredName(boolean bold) {
        return ChatColor.COLOR_CHAR + color.getChar() + (bold ? ChatColor.BOLD + name.toUpperCase() : name);
    }
}
