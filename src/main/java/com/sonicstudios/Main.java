package com.sonicstudios;

import com.sonicstudios.command.*;
import lombok.Getter;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import com.sonicstudios.admin.SonicSocialsCommand;
import com.sonicstudios.framework.InventoryManager;
import com.sonicstudios.manager.FileManager;

@Getter
public final class Main extends JavaPlugin {

    @Getter
    private static Main instance;
    private FileManager fileManager;

    @Override
    public void onEnable() {
        instance = this;

        saveDefaultConfig();

        // Manager
        fileManager = new FileManager();

        // Loads the files.
        fileManager.loadFile("media.yml");
        fileManager.loadFile("socials.yml");


        sendStartupBanner();

        // Admin Commands
        new SonicSocialsCommand();

        // Player Commands
        new MediaCommand();
        new SocialsCommand();
        new LiveCommand();
        new DiscordCommand();
        new YouTubeCommand();
        new TwitchCommand();
        new TikTokCommand();


        // Framework
        InventoryManager.register(this);
    }

    @Override
    public void onDisable() {
    }

    // Startup Banner for the Plugin, give me feedback pls
    private void sendStartupBanner() {
        String[] banner = {
                " ",
                ChatColor.BLUE + " ____              _      ____             _       _     ",
                ChatColor.BLUE + "/ ___|  ___  _ __ (_) ___/ ___|  ___   ___(_) __ _| |___ ",
                ChatColor.BLUE + "\\___ \\ / _ \\| '_ \\| |/ __\\___ \\ / _ \\ / __| |/ _` | / __|",
                ChatColor.BLUE + " ___) | (_) | | | | | (__ ___) | (_) | (__| | (_| | \\__ \\",
                ChatColor.BLUE + "|____/ \\___/|_| |_|_|\\___|____/ \\___/ \\___|_|\\__,_|_|___/",
                " ",
                ChatColor.BLUE + "Plugin Information:",
                ChatColor.BLUE + "● " + ChatColor.WHITE + "Version: " + ChatColor.BLUE + "1.0.0",
                ChatColor.BLUE + "● " + ChatColor.WHITE + "Authors: " + ChatColor.BLUE + "SonicStudios",
                " ",
                ChatColor.BLUE + "Server Information:",
                ChatColor.BLUE + "● " + ChatColor.WHITE + "Software: " + ChatColor.BLUE + Bukkit.getServer().getName(),
                ChatColor.BLUE + "● " + ChatColor.WHITE + "Version: " + ChatColor.BLUE + Bukkit.getServer().getBukkitVersion(),
                " ",
                ChatColor.BLUE + "This software has been created by Sonic Studios. All rights reserved.",
                " "
        };

        for (String line : banner) {
            Bukkit.getConsoleSender().sendMessage(line);
        }
    }
}