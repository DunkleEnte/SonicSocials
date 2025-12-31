package com.sonicstudios.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import com.sonicstudios.Main;
import com.sonicstudios.menu.MediaMenu;

import java.util.Objects;

public class MediaCommand implements CommandExecutor {

    public MediaCommand() {
        Objects.requireNonNull(Main.getInstance().getCommand("media")).setExecutor(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if(!(sender instanceof Player player)) return false;

        new MediaMenu(player);
        return false;
    }
}