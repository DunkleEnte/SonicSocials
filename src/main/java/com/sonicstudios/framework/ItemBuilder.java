package com.sonicstudios.framework;

import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.*;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import com.sonicstudios.Main;
import com.sonicstudios.utility.MessageUtil;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

@SuppressWarnings("unused")
public class ItemBuilder {
    private final ItemStack itemStack;

    public ItemBuilder(Material material){
        this(material, 1);
    }

    public ItemBuilder(ItemStack itemStack){
        this.itemStack = itemStack;
    }

    public ItemBuilder(Material material, int amount){
        itemStack = new ItemStack(material, amount);
    }

    public static ItemBuilder item(ItemStack itemStack) {
        return new ItemBuilder(itemStack);
    }

    public static ItemBuilder item(Material material) {
        return new ItemBuilder(material);
    }

    public static ItemBuilder item(Material material, int amount) {
        return new ItemBuilder(material, amount);
    }

    public ItemBuilder clone() {
        try {
            ItemBuilder clone = (ItemBuilder) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
        return new ItemBuilder(itemStack);
    }

    public ItemBuilder name(String name) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta != null) {
            itemMeta.setDisplayName(MessageUtil.translate(name));
        }
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemBuilder tooltip(boolean b){
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta != null) {
            itemMeta.setHideTooltip(!b);
        }
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    @SuppressWarnings("deprecation")
    public ItemBuilder rawName(String name) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta != null) {
            itemMeta.setDisplayName(name);
        }
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemBuilder addUnsafeEnchantment(Enchantment enchantment, int level) {
        itemStack.addUnsafeEnchantment(enchantment, level);
        return this;
    }

    public ItemBuilder removeEnchantment(Enchantment enchantment) {
        itemStack.removeEnchantment(enchantment);
        return this;
    }

    @SuppressWarnings("deprecation")
    public ItemBuilder skullOwner(String playerName) {
        if (itemStack.getType() != Material.PLAYER_HEAD)
            itemStack.setType(Material.PLAYER_HEAD);

        SkullMeta itemMeta = (SkullMeta) itemStack.getItemMeta();
        if (itemMeta != null) {
            OfflinePlayer offline = Bukkit.getOfflinePlayer(playerName);
            itemMeta.setOwningPlayer(offline);
            itemStack.setItemMeta(itemMeta);
        }
        return this;
    }

    @SuppressWarnings("deprecation")
    public ItemBuilder skullOwner(Player player) {
        SkullMeta itemMeta = (SkullMeta) itemStack.getItemMeta();
        if (itemMeta != null) {
            itemMeta.setOwnerProfile(player.getPlayerProfile());
            itemStack.setItemMeta(itemMeta);
        }
        return this;
    }

    @SuppressWarnings("deprecation")
    public ItemBuilder skullOwner(OfflinePlayer player) {
        if (itemStack.getType() != Material.PLAYER_HEAD) {
            itemStack.setType(Material.PLAYER_HEAD);
        }
        SkullMeta meta = (SkullMeta) itemStack.getItemMeta();
        if (meta != null) {
            meta.setOwningPlayer(player);
            itemStack.setItemMeta(meta);
        }
        return this;
    }

    public ItemBuilder addEnchantment(Enchantment enchantment, int level){
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta != null) {
            itemMeta.addEnchant(enchantment, level, true);
        }
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemBuilder addEnchantments(Map<Enchantment, Integer> enchantments){
        itemStack.addEnchantments(enchantments);
        return this;
    }

    public ItemBuilder lore(String... lore){
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta != null) {
            itemMeta.setLore(Arrays.stream(lore).map(MessageUtil::translate).toList());
        }
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemBuilder lore(List<String> lore) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta != null) {
            itemMeta.setLore(lore.stream().map(MessageUtil::translate).toList());
        }
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemBuilder amount(int amount) {
        itemStack.setAmount(amount);
        return this;
    }

    public ItemBuilder durability(int damage) {
        if (itemStack.getItemMeta() instanceof Damageable damageable) {
            damageable.setDamage(damage);
            itemStack.setItemMeta(damageable);
        } else {
            throw new IllegalArgumentException("ItemMeta is required to be an instance of Damageable to set the durability!");
        }
        return this;
    }

    public ItemBuilder addFlag(ItemFlag flag) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta != null) {
            itemMeta.addItemFlags(flag);
            itemStack.setItemMeta(itemMeta);
        }
        return this;
    }

    public ItemBuilder removeFlag(ItemFlag flag) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta != null) {
            itemMeta.removeItemFlags(flag);
            itemStack.setItemMeta(itemMeta);
        }
        return this;
    }

    public ItemBuilder glow() {
        addUnsafeEnchantment(Enchantment.LUCK_OF_THE_SEA, 1);
        addFlag(ItemFlag.HIDE_ENCHANTS);
        return this;
    }

    public ItemBuilder removeGlow() {
        removeEnchantment(Enchantment.LUCK_OF_THE_SEA);
        removeFlag(ItemFlag.HIDE_ENCHANTS);
        return this;
    }

    public ItemBuilder unbreakable() {
        ItemMeta meta = itemStack.getItemMeta();
        if (meta != null) {
            meta.setUnbreakable(true);
        }
        itemStack.setItemMeta(meta);
        return this;
    }

    public ItemBuilder breakable() {
        ItemMeta meta = itemStack.getItemMeta();
        if (meta != null) {
            meta.setUnbreakable(false);
        }
        itemStack.setItemMeta(meta);
        return this;
    }

    public ItemBuilder customModelData(int data) {
        ItemMeta meta = itemStack.getItemMeta();
        if (meta != null) {
            meta.setCustomModelData(data);
        }
        itemStack.setItemMeta(meta);
        return this;
    }

    public ItemBuilder meta(ItemMeta itemMeta) {
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemMeta meta() {
        return itemStack.getItemMeta();
    }

    public PersistentDataContainer pdc() {
        return itemStack.getItemMeta().getPersistentDataContainer();
    }

    public ItemBuilder editMeta(Consumer<ItemMeta> metaConsumer) {
        itemStack.editMeta(metaConsumer);
        return this;
    }

    public ItemBuilder editStack(Consumer<ItemStack> stackConsumer) {
        stackConsumer.accept(itemStack);
        return this;
    }

    public ItemBuilder editPdc(Consumer<PersistentDataContainer> pdcConsumer) {
        pdcConsumer.accept(itemStack.getItemMeta().getPersistentDataContainer());
        return this;
    }

    public ItemBuilder from(ItemStack itemStack) {
        return new ItemBuilder(itemStack);
    }

    public ItemBuilder bookData(BookMeta bookMeta) {
        itemStack.setItemMeta(bookMeta);
        return this;
    }

    public ItemBuilder bannerData(BannerMeta bannerMeta) {
        itemStack.setItemMeta(bannerMeta);
        return this;
    }

    public ItemBuilder leatherArmorColor(Color color) {
        LeatherArmorMeta meta = (LeatherArmorMeta) itemStack.getItemMeta();
        if (meta != null) meta.setColor(color);
        itemStack.setItemMeta(meta);
        return this;
    }

    public ItemBuilder fireworkData(FireworkMeta meta) {
        itemStack.setItemMeta(meta);
        return this;
    }

    public ItemBuilder addAttribute(Attribute attribute, AttributeModifier modifier) {
        ItemMeta meta = itemStack.getItemMeta();
        if (meta != null) meta.addAttributeModifier(attribute, modifier);
        itemStack.setItemMeta(meta);
        return this;
    }

    public ItemBuilder clearAttributes() {
        ItemMeta meta = itemStack.getItemMeta();
        if (meta != null) Objects.requireNonNull(meta.getAttributeModifiers())
                .forEach(meta::removeAttributeModifier);
        itemStack.setItemMeta(meta);
        return this;
    }

    public ItemBuilder hideAttributes() {
        ItemMeta meta = itemStack.getItemMeta();
        if (meta != null) {
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        }
        itemStack.setItemMeta(meta);
        return this;
    }

    public ItemBuilder addContainerValue(String key, String value){
        ItemMeta meta = itemStack.getItemMeta();
        if(meta != null){
            meta.getPersistentDataContainer().set(new NamespacedKey(Main.getInstance(), key), PersistentDataType.STRING, value);
            itemStack.setItemMeta(meta);
        }
        return this;
    }

    public ItemBuilder addContainerValue(String key, int value){
        ItemMeta meta = itemStack.getItemMeta();
        if(meta != null){
            meta.getPersistentDataContainer().set(new NamespacedKey(Main.getInstance(), key), PersistentDataType.INTEGER, value);
            itemStack.setItemMeta(meta);
        }
        return this;
    }

    public ItemBuilder addContainerValue(String key, boolean value){
        ItemMeta meta = itemStack.getItemMeta();
        if(meta != null){
            meta.getPersistentDataContainer().set(new NamespacedKey(Main.getInstance(), key), PersistentDataType.BOOLEAN, value);
            itemStack.setItemMeta(meta);
        }
        return this;
    }

    public ItemStack build() {
        return itemStack;
    }
}