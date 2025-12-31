package com.sonicstudios.menu;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import com.sonicstudios.framework.InventoryBuilder;
import com.sonicstudios.framework.ItemBuilder;
import com.sonicstudios.utility.MenuUtil;

public class SocialsMenu extends InventoryBuilder {

    public SocialsMenu(Player player) {
        super(MenuUtil.getMenuSize("socials"), MenuUtil.getTitle("socials"), true);

        final String[] keys = {"tiktok-item", "youtube-item", "twitch-item", "discord-item"};

        for (String key : keys) {
            ItemStack item = ItemBuilder.item(MenuUtil.getMaterial("socials", key))
                    .name(MenuUtil.getName("socials", key))
                    .lore(MenuUtil.getLore("socials", key))
                    .build();

            setItem(MenuUtil.getSlot("socials", key), item, event -> {
                event.setCancelled(true);
                player.closeInventory();

                final String command = MenuUtil.getCommand("socials", key);

                if (!command.isEmpty()) player.performCommand(command);
            });
        }

        open(player);
    }

}