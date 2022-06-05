package br.com.yolomc.bukkit.api.menu;

import org.bukkit.inventory.InventoryHolder;

import lombok.RequiredArgsConstructor;

/**
 * Representa o suporte de um {@link MenuInventory}.
 *
 * @author skyprogrammer
 */
@RequiredArgsConstructor
public class MenuHolder implements InventoryHolder {
	
	private final MenuInventory inventory;

	@Override
	public MenuInventory getInventory() {
		return this.inventory;
	}
}
