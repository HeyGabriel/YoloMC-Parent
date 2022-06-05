package br.com.yolomc.bukkit.api.menu;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftInventoryCustom;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

/**
 * Representa um inventário custom com leitura automáticas de clicks feitos
 * por jogadores.
 *
 * @author skyprogrammer
 */
public class MenuInventory extends CraftInventoryCustom {

	private final Map<ItemStack, MenuClickHandler> handlers = new HashMap<>();

	public MenuInventory(int size, String title) {
		super(null, size, title.length() > 32 ? title.substring(0, 32) : title);
		injectHolder();
	}

	public MenuInventory(InventoryType type, String title) {
		super(null, type, title.length() > 32 ? title.substring(0, 32) : title);
		injectHolder();
	}

	public MenuInventory(InventoryType type) {
		super(null, type);
		injectHolder();
	}

	public void setItem(int index, ItemStack item, MenuClickHandler click) {
		super.setItem(index, item);
		this.handlers.put(item, click);
	}

	public HashMap<Integer, ItemStack> addItem(MenuClickHandler handler, ItemStack... items) {
		HashMap<Integer, ItemStack> map = super.addItem(items);
		for (ItemStack item : items)
			this.handlers.put(item, handler);
		return map;
	}

	public MenuClickHandler getClickHandler(ItemStack item) {
		if (item == null)
			return null;
		return this.handlers.get(item);
	}

	private void injectHolder() {
		try {
			Field field = Class.forName("org.bukkit.craftbukkit.v1_8_R3.inventory.CraftInventoryCustom$MinecraftInventory")
					.getDeclaredField("owner");
			field.setAccessible(true);
			Field modifiersField = Field.class.getDeclaredField("modifiers");
			modifiersField.setAccessible(true);
			modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
			field.set(getInventory(), new MenuHolder(this));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
