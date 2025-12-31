package com.sonicstudios.menu;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import com.sonicstudios.framework.InventoryBuilder;
import com.sonicstudios.framework.ItemBuilder;
import com.sonicstudios.utility.MenuUtil;

public class MediaMenu extends InventoryBuilder {

    public MediaMenu(Player player) {
        super(MenuUtil.getMenuSize("media"), MenuUtil.getTitle("media"), true);

        final String key = "media-item";

        ItemStack item = ItemBuilder.item(MenuUtil.getMaterial("media", key))
                .name(MenuUtil.getName("media", key))
                .lore(MenuUtil.getLore("media", key))
                .build();

        setItem(MenuUtil.getSlot("media", key), item, event -> {
            event.setCancelled(true);
            player.closeInventory();

            final String command = MenuUtil.getCommand("media", key);

            if (!command.isEmpty()) player.performCommand(command);
        });

        open(player);
    }
}