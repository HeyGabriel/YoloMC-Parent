package br.com.yolomc.core.profile;

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

    private char colorChar;
    private String name;
    private String prefix;

    public String getColouredName(boolean bold) {
        return colorChar + (bold ? "§l" + name.toUpperCase() : name);
    }
}
