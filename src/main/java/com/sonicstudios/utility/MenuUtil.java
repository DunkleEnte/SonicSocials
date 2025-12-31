package com.sonicstudios.utility;

import lombok.experimental.UtilityClass;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import com.sonicstudios.Main;

import java.util.List;

@UtilityClass
public class MenuUtil {

    // file of the gui menu
    private FileConfiguration getConfig(String file) {
        return Main.getInstance().getFileManager().getConfig(file + ".yml");
    }

    // Title of the GUI Menu.
    public String getTitle(String file) {
        return MessageUtil.translate(getConfig(file).getString("menu.title", ""));
    }

    // Size of the GUI Menu.
    public int getMenuSize(String file) {
        return getConfig(file).getInt("menu.size", 27);
    }

    // Slot of the item
    public int getSlot(String file, String key) {
        return getConfig(file).getInt("menu." + key + ".slot", 0);
    }

    // material of the item
    public Material getMaterial(String file, String key) {
        return Material.matchMaterial(getConfig(file).getString("menu." + key + ".material", "BARRIER"));
    }

    // name of the item
    public String getName(String file, String key) {
        return MessageUtil.translate(getConfig(file).getString("menu." + key + ".name", key));
    }

    // lore of the item as a list
    public List<String> getLore(String file, String key) {
        List<String> lore = getConfig(file).getStringList("menu." + key + ".lore");

        return lore.stream().map(MessageUtil::translate).toList();
    }

    // command that will be executed when clicking on an item
    public String getCommand(String file, String key) {
        return getConfig(file).getString("menu." + key + ".command", "");
    }
}
