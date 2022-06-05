package br.com.yolomc.bukkit.api.menu;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * Providencia informações sobre clicks em inventários feito por jogadores.
 *
 * @author skyprogrammer
 */
public interface MenuClickHandler {

	void onClick(Player p, MenuInventory inventory, ClickType type, ItemStack stack, int slot);
}
