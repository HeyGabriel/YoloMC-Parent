package br.com.yolomc.core.profile;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Representa um cargo criado pelo usuário administrador.
 * Armazena informações como nome e id de Team e a nametag parente.
 *
 * @author skyprogrammer
 */
@Getter
@AllArgsConstructor
public class Group {

    private int id;
    private String name;
    private Tag tag;
}
