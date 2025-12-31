package com.sonicstudios.manager;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import com.sonicstudios.Main;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class FileManager {

    private final Map<String, File> files = new HashMap<>();
    private final Map<String, FileConfiguration> configs = new HashMap<>();


    // Loads a file
    public void loadFile(final String fileName) {
        final File folder = new File(Main.getInstance().getDataFolder(), "menus");
        if (!folder.exists()) folder.mkdirs();

        final File file = new File(folder, fileName);
        if (!file.exists()) Main.getInstance().saveResource("menus/" + fileName, false);

        files.put(fileName, file);
        configs.put(fileName, YamlConfiguration.loadConfiguration(file));
    }

    // Reloads a file -> existing file.
    public void reloadFile(final String fileName) {
        final File folder = new File(Main.getInstance().getDataFolder(), "menus");
        if (!folder.exists()) folder.mkdirs();

        final File file = new File(folder, fileName);

        files.put(fileName, file);
        configs.put(fileName, YamlConfiguration.loadConfiguration(file));
    }

    // gets the config for the selected file
    public FileConfiguration getConfig(final String fileName) {
        if (!configs.containsKey(fileName)) loadFile(fileName);
        return configs.get(fileName);
    }
}
