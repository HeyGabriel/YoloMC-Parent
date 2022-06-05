package br.com.yolomc.core.profile.model;

import br.com.yolomc.core.Commons;
import br.com.yolomc.core.profile.Group;
import lombok.Getter;

/**
 * Representa o modelo de um {@link Group} que fica
 * armazenado nos dados de um {@link br.com.yolomc.core.profile.YoloPlayer}.
 */
@Getter
public class GroupModel {

    private String name;
    private String teamName;

    public GroupModel(Group group) {
        this.name = group.getName();
        this.teamName = group.getTeamName();
    }

    public boolean stillExist() {
        return Commons.getGroupManager().groupExists(getName());
    }
}
