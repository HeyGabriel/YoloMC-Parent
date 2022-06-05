package br.com.yolomc.bukkit.listener;

import org.bukkit.DyeColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.EnchantingInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Dye;

/**
 * Listener para permitir os jogadores encantar itens sem necessitar
 * do Lápis Lazuli.
 *
 * @author skyprogrammer
 */
public class EnchantingListener implements Listener {

    private ItemStack itemStack;

    public EnchantingListener() {
        Dye d = new Dye();
        d.setColor(DyeColor.BLUE);
        this.itemStack = d.toItemStack();
        this.itemStack.setAmount(3);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onInventoryClose(InventoryCloseEvent event) {
        if (event.getPlayer() instanceof Player && event.getInventory() instanceof EnchantingInventory) {
            event.getInventory().setItem(1, null);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player && event.getInventory() instanceof EnchantingInventory) {
            if (event.getSlot() == 1 && event.getInventory().getItem(1) != null) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onInventoryOpen(InventoryOpenEvent e) {
        if (e.getPlayer() instanceof Player && e.getInventory() instanceof EnchantingInventory) {
            e.getInventory().setItem(1, this.itemStack);
        }
    }
}
