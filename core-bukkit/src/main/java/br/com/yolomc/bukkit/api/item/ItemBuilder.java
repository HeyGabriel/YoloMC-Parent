package br.com.yolomc.bukkit.api.item;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

/**
 * Construtor de ItemStacks.
 *
 * @author skyprogrammer
 */
public class ItemBuilder {

    private ItemStack itemStack;

    public ItemBuilder(Material type) {
        itemStack = new ItemStack(type);
    }

    public ItemBuilder(int id) {
        this(Material.getMaterial(id));
    }

    public ItemBuilder changeItem(Consumer<ItemStack> consumer) {
        consumer.accept(itemStack);
        return this;
    }

    public ItemBuilder changeMeta(Consumer<ItemMeta> consumer) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        consumer.accept(itemMeta);
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemBuilder setType(Material type) {
        return changeItem(itemStack -> itemStack.setType(type));
    }

    public ItemBuilder setAmount(int amount) {
        return changeItem(itemStack -> itemStack.setAmount(amount));
    }

    public ItemBuilder setData(MaterialData data) {
        return changeItem(itemStack -> itemStack.setData(data));
    }

    public ItemBuilder setDurability(int durability) {
        return changeItem(itemStack -> itemStack.setDurability((byte) durability));
    }

    public ItemBuilder setTypeId(int type) {
        return changeItem(itemStack -> itemStack.setTypeId(type));
    }

    public ItemBuilder removeItemFlag(ItemFlag... itemFlags) {
        return changeMeta(itemMeta -> itemMeta.removeItemFlags(itemFlags));
    }

    public ItemBuilder addItemFlags(ItemFlag... itemFlags) {
        return changeMeta(itemMeta -> itemMeta.addItemFlags(itemFlags));
    }

    public ItemBuilder setLore(List<String> lore) {
        return changeMeta(itemMeta -> itemMeta.setLore(lore));
    }

    public ItemBuilder setLore(String... lore) {
        return setLore(Arrays.asList(lore));
    }

    public ItemBuilder setName(String name) {
        return changeMeta(itemMeta -> itemMeta.setDisplayName(name));
    }

    public ItemBuilder addEnchant(Enchantment ench, int level, boolean ignoreLevelRestriction) {
        return changeMeta(itemMeta -> itemMeta.addEnchant(ench, level, ignoreLevelRestriction));
    }

    public ItemBuilder addUnsafeEnchantment(Enchantment ench, int level) {
        return changeItem(itemStack -> itemStack.addUnsafeEnchantment(ench, level) );
    }

    public ItemBuilder setUnbreakable(boolean unbreakable) {
        return changeMeta(itemMeta -> itemMeta.spigot().setUnbreakable(unbreakable));
    }

    public ItemStack getStack() {
        return itemStack;
    }
}
